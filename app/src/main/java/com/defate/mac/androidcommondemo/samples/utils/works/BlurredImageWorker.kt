package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 使图片模糊的work
 */
class BlurredImageWorker(context: Context, workerParamer: WorkerParameters): Worker(context, workerParamer){


    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}