package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defate.mac.androidcommondemo.samples.utils.KEY_IMAGE_URI
import com.defate.mac.androidcommondemo.samples.utils.OUTPUT_PATH
import com.defate.mac.androidcommondemo.samples.utils.SleepTools
import com.defate.mac.common_android.bitmap.BitmapIoTools
import com.defate.mac.common_android.bitmap.BlurredTools
import com.defate.mac.common_android.notification.NotificationServices
import java.io.FileNotFoundException

/**
 * 使图片模糊的work
 */
class BlurredImageWorker(context: Context, workerParamer: WorkerParameters): Worker(context, workerParamer){

    val TAG= BlurredImageWorker::class.java.simpleName

    override fun doWork(): Result {
        val applicationContext: Context = applicationContext
        NotificationServices.getNormalNotication(
            applicationContext,
            "Blurring image",
            "Blurring image...",
            "test",
            45);
       SleepTools.sleep()

        val resourceUri = inputData.getString(KEY_IMAGE_URI)  //获取图片地址
        try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver

            // Create a bitmap
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            // Blur the bitmap
            val output = BlurredTools.blurBitmap(bitmap, applicationContext)

            // Write bitmap to a temp file
            val outputUri = BitmapIoTools.writeBitmapToFile(applicationContext, output, OUTPUT_PATH)

            // Return the output for the temp file
            val outputData = Data.Builder().putString(
                KEY_IMAGE_URI, outputUri.toString()
            ).build()

            // If there were no errors, return SUCCESS
            return Result.success(outputData)
        } catch (fileNotFoundException: FileNotFoundException) {
            Log.e(TAG, "Failed to decode input stream", fileNotFoundException)
            throw RuntimeException("Failed to decode input stream", fileNotFoundException)

        } catch (throwable: Throwable) {

            // If there were errors, return FAILURE
            Log.e(TAG, "Error applying blur", throwable)
            return Result.failure()
        }


    }


}