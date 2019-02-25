package com.defate.mac.androidcommondemo.samples.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.defate.mac.androidcommondemo.samples.utils.DATABASE_NAME
import com.defate.mac.androidcommondemo.samples.utils.works.AssetsGsonWorker


/**
 * 在这个类中定义创建Dao类的接口，是抽象类，Room将在编译时生成具体的实现类
 */
@Database(entities = arrayOf(Scp::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun ScpDao(): ScpDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequest.Builder(AssetsGsonWorker::class.java!!).build()
                        WorkManager.getInstance().enqueue(request)
                    }
                })
                .build()
        }
    }
}