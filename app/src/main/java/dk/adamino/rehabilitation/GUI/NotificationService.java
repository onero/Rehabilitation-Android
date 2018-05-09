package dk.adamino.rehabilitation.GUI;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import dk.adamino.rehabilitation.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Adamino.
 */
public class NotificationService extends BroadcastReceiver {

    public static final String TAG = "Notification";

    private static NotificationManager mNotificationManager;
    private static final String EXTRA_ACTION_NOTIFY = "notify";
    private static final String EXTRA_ACTION_POSTPONE = "postpone";
    private static final int NOTIFICATION_ID = 1;

    private static Notification mNotification;

    public NotificationService() {
    }

    /**
     * Send notification to client about exercises
     */
    public void sendNotification() {
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    /**
     * Cancel the current notification
     */
    public static void cancelNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if notifications have been setup
        if (mNotificationManager == null) {
            setupNotifications(context);
        }
        String action = intent.getAction();
        // Check for action
        switch (action) {
            // Should we display exercise notification
            case EXTRA_ACTION_NOTIFY:
                Log.d(TAG, "Alarm Fired!");
                sendNotification();
                break;
            case EXTRA_ACTION_POSTPONE:
                Log.d(TAG, "Alarm Postponed!");
                AlarmService.getInstance().setAlarmForOneHour();
                cancelNotification();
                break;
            default:
                Log.d(TAG, "Didn't work!");
                break;
        }

    }

    /**
     * Setup exercise notification for user
     * @param context
     */
    private void setupNotifications(Context context) {
        // TODO ALH: Refactor to select activity based on if client is already logged in
        Intent loginActivity = LoginActivity.newIntent(context);
        // Create pending activity, which is a activity that can be called from a notification
        PendingIntent loginPendingIntent = PendingIntent.getActivity(
                context,
                0,
                loginActivity,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        Intent postpone = new Intent(context, NotificationService.class);
        postpone.setAction(EXTRA_ACTION_POSTPONE);
        PendingIntent postponePendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                postpone,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // Build exercise notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_rehab)
                .setContentTitle(context.getString(R.string.exercise_notification_title))
                .setContentText(context.getText(R.string.exercise_notification_content))
                .setAutoCancel(true)
                .setContentIntent(loginPendingIntent)
                .addAction(R.drawable.ic_rehab, "Do exercises", loginPendingIntent)
                .addAction(android.R.drawable.ic_lock_power_off, "Postpone", postponePendingIntent);

        mNotification = builder.build();

        // Get reference to manager for enabling activating the notification
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * Create Intent to navigate to this activity
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(EXTRA_ACTION_NOTIFY);
        return intent;
    }


}
