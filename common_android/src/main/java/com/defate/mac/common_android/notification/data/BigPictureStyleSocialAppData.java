package com.defate.mac.common_android.notification.data;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import com.defate.mac.common_android.R;
import com.defate.mac.common_android.notification.data.base.MockNotificationData;

import java.util.ArrayList;
/** Represents data needed for BigPictureStyle Notification. */

/**
 * 带大图展示的notification
 */
public class BigPictureStyleSocialAppData extends MockNotificationData {

    private static BigPictureStyleSocialAppData sInstance = null;

    // Unique data for this Notification.Style:
    private int mBigImage;
    private String mBigContentTitle;
    private String mSummaryText;
    private Class mClazz;
    private int notificationChannelId;  //这个消息的编号

    private CharSequence[] mPossiblePostResponses;

    private ArrayList<String> mParticipants;

    public static BigPictureStyleSocialAppData getInstance() {
        if (sInstance == null) {
            sInstance = getSync();
        }
        return sInstance;
    }

    private static synchronized BigPictureStyleSocialAppData getSync() {
        if (sInstance == null) {
            sInstance = new BigPictureStyleSocialAppData();
        }

        return sInstance;
    }

    private BigPictureStyleSocialAppData() {

        notificationChannelId = 12343;
        // Standard Notification values:
        // Title/Content for API <16 (4.0 and below) devices.
        mContentTitle = "Bob's Post";
        mContentText = "[Picture] Like my shot of Earth?";
        mPriority = NotificationCompat.PRIORITY_HIGH;

        // Style notification values:
        mBigImage = R.drawable.earth;
        mBigContentTitle = "Bob's Post";
        mSummaryText = "Like my shot of Earth?";

        // This would be possible responses based on the contents of the post.
        mPossiblePostResponses = new CharSequence[] {"Yes", "No", "Maybe?"};

        mParticipants = new ArrayList<>();
        mParticipants.add("Bob Smith");

        // Notification channel values (for devices targeting 26 and above):
        mChannelId = "channel_social_1";
        // The user-visible name of the channel.
        mChannelName = "Sample Social";
        // The user-visible description of the channel.
        mChannelDescription = "Sample Social Notifications";
        mChannelImportance = NotificationManager.IMPORTANCE_HIGH;
        mChannelEnableVibrate = true;
        mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
    }

    public int getBigImage() {
        return mBigImage;
    }

    public String getBigContentTitle() {
        return mBigContentTitle;
    }

    public String getSummaryText() {
        return mSummaryText;
    }

    public CharSequence[] getPossiblePostResponses() {
        return mPossiblePostResponses;
    }

    public ArrayList<String> getParticipants() {
        return mParticipants;
    }

    public Class getmClazz() {
        return mClazz;
    }

    public void setmClazz(Class mClazz) {
        this.mClazz = mClazz;
    }

    public int getNotificationChannelId() {
        return notificationChannelId;
    }

    @Override
    public String toString() {
        return getContentTitle() + " - " + getContentText();
    }
}