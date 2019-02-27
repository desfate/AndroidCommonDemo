package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defate.mac.androidcommondemo.samples.utils.KEY_IMAGE_URI
import com.defate.mac.androidcommondemo.samples.utils.SleepTools
import com.defate.mac.common_android.notification.NotificationServices
import java.text.SimpleDateFormat
import java.util.*

/**
 * 用于储存图片 并进行一些处理
 */
class SaveImageToFileWorker (context: Context, workParams: WorkerParameters): Worker(context, workParams){

    private val TAG = SaveImageToFileWorker::class.java.simpleName

    private val TITLE = "Blurred Image"
    private val DATE_FORMATTER = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault())

    override fun doWork(): Result {
        val applicationContext: Context = applicationContext
        NotificationServices.getNormalNotication(
            applicationContext,
            "save the image",
            "save the image",
            "save",
            1432);
        SleepTools.sleep()

        val resolver = applicationContext.contentResolver
        try {
            val resourceUri = inputData
                .getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )
            val imageUrl = MediaStore.Images.Media.insertImage(
                resolver, bitmap, TITLE, DATE_FORMATTER.format(Date())
            )
            if (TextUtils.isEmpty(imageUrl)) {
                Log.e(TAG, "Writing to MediaStore failed")
                return Result.failure()
            }
            val outputData = Data.Builder()
                .putString(KEY_IMAGE_URI, imageUrl)
                .build()
            return Result.success(outputData)
        } catch (exception: Exception) {
            Log.e(TAG, "Unable to save image to Gallery", exception)
            return Result.failure()
        }

    }
}