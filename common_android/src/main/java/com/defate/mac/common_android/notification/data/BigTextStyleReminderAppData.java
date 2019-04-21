package com.defate.mac.common_android.notification.data;

import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import com.defate.mac.common_android.notification.data.base.MockNotificationData;

/** Represents data needed for BigTextStyle Notification. */

/**
 * 带两个按钮风格的文本展示
 */
public class BigTextStyleReminderAppData extends MockNotificationData {

    private static BigTextStyleReminderAppData sInstance = null;

    // Unique data for this Notification.Style:
    private String mBigContentTitle;
    private String mBigText;
    private String mSummaryText;
    private Class clazz;  //需要向那个类跳转
    private int notificationChannelId;  //这个消息的编号

    public static BigTextStyleReminderAppData getInstance() {
        if (sInstance == null) {
            sInstance = getSync();
        }

        return sInstance;
    }

    private static synchronized BigTextStyleReminderAppData getSync() {
        if (sInstance == null) {
            sInstance = new BigTextStyleReminderAppData();
        }

        return sInstance;
    }

    private BigTextStyleReminderAppData() {

        notificationChannelId = 112;

        // Standard Notification values:
        // Title for API <16 (4.0 and below) devices.
        mContentTitle = "Don't forget to...";
        // Content for API <24 (4.0 and below) devices.
        mContentText = "Feed Dogs and check garage!";
        mPriority = NotificationCompat.PRIORITY_DEFAULT;

        // BigText Style Notification values:
        mBigContentTitle = "Don't forget to...";
        mBigText =
                "... feed the dogs before you leave for work, and check the garage to "
                        + "make sure the door is closed.";
        mSummaryText = "Dogs and Garage";

        // Notification channel values (for devices targeting 26 and above):
        mChannelId = "channel_reminder_1";
        // The user-visible name of the channel.
        mChannelName = "Sample Reminder";
        // The user-visible description of the channel.
        mChannelDescription = "Sample Reminder Notifications";
        mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
        mChannelEnableVibrate = false;
        mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
    }

    public String getBigContentTitle() {
        return mBigContentTitle;
    }

    public String getBigText() {
        return mBigText;
    }

    public String getSummaryText() {
        return mSummaryText;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public int getNotificationChannelId() {
        return notificationChannelId;
    }

    @Override
    public String toString() {
        return getBigContentTitle() + getBigText();
    }
}