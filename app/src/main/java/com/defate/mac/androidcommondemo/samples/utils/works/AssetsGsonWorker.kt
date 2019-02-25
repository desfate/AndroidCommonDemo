package com.defate.mac.androidcommondemo.samples.utils.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.defate.mac.androidcommondemo.samples.room.AppDatabase
import com.google.gson.stream.JsonReader
import com.defate.mac.androidcommondemo.samples.room.Scp
import com.defate.mac.androidcommondemo.samples.utils.SCP_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * work将执行你的任务   如果程序还在运行
 *
 * 如果程序不运行  work将在适当的时间  执行你的任务
 *
 * 如果android 版本大于21  Job Scheduler
 * 如果android 版本大于14  Firebase JobDispatcher
 * 如果是立刻执行的任务     AlarmManager
 */
class AssetsGsonWorker(context: Context,  workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG by lazy { AssetsGsonWorker::class.java.simpleName }

    override fun doWork(): Result {

        //TODO 需要在任务中执行的业务逻辑
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //将要做的工作

        val plantType = object : TypeToken<List<Scp>>() {}.type
        var jsonReader: JsonReader? = null

        return try {
            val inputStream = applicationContext.assets.open(SCP_DATA_FILENAME)
            jsonReader = JsonReader(inputStream.reader())
            val scpList: List<Scp> = Gson().fromJson(jsonReader, plantType)
            val database = AppDatabase.getInstance(applicationContext)
            database.ScpDao().insertAll(scpList)  //  从文件中读取数据 并写入数据库  用work去实现
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        } finally {
            jsonReader?.close()
        }
    }

}