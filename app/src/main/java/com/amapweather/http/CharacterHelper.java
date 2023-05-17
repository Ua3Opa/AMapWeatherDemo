package com.amapweather.http;

import android.text.InputFilter;
import android.text.Spanned;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CharacterHelper {
    public static final InputFilter emojiFilter = new InputFilter() {//emoji过滤器

        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return "";
            }

            return null;
        }
    };

    /**
     * 限制中文名 inputFilter
     */
    public static final InputFilter chinaNameFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern pattern = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5|/]+");
            Matcher matcher = pattern.matcher(source.toString());
            if (!matcher.matches()) {
                return "";
            } else {
                return null;
            }
        }
    };

    /**
     * 限制输入特殊符号 inputFilter
     */
    public static final InputFilter noSpwFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern pattern = Pattern.compile("[`~!@#$%^&*()_\\-+=<>?:\"{}|,.\\/;'\\\\[\\]·~！@#￥%……&*（）" +
                    "——\\-+={}|《》？：“”【】、；‘’，。、]]");
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.matches()) {
                return "";
            } else {
                return null;
            }
        }
    };


    /**
     * 字符串转换成十六进制字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }


    /**
     * json 格式化
     *
     * @param bodyString
     * @return
     */
    public static String jsonFormat(String bodyString) {
        String message;
        try {
            if (bodyString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(bodyString);
                message = jsonObject.toString(4);
            } else if (bodyString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(bodyString);
                message = jsonArray.toString(4);
            } else {
                message = bodyString;
            }
        } catch (JSONException e) {
            message = bodyString;
        }
        return message;
    }

    /**
     * 【Unicode转中文】
     *
     * @param unicode
     * @return 返回转码后的字符串 - 中文格式
     */
    public static String unicodeToChinese(final String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 0; i < hex.length; i++) {
            try {
                // 汉字范围 \u4e00-\u9fa5 (中文)
                if (hex[i].length() >= 4) {//取前四个，判断是否是汉字
                    String chinese = hex[i].substring(0, 4);
                    try {
                        int chr = Integer.parseInt(chinese, 16);
                        boolean isChinese = isChinese((char) chr);
                        //转化成功，判断是否在  汉字范围内
                        if (isChinese) {//在汉字范围内
                            // 追加成string
                            string.append((char) chr);
                            //并且追加  后面的字符
                            String behindString = hex[i].substring(4);
                            string.append(behindString);
                        } else {
                            string.append(hex[i]);
                        }
                    } catch (NumberFormatException e1) {
                        string.append(hex[i]);
                    }
                } else {
                    string.append(hex[i]);
                }
            } catch (NumberFormatException e) {
                string.append(hex[i]);
            }
        }
        return string.toString();
    }

    /**
     * 【判断是否为中文字符】
     *
     * @param c
     * @return 返回判断结果 - boolean类型
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 校验String是否全是中文
     *
     * @param name 被校验的字符串
     * @return true代表全是汉字
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 校验String是否全是英文
     *
     * @param name 被校验的字符串
     * @return true代表全是英文
     */
    public static boolean checkNameEnglish(String name) {
        return name.matches("[a-zA-Z]+");
    }

}
