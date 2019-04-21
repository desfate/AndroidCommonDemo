package com.defate.mac.common_android.notification.data;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import com.defate.mac.common_android.R;
import com.defate.mac.common_android.notification.NotificationTools;
import com.defate.mac.common_android.notification.data.base.MockNotificationData;

import java.util.ArrayList;

/** Represents data needed for MessagingStyle Notification. */
public class MessagingStyleCommsAppData extends MockNotificationData {

    private static MessagingStyleCommsAppData sInstance = null;

    // Unique data for this Notification.Style:
    private ArrayList<NotificationCompat.MessagingStyle.Message> mMessages;
    // String of all mMessages.
    private String mFullConversation;
    // Name preferred when replying to chat.
    private Person mMe;
    private ArrayList<Person> mParticipants;

    private CharSequence[] mReplyChoicesBasedOnLastMessages;

    private Class mClazz;
    private int notificationChannelId;  //这个消息的编号

    public static MessagingStyleCommsAppData getInstance(Context context) {
        if (sInstance == null) {
            sInstance = getSync(context);
        }
        return sInstance;
    }

    private static synchronized MessagingStyleCommsAppData getSync(Context context) {
        if (sInstance == null) {
            sInstance = new MessagingStyleCommsAppData(context);
        }

        return sInstance;
    }

    private MessagingStyleCommsAppData(Context context) {
        notificationChannelId = 184234;

        // Standard notification values:
        // Content for API <24 (M and below) devices.
        // Note: I am actually hardcoding these Strings based on info below. You would be
        // pulling these values from the same source in your database. I leave this up here, so
        // you can see the standard parts of a Notification first.
        mContentTitle = "3 Messages w/ Famous, Wendy";
        mContentText = "HEY, I see my house! :)";
        mPriority = NotificationCompat.PRIORITY_HIGH;

        // Create the users for the conversation.
        // Name preferred when replying to chat.
        mMe =
                new Person.Builder()
                        .setName("Me MacDonald")
                        .setKey("1234567890")
                        .setUri("tel:1234567890")
                        .setIcon(
                                IconCompat.createWithResource(context, R.drawable.me_macdonald))
                        .build();

        Person participant1 =
                new Person.Builder()
                        .setName("Famous Fryer")
                        .setKey("9876543210")
                        .setUri("tel:9876543210")
                        .setIcon(
                                IconCompat.createWithResource(context, R.drawable.famous_fryer))
                        .build();

        Person participant2 =
                new Person.Builder()
                        .setName("Wendy Wonda")
                        .setKey("2233221122")
                        .setUri("tel:2233221122")
                        .setIcon(IconCompat.createWithResource(context, R.drawable.wendy_wonda))
                        .build();

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the user(s) is starred as a favorite.
        // Note: You don't need to add yourself, aka 'me', as a participant.
        mParticipants = new ArrayList<>();
        mParticipants.add(participant1);
        mParticipants.add(participant2);

        mMessages = new ArrayList<>();

        // For each message, you need the timestamp. In this case, we are using arbitrary longs
        // representing time in milliseconds.
        mMessages.add(
                // When you are setting an image for a message, text does not display.
                new NotificationCompat.MessagingStyle.Message("", 1528490641998l, participant1)
                        .setData("image/png", NotificationTools.resourceToUri(context, R.drawable.earth)));

        mMessages.add(
                new NotificationCompat.MessagingStyle.Message(
                        "Visiting the moon again? :P", 1528490643998l, mMe));

        mMessages.add(
                new NotificationCompat.MessagingStyle.Message("HEY, I see my house!", 1528490645998l, participant2));

        // String version of the mMessages above.
        mFullConversation =
                "Famous: [Picture of Moon]\n\n"
                        + "Me: Visiting the moon again? :P\n\n"
                        + "Wendy: HEY, I see my house! :)\n\n";

        // Responses based on the last messages of the conversation. You would use
        // Machine Learning to get these (https://developers.google.com/ml-kit/).
        mReplyChoicesBasedOnLastMessages =
                new CharSequence[] {"Me too!", "How's the weather?", "You have good eyesight."};

        // Notification channel values (for devices targeting 26 and above):
        mChannelId = "channel_messaging_1";
        // The user-visible name of the channel.
        mChannelName = "Sample Messaging";
        // The user-visible description of the channel.
        mChannelDescription = "Sample Messaging Notifications";
        mChannelImportance = NotificationManager.IMPORTANCE_MAX;
        mChannelEnableVibrate = true;
        mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE;
    }

    public ArrayList<NotificationCompat.MessagingStyle.Message> getMessages() {
        return mMessages;
    }

    public String getFullConversation() {
        return mFullConversation;
    }

    public Person getMe() {
        return mMe;
    }

    public int getNumberOfNewMessages() {
        return mMessages.size();
    }

    public ArrayList<Person> getParticipants() {
        return mParticipants;
    }

    public CharSequence[] getReplyChoicesBasedOnLastMessage() {
        return mReplyChoicesBasedOnLastMessages;
    }

    @Override
    public String toString() {
        return getFullConversation();
    }

    public boolean isGroupConversation() {
        return mParticipants.size() > 1;
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

    public void setNotificationChannelId(int notificationChannelId) {
        this.notificationChannelId = notificationChannelId;
    }
}
