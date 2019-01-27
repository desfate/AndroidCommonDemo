package com.defate.mac.androidcommondemo

import android.content.Context
import android.support.v4.app.NotificationCompat
import com.defate.mac.common_android.NotificationTools

/**
 * 客户端的通知栏相关配置  https://github.com/googlesamples/android-Notifications  一部分代码来自
 */
class NotificationManagers constructor(context: Context){

    var context: Context? = context

    init {
        setBuilder()
    }

    lateinit var builder: NotificationCompat.Builder
    //主构造方法  可以只传递一个context

    //次级构造方法  可以构造一些基本信息
    constructor(context: Context, title: String, contexts: String): this(context){  //只设置title 和 正文
        builder =  NotificationTools.getNotificationCompatBuilder(context,"id").setContentTitle(title) //设置通知栏标题
            .setContentText(contexts) //设置通知栏显示内容
            .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知优先级  //五个优先级  从-2到2
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
    }

    private fun setBuilder(){
        builder = NotificationTools.getNotificationCompatBuilder(context,"id")
    }



    /**
     *  https://github.com/googlesamples/android-Notifications  上展示了四个例子
     */




}
