package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defate.mac.androidcommondemo.samples.utils.OUTPUT_PATH
import com.defate.mac.androidcommondemo.samples.utils.SleepTools
import com.defate.mac.common_android.notification.NotificationServices
import java.io.File

/**
 * 清除缓存的文件
 */
class CleanImageWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters){

    val TAG= CleanImageWorker::class.java.simpleName

    override fun doWork(): Result {
        val applicationContext: Context = applicationContext
        NotificationServices.getNormalNotication(
            applicationContext,
            "clean the image",
            "clean the image",
            "clean",
            234);
        SleepTools.sleep()

        try {
            val outputDirectory = File(
                applicationContext.filesDir,
                OUTPUT_PATH
            )
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null && entries!!.size > 0) {
                    for (entry in entries!!) {
                        val name = entry.getName()
                        if (!TextUtils.isEmpty(name) && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            Log.i(TAG, String.format("Deleted %s - %s", name, deleted))
                        }
                    }
                }
            }
            return Result.success()
        } catch (exception: Exception) {
            Log.e(TAG, "Error cleaning up", exception)
            return Result.failure()
        }

    }
}