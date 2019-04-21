package com.defate.mac.common_android.notification.data;

import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import com.defate.mac.common_android.notification.data.base.MockNotificationData;

import java.util.ArrayList;

/** Represents data needed for InboxStyle Notification. */

/**
 * 多条文字类型
 */
public class InboxStyleEmailAppData extends MockNotificationData {

    private static InboxStyleEmailAppData sInstance = null;

    // Unique data for this Notification.Style:
    private int mNumberOfNewEmails;
    private String mBigContentTitle;
    private String mSummaryText;
    private ArrayList<String> mIndividualEmailSummary;
    private Class clazz;  //需要向那个类跳转
    private int notificationChannelId;  //这个消息的编号

    private ArrayList<String> mParticipants;

    public static InboxStyleEmailAppData getInstance() {
        if (sInstance == null) {
            sInstance = getSync();
        }
        return sInstance;
    }

    private static synchronized InboxStyleEmailAppData getSync() {
        if (sInstance == null) {
            sInstance = new InboxStyleEmailAppData();
        }

        return sInstance;
    }

    private InboxStyleEmailAppData() {
        notificationChannelId = 475983;

        // Standard Notification values:
        // Title/Content for API <16 (4.0 and below) devices.
        mContentTitle = "5 new emails";
        mContentText = "from Jane, Jay, Alex +2 more";
        mNumberOfNewEmails = 5;
        mPriority = NotificationCompat.PRIORITY_DEFAULT;

        // Style notification values:
        mBigContentTitle = "5 new emails from Jane, Jay, Alex +2";
        mSummaryText = "New emails";

        // Add each summary line of the new emails, you can add up to 5.
        mIndividualEmailSummary = new ArrayList<>();
        mIndividualEmailSummary.add("Jane Faab  -   Launch Party is here...");
        mIndividualEmailSummary.add("Jay Walker -   There's a turtle on the server!");
        mIndividualEmailSummary.add("Alex Chang -   Check this out...");
        mIndividualEmailSummary.add("Jane Johns -   Check in code?");
        mIndividualEmailSummary.add("John Smith -   Movies later....");

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the user(s) is starred as a favorite.
        mParticipants = new ArrayList<>();
        mParticipants.add("Jane Faab");
        mParticipants.add("Jay Walker");
        mParticipants.add("Alex Chang");
        mParticipants.add("Jane Johns");
        mParticipants.add("John Smith");

        // Notification channel values (for devices targeting 26 and above):
        mChannelId = "channel_email_1";
        // The user-visible name of the channel.
        mChannelName = "Sample Email";
        // The user-visible description of the channel.
        mChannelDescription = "Sample Email Notifications";
        mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
        mChannelEnableVibrate = true;
        mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
    }

    public int getNumberOfNewEmails() {
        return mNumberOfNewEmails;
    }

    public String getBigContentTitle() {
        return mBigContentTitle;
    }

    public String getSummaryText() {
        return mSummaryText;
    }

    public ArrayList<String> getIndividualEmailSummary() {
        return mIndividualEmailSummary;
    }

    public ArrayList<String> getParticipants() {
        return mParticipants;
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

    public void setNotificationChannelId(int notificationChannelId) {
        this.notificationChannelId = notificationChannelId;
    }

    @Override
    public String toString() {
        return getContentTitle() + " " + getContentText();
    }
}