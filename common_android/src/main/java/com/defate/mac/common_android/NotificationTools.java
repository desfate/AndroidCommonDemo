package com.defate.mac.common_android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * android notification tools class
 * 安卓消息机制的工具类 实现消息栏通知功能的整理
 *
 * notification type    1. normal message  2. message with click listener  3. show download progress
 * 一般的消息类型  1. 普通文字消息通知   2.消息带点击功能  3.展示下载进度小消息通知
 *
 * use step
 * 配置Notification.Builder
 * 获取状态栏通知管理NotificationManager
 * 绑定Notification，发送通知请求。
 */
public class NotificationTools {

    /**
     * 根据当前sdk版本生成notificationCompat
     * @param context
     * @return
     */
    public static NotificationCompat.Builder getNotificationCompatBuilder(Context context){
        //判断是否是8.0Android.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel("static", "Primary Channel", NotificationManager.IMPORTANCE_HIGH);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan1);
            return new NotificationCompat.Builder(context, "static");
        } else {
            return new NotificationCompat.Builder(context);
        }
    }




}
