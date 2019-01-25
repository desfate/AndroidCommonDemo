package com.defate.mac.common_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * android notification tools class
 * 安卓消息机制的工具类 实现消息栏通知功能的整理
 *
 * notification type    1. normal message  2. message with click listener  3. show download progress
 * 一般的消息类型  1. 普通文字消息通知   2.消息带点击功能  3.展示下载进度小消息通知
 *
 * 在4.1以下，只有标准模式，就是包含图标、标题、文字的基本样式
 * 4.1及以上增加了扩展视图，提供了BigText,BigImage两种样式
 *
 * use step
 * 配置Notification.Builder
 * 获取状态栏通知管理NotificationManager
 * 绑定Notification，发送通知请求。
 */
public class NotificationTools {

    private static final int PUSHID = 1;

    private static final String PRIMARY_CHANNEL_ID = "static";
    private static final String PRIMARY_CHANNEL = "Primary Channel";

    /**
     * 根据当前sdk版本生成notificationCompat
     * @param context
     * @return
     */
    public static NotificationCompat.Builder getNotificationCompatBuilder(Context context){
        //判断是否是8.0Android.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL , NotificationManager.IMPORTANCE_HIGH);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan1);
            return new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID);
        } else {
            return new NotificationCompat.Builder(context);
        }
    }

    /**
     * 返回带有点击意图的跳转
     * @param context
     * @param clazz 需要跳转的class
     * @return
     */
    public static NotificationCompat.Builder getNotificationWithIntent(Context context, Class clazz){
        Intent notificationIntent = new Intent(context, clazz);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        return getNotificationCompatBuilder(context)
                .setContentIntent(intent); //设置通知栏点击意图
    }

    /**
     * 展示notification
     * @param context
     * @param builder
     */
    public static void showNotication(Context context ,NotificationCompat.Builder builder){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(PUSHID, notify);
    }




}
