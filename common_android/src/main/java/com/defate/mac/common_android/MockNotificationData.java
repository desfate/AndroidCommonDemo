package com.defate.mac.common_android;
/** Represents standard data needed for a Notification. */

/**
 * 一切消息来自这个基类
 */
public abstract class MockNotificationData {

    // Standard notification values:
    protected String mContentTitle;
    protected String mContentText;
    protected int mPriority;

    // Notification channel values (O and above):
    protected String mChannelId;
    protected CharSequence mChannelName;
    protected String mChannelDescription;
    protected int mChannelImportance;
    protected boolean mChannelEnableVibrate;
    protected int mChannelLockscreenVisibility;

    // Notification Standard notification get methods:
    public String getContentTitle() {
        return mContentTitle;
    }

    public String getContentText() {
        return mContentText;
    }

    public int getPriority() {
        return mPriority;
    }

    // Channel values (O and above) get methods:
    public String getChannelId() {
        return mChannelId;
    }

    public CharSequence getChannelName() {
        return mChannelName;
    }

    public String getChannelDescription() {
        return mChannelDescription;
    }

    public int getChannelImportance() {
        return mChannelImportance;
    }

    public boolean isChannelEnableVibrate() {
        return mChannelEnableVibrate;
    }

    public int getChannelLockscreenVisibility() {
        return mChannelLockscreenVisibility;
    }
}