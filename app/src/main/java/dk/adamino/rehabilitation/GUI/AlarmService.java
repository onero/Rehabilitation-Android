package dk.adamino.rehabilitation.GUI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

/**
 * Created by Adamino.
 */
public class AlarmService {
    private static AlarmService instance;

    private static AlarmManager mAlarmManager;
    private static PendingIntent mNotificationPendingIntent;

    private AlarmService() {}

    public static AlarmService getInstance() {
        if (instance == null) {
            instance = new AlarmService();
        }
        return instance;
    }

    public void setActivity(Activity activityToStartAlarmFrom) {
        mAlarmManager = (AlarmManager) activityToStartAlarmFrom.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Cancel Notification
     */
    private void cancelNotification() {
        mAlarmManager.cancel(mNotificationPendingIntent);
        mNotificationPendingIntent.cancel();
    }

    public void setPendingIntent(PendingIntent pendingIntentToStartWithAlarm) {
        mNotificationPendingIntent = pendingIntentToStartWithAlarm;
    }

    /**
     * Set alarm to fire pendingIntent in one hour
     */
    public void setAlarmForOneHour() {
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                mNotificationPendingIntent);
    }
    /**
     * Set alarm to fire pendingIntent in one day
     */
    public void setAlarmForOneDay() {
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                mNotificationPendingIntent);
    }
}
