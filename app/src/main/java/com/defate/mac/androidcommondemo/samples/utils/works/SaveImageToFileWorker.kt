package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 用于储存图片 并进行一些处理
 */
class SaveImageToFileWorker (context: Context, workParams: WorkerParameters): Worker(context, workParams){



    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}