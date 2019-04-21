package com.defate.mac.common_android.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import com.defate.mac.common_android.R;
import com.defate.mac.common_android.notification.data.BigPictureStyleSocialAppData;
import com.defate.mac.common_android.notification.data.BigTextStyleReminderAppData;
import com.defate.mac.common_android.notification.data.InboxStyleEmailAppData;
import com.defate.mac.common_android.notification.data.MessagingStyleCommsAppData;
import com.defate.mac.common_android.notification.services.BigPictureSocialIntentService;
import com.defate.mac.common_android.notification.services.BigTextIntentService;
import com.defate.mac.common_android.notification.services.MessagingIntentService;

/**
 *  分析了google 给的例子
 *  BigTextIntentService  是用来处理通知操作的类
 */

public class NotificationServices {

    /**
     *  普通类型的消息
     * @param context
     * @param title
     * @param content
     * @param primary_channel_id
     * @param notificationChannelId
     */
    public static void getNormalNotication(Context context, String title, String content, String primary_channel_id, int notificationChannelId){
        NotificationTools.showNotication(
                notificationChannelId,
                context,
                NotificationTools.getNotificationCompatBuilder(context, primary_channel_id)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[0]));
    }

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

    public static void getBigPictureStyleNotication(Context context, BigPictureStyleSocialAppData data){

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String primary_channel_id =
                NotificationTools.createNotificationChannel(context, data);

        // 2. Build the BIG_PICTURE_STYLE.
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                // Provides the bitmap for the BigPicture notification.
                .bigPicture(
                        BitmapFactory.decodeResource(
                                context.getResources(),
                                data.getBigImage()))
                // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle(data.getBigContentTitle())
                // Summary line after the detail section in the big form of the template.
                .setSummaryText(data.getSummaryText());

        // 3. Set up main Intent for notification.
        Intent mainIntent = new Intent(context, data.getmClazz());

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // Even though this sample's MainActivity doesn't link to the Activity this Notification
        // launches directly, i.e., it isn't part of the normal workflow, a social app generally
        // always links to individual posts as part of the app flow, so we will follow option 1.

        // For an example of option 2, check out the BIG_TEXT_STYLE example.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack.
        stackBuilder.addParentStack(data.getmClazz());
        // Adds the Intent to the top of the stack.
        stackBuilder.addNextIntent(mainIntent);
        // Gets a PendingIntent containing the entire back stack.
        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // 4. Set up RemoteInput, so users can input (keyboard and voice) from notification.

        // Note: For API <24 (M and below) we need to use an Activity, so the lock-screen presents
        // the auth challenge. For API 24+ (N and above), we use a Service (could be a
        // BroadcastReceiver), so the user can input from Notification or lock-screen (they have
        // choice to allow) without leaving the notification.

        // Create the RemoteInput.
        String replyLabel = context.getString(R.string.reply_label);
        RemoteInput remoteInput =
                new RemoteInput.Builder(BigPictureSocialIntentService.EXTRA_COMMENT)
                        .setLabel(replyLabel)
                        // List of quick response choices for any wearables paired with the phone
                        .setChoices(data.getPossiblePostResponses())
                        .build();

        // Pending intent =
        //      API <24 (M and below): activity so the lock-screen presents the auth challenge
        //      API 24+ (N and above): this should be a Service or BroadcastReceiver
        PendingIntent replyActionPendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(context, BigPictureSocialIntentService.class);
            intent.setAction(BigPictureSocialIntentService.ACTION_COMMENT);
            replyActionPendingIntent = PendingIntent.getService(context, 0, intent, 0);

        } else {
            replyActionPendingIntent = mainPendingIntent;
        }

        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_cancel_white_48dp,
                        replyLabel,
                        replyActionPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // 5. Build and issue the notification.

        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. Later, we use the same global builder to get back the notification
        // we built here for a comment on the post.

        NotificationCompat.Builder notificationCompatBuilder = NotificationTools.getNotificationCompatBuilder(context, primary_channel_id);

        notificationCompatBuilder
                // BIG_PICTURE_STYLE sets title and content for API 16 (4.1 and after).
                .setStyle(bigPictureStyle)
                // Title for API <16 (4.0 and below) devices.
                .setContentTitle(data.getContentTitle())
                // Content for API <24 (7.0 and below) devices.
                .setContentText(data.getContentText())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_cancel_white_48dp))
                .setContentIntent(mainPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary))

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

                .setSubText(Integer.toString(1))
                .addAction(replyAction)
                .setCategory(Notification.CATEGORY_SOCIAL)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(data.getPriority())

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(data.getChannelLockscreenVisibility());

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the sender(s) is starred as a favorite.
        for (String name : data.getParticipants()) {
            notificationCompatBuilder.addPerson(name);
        }


        NotificationTools.showNotication(data.getNotificationChannelId(), context, notificationCompatBuilder);
    }

    public static void getInboxStyleNotification(Context context, InboxStyleEmailAppData data){


        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String primary_channel_id =
                NotificationTools.createNotificationChannel(context, data);

        // 2. Build the INBOX_STYLE.
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                // This title is slightly different than regular title, since I know INBOX_STYLE is
                // available.
                .setBigContentTitle(data.getBigContentTitle())
                .setSummaryText(data.getSummaryText());

        // Add each summary line of the new emails, you can add up to 5.
        for (String summary : data.getIndividualEmailSummary()) {
            inboxStyle.addLine(summary);
        }

        // 3. Set up main Intent for notification.
        Intent mainIntent = new Intent(context, data.getClazz());

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // Even though this sample's MainActivity doesn't link to the Activity this Notification
        // launches directly, i.e., it isn't part of the normal workflow, a eamil app generally
        // always links to individual emails as part of the app flow, so we will follow option 1.

        // For an example of option 2, check out the BIG_TEXT_STYLE example.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack.
        stackBuilder.addParentStack(data.getClazz());
        // Adds the Intent to the top of the stack.
        stackBuilder.addNextIntent(mainIntent);
        // Gets a PendingIntent containing the entire back stack.
        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // 4. Build and issue the notification.

        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. However, we don't need to update this notification later, so we
        // will not need to set a global builder for access to the notification later.

        NotificationCompat.Builder notificationCompatBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), primary_channel_id);

        notificationCompatBuilder

                // INBOX_STYLE sets title and content for API 16+ (4.1 and after) when the
                // notification is expanded.
                .setStyle(inboxStyle)

                // Title for API <16 (4.0 and below) devices and API 16+ (4.1 and after) when the
                // notification is collapsed.
                .setContentTitle(data.getContentTitle())

                // Content for API <24 (7.0 and below) devices and API 16+ (4.1 and after) when the
                // notification is collapsed.
                .setContentText(data.getContentText())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_cancel_white_48dp))
                .setContentIntent(mainPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary))

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

                // Sets large number at the right-hand side of the notification for API <24 devices.
                .setSubText(Integer.toString(data.getNumberOfNewEmails()))

                .setCategory(Notification.CATEGORY_EMAIL)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(data.getPriority())

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(data.getChannelLockscreenVisibility());

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the sender(s) is starred as a favorite.
        for (String name : data.getParticipants()) {
            notificationCompatBuilder.addPerson(name);
        }

        NotificationTools.showNotication(data.getNotificationChannelId(), context, notificationCompatBuilder);
    }

    public static void getMessagingStyleNotification(Context context, MessagingStyleCommsAppData data){


        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String primary_channel_id =
                NotificationTools.createNotificationChannel(context, data);

        // 2. Build the NotificationCompat.Style (MESSAGING_STYLE).
        String contentTitle = data.getContentTitle();

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle(data.getMe())
                        /*
                         * <p>This API's behavior was changed in SDK version
                         * {@link Build.VERSION_CODES#P}. If your application's target version is
                         * less than {@link Build.VERSION_CODES#P}, setting a conversation title to
                         * a non-null value will make {@link #isGroupConversation()} return
                         * {@code true} and passing {@code null} will make it return {@code false}.
                         * This behavior can be overridden by calling
                         * {@link #setGroupConversation(boolean)} regardless of SDK version.
                         * In {@code P} and above, this method does not affect group conversation
                         * settings.
                         *
                         * In our case, we use the same title.
                         */
                        .setConversationTitle(contentTitle);

        // Adds all Messages.
        // Note: Messages include the text, timestamp, and sender.
        for (NotificationCompat.MessagingStyle.Message message : data.getMessages()) {
            messagingStyle.addMessage(message);
        }

        messagingStyle.setGroupConversation(data.isGroupConversation());

        // 3. Set up main Intent for notification.
        Intent notifyIntent = new Intent(context, data.getmClazz());

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // Even though this sample's MainActivity doesn't link to the Activity this Notification
        // launches directly, i.e., it isn't part of the normal workflow, a chat app generally
        // always links to individual conversations as part of the app flow, so we will follow
        // option 1.

        // For an example of option 2, check out the BIG_TEXT_STYLE example.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack
        stackBuilder.addParentStack(data.getmClazz());
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(notifyIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        // 4. Set up RemoteInput, so users can input (keyboard and voice) from notification.

        // Note: For API <24 (M and below) we need to use an Activity, so the lock-screen present
        // the auth challenge. For API 24+ (N and above), we use a Service (could be a
        // BroadcastReceiver), so the user can input from Notification or lock-screen (they have
        // choice to allow) without leaving the notification.

        // Create the RemoteInput specifying this key.
        String replyLabel = context.getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(MessagingIntentService.EXTRA_REPLY)
                .setLabel(replyLabel)
                // Use machine learning to create responses based on previous messages.
                .setChoices(data.getReplyChoicesBasedOnLastMessage())
                .build();

        // Pending intent =
        //      API <24 (M and below): activity so the lock-screen presents the auth challenge.
        //      API 24+ (N and above): this should be a Service or BroadcastReceiver.
        PendingIntent replyActionPendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(context, MessagingIntentService.class);
            intent.setAction(MessagingIntentService.ACTION_REPLY);
            replyActionPendingIntent = PendingIntent.getService(context, 0, intent, 0);

        } else {
            replyActionPendingIntent = mainPendingIntent;
        }

        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_cancel_white_48dp,
                        replyLabel,
                        replyActionPendingIntent)
                        .addRemoteInput(remoteInput)
                        // Informs system we aren't bringing up our own custom UI for a reply
                        // action.
                        .setShowsUserInterface(false)
                        // Allows system to generate replies by context of conversation.
                        .setAllowGeneratedReplies(true)
                        .setSemanticAction(NotificationCompat.Action.SEMANTIC_ACTION_REPLY)
                        .build();


        // 5. Build and issue the notification.

        // Because we want this to be a new notification (not updating current notification), we
        // create a new Builder. Later, we update this same notification, so we need to save this
        // Builder globally (as outlined earlier).

        NotificationCompat.Builder notificationCompatBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), primary_channel_id);


        notificationCompatBuilder
                // MESSAGING_STYLE sets title and content for API 16 and above devices.
                .setStyle(messagingStyle)
                // Title for API < 16 devices.
                .setContentTitle(contentTitle)
                // Content for API < 16 devices.
                .setContentText(data.getContentText())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_cancel_white_48dp))
                .setContentIntent(mainPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary))

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

                // Number of new notifications for API <24 (M and below) devices.
                .setSubText(Integer.toString(data.getNumberOfNewMessages()))

                .addAction(replyAction)
                .setCategory(Notification.CATEGORY_MESSAGE)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(data.getPriority())

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(data.getChannelLockscreenVisibility());

        // If the phone is in "Do not disturb" mode, the user may still be notified if the
        // sender(s) are in a group allowed through "Do not disturb" by the user.
        for (Person name : data.getParticipants()) {
            notificationCompatBuilder.addPerson(name.getUri());
        }

        NotificationTools.showNotication(data.getNotificationChannelId(), context, notificationCompatBuilder);
    }
}
