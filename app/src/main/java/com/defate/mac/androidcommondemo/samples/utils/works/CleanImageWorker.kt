package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 清除缓存的文件
 */
class CleanImageWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters){


    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}