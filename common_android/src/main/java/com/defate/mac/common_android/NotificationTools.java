package com.defate.mac.common_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
 *
 * 创建和管理通知渠道
 * 当您定位到Android 8.0（API级别26）时，
 * 您必须实现一个或多个通知渠道。
 * 如果您targetSdkVersion的设置为25或更低，
 * 当您的应用在Android 8.0（API级别26）或更高版本上运行时，
 * 其行为与运行Android 7.1（API级别25）或更低版本的设备上的行为相同。
 */
public class NotificationTools {

    public static final int PUSHID = 1;

    private static final String PRIMARY_CHANNEL_ID = "static";
    private static final String PRIMARY_CHANNEL = "Primary Channel";

    /**
     * 根据当前sdk版本生成notificationCompat
     * @param context
     * @return
     */
    public static NotificationCompat.Builder getNotificationCompatBuilder(Context context, String primary_channel_id){
        //判断是否是8.0Android.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(primary_channel_id, PRIMARY_CHANNEL , NotificationManager.IMPORTANCE_HIGH);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan1);
            return new NotificationCompat.Builder(context, primary_channel_id);
        } else {
            return new NotificationCompat.Builder(context);
        }
    }


    /**
     * 展示notification
     * @param context
     * @param builder
     */
    public static void showNotication(int notificationChannelId,Context context ,NotificationCompat.Builder builder){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notificationChannelId, notify);
    }

    public static String createNotificationChannel(Context context, MockNotificationData mockNotificationData) {
        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            String channelId = mockNotificationData.getChannelId();

            // The user-visible name of the channel.
            CharSequence channelName = mockNotificationData.getChannelName();
            // The user-visible description of the channel.
            String channelDescription = mockNotificationData.getChannelDescription();
            int channelImportance = mockNotificationData.getChannelImportance();
            boolean channelEnableVibrate = mockNotificationData.isChannelEnableVibrate();
            int channelLockscreenVisibility =
                    mockNotificationData.getChannelLockscreenVisibility();

            // Initializes NotificationChannel.
            NotificationChannel notificationChannel =
                    new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);
            notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }

}
