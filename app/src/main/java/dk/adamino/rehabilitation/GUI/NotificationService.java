package dk.adamino.rehabilitation.GUI;

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

    private static NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;
    private static final int NOTIFICATION_ID = 1;

    public NotificationService() {
    }

    /**
     * Send notification to client about exercises
     */
    public void notifyClientAboutExercises() {
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
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
        Log.d("Alarm", "Alarm Fired!");
        // TODO ALH: Refactor to select activity based on if client is already logged in
        Intent loginActivity = LoginActivity.newIntent(context);
        // Create pending activity, which is a activity that can be called from a notification
        PendingIntent loginPendingIntent = PendingIntent.getActivity(
                context,
                0,
                loginActivity,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Build exercise notification
        mNotificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_rehab)
                .setContentTitle(context.getString(R.string.exercise_notification_title))
                .setContentText(context.getText(R.string.exercise_notification_content))
                .setAutoCancel(true)
                .setContentIntent(loginPendingIntent)
                .addAction(R.drawable.ic_rehab, "Do exercises", loginPendingIntent);

        // Get reference to manager for enabling activating the notification
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notifyClientAboutExercises();
    }


}
