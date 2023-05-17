package com.amapweather.domain

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class JobExecutor @Inject constructor() : ThreadExecutor {

    private val corePoolSize = 4
    private val maximumPoolSize = 8
    private val keepAliveTime = 4

    private var threadPoolExecutor: ThreadPoolExecutor? = null

    init {
        threadPoolExecutor = ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime.toLong(), TimeUnit.SECONDS, LinkedBlockingQueue(), JobThreadFactory())
    }

    override
    fun execute(command: Runnable?) {
        threadPoolExecutor!!.execute(command)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "JobThread_" + counter++)
        }
    }
}