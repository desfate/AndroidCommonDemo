package com.defate.mac.common_android;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

/**
 *  分析了google 给的例子
 *  BigTextIntentService  是用来处理通知操作的类
 */

public class NotificationServices {

    /**
     * 比如需要唤醒getBigTextStyle类型的消息
     * @param context
     * @param data
     */
    public static void getBigTextStyleNotication(Context context, BigTextStyleReminderAppData data){

        // 1. 设置通道id 但是在 Build.VERSION_CODES.O 以下的情况 notificationChannelId == null
        String primary_channel_id =
                NotificationTools.createNotificationChannel(context, data);

        // 2. 根据数据生成样式
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(data.getBigText())
                .setBigContentTitle(data.getBigContentTitle())
                .setSummaryText(data.getSummaryText());


        // 3.事件生成 设置跳转事件

        PendingIntent notifyPendingIntent = null ;
        if(data.getClazz() == null){  //未添加事件

        }else{
            Intent notifyIntent = new Intent(context, data.getClazz());
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            notifyPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        // 4. 创建消息内部事件

        // Snooze Action. 休眠事件
        Intent snoozeIntent = new Intent(context, BigTextIntentService.class);
        snoozeIntent.setAction(BigTextIntentService.ACTION_SNOOZE);  //通知intentService 事件类型

        PendingIntent snoozePendingIntent = PendingIntent.getService(context, 0, snoozeIntent, 0);
        NotificationCompat.Action snoozeAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_alarm_white_48dp,
                        "Snooze",
                        snoozePendingIntent)
                        .build();  //这里可以设置事件样式

        // Dismiss Action. 撤销事件
        Intent dismissIntent = new Intent(context, BigTextIntentService.class);
        dismissIntent.setAction(BigTextIntentService.ACTION_DISMISS);

        PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0, dismissIntent, 0);
        NotificationCompat.Action dismissAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_cancel_white_48dp,
                        "Dismiss",
                        dismissPendingIntent)
                        .build(); //这里可以设置事件样式


        // 5. 构建并发行这个通知

        // Notification Channel Id is ignored for Android pre O (26).
        NotificationCompat.Builder notificationCompatBuilder = NotificationTools.getNotificationCompatBuilder(context, primary_channel_id);
        // 给builder设置属性
        notificationCompatBuilder
                // BIG_TEXT_STYLE sets title and content for API 16 (4.1 and after).  设置刚刚展开后的效果
                .setStyle(bigTextStyle)
                // Title for API <16 (4.0 and below) devices.
                .setContentTitle(data.getContentTitle())   //标题
                // Content for API <24 (7.0 and below) devices.
                .setContentText(data.getContentText())   //正文
                .setSmallIcon(R.drawable.ic_launcher)  //图标样式
                .setLargeIcon(BitmapFactory.decodeResource(  //大图标
                        context.getResources(),
                        R.drawable.ic_alarm_white_48dp))
                .setContentIntent(notifyPendingIntent)  //点击跳转事件
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary)) //颜色

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)
                //side note:在API 24+上为4个或更多通知启用自动绑定（n+）
                //设备和所有磨损设备。如果您有多个通知并且
                //您希望使用不同的摘要通知，设置组键并创建
                //摘要通知通过
                //.setgroupsummary（真）
                //.setgroup（group_key_your_name_here）组

                .setCategory(Notification.CATEGORY_REMINDER)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                //将优先级设置为25及以下。对于26及以上，不推荐使用“优先级”
                //“重要性”，在NotificationChannel中设置。整数表示
                //“优先级”与“重要性”不同，因此请确保不要混淆它们。
                .setPriority(data.getPriority())

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                //将锁屏可见性设置为25及以下。26及以上，锁屏
                // 在NotificationChannel中设置可见性。
                .setVisibility(data.getChannelLockscreenVisibility())

                // Adds additional actions specified above. 添加消息内事件
                .addAction(snoozeAction)
                .addAction(dismissAction)

                .build();

        NotificationTools.showNotication(data.getNotificationChannelId(), context, notificationCompatBuilder);
    }
}
