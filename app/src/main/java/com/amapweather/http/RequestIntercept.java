package com.amapweather.http;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 请求信息拦截器
 */
@Singleton
public class RequestIntercept implements Interceptor {

    private final String TAG = this.getClass().getSimpleName();

    @Inject
    public RequestIntercept() {
    }

    @Override
    public @NotNull
    Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
        } else {
            Log.v(TAG, "request.body() == null");
        }

        //打印url信息
        Log.v(TAG, "Sending Request url        :" + request.url().toString());
        Log.v(TAG, "Sending Request Params     :" + (request.body() != null ? parseParams(request.body(), requestBuffer) : "null"));
        Log.v(TAG, "Sending Request Connection :" + chain.connection());
        Log.v(TAG, "Sending Request Headers    :" + request.headers());

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        //打印响应时间
        Log.v("Received response  in %.1fms%n%s", String.valueOf((t2 - t1) / 1e6d) + originalResponse.headers());

        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = originalResponse
                .headers()
                .get("Content-Encoding");
        String s = originalResponse.headers().toString();
        Log.d(TAG, "headers-------->" + s);
        Buffer clone = buffer.clone();
        String bodyString;

        // 解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {// content使用gzip压缩
            bodyString = ZipHelper.decompressForGzip(clone.readByteArray());// 解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {// content使用zlib压缩
            bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());// 解压
        } else {// content没有被压缩
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }

        Log.v(TAG, CharacterHelper.jsonFormat(bodyString));

        return originalResponse;
    }

    @NonNull
    public static String parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8().replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
            // 修复% 的问题
        }
        return "null";
    }

}
