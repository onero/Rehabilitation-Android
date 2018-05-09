package dk.adamino.rehabilitation.BLL;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import dk.adamino.rehabilitation.GUI.LoginActivity;
import dk.adamino.rehabilitation.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Adamino.
 */
public class NotificationService {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;
    private static final int NOTIFICATION_ID = 1;


    public NotificationService(Activity activityToCreateNotificationFrom) {
        // Create intent to send user to activity
        // TODO ALH: Refactor to select activity based on if client is already logged in
        Intent loginActivity = LoginActivity.newIntent(activityToCreateNotificationFrom);
        // Create pending activity, which is a activity that can be called from a notification
        PendingIntent loginPendingIntent = PendingIntent.getActivity(
                activityToCreateNotificationFrom,
                0,
                loginActivity,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Build exercise notification
         mNotificationBuilder = new NotificationCompat.Builder(activityToCreateNotificationFrom)
                .setSmallIcon(R.drawable.ic_rehab)
                .setContentTitle(activityToCreateNotificationFrom.getString(R.string.exercise_notification_title))
                .setContentText(activityToCreateNotificationFrom.getText(R.string.exercise_notification_content))
                .setAutoCancel(true)
                .setContentIntent(loginPendingIntent)
                .addAction(R.drawable.ic_rehab, "Do exercises", loginPendingIntent);

         // Get reference to manager for enabling activating the notification
        mNotificationManager = (NotificationManager) activityToCreateNotificationFrom.getSystemService(NOTIFICATION_SERVICE);
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
    public void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
