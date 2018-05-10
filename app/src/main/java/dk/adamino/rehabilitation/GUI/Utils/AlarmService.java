package dk.adamino.rehabilitation.GUI.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Adamino.
 */
public class AlarmService {
    private static final String TAG = "RehabAlarmService";
    private static AlarmService instance;

    private static AlarmManager mAlarmManager;
    private static PendingIntent mNotificationPendingIntent;

    private AlarmService(Context context) {
        // Get reference to System Alarm Manager
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Create notification intent
        Intent notificationIntent = NotificationService.newIntent(context);
        // Setup Notification pending intent for alarms
        mNotificationPendingIntent = PendingIntent.getBroadcast(context,
                1337,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static AlarmService getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmService(context);
        }
        return instance;
    }

    /**
     * Cancel Alarm
     */
    public void cancelAlarm() {
        Log.d(TAG, "Alarm canceled");
        mAlarmManager.cancel(mNotificationPendingIntent);
    }

    /**
     * Set alarm to fire pendingIntent in one hour
     */
    public void setAlarmForOneHour() {
        Log.d(TAG, "Alarm set for 1 hour");
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                mNotificationPendingIntent);
    }
    /**
     * Set alarm to fire pendingIntent in one day
     */
    public void setAlarmForOneDay() {
        Log.d(TAG, "Alarm set for 1 day");
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 15000,
                mNotificationPendingIntent);
    }

    /**
     * Set alarm for provided time
     * @param hour
     * @param minute
     */
    public void setAlarmAtSpecificTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Log.d(TAG, "Alarm set for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                mNotificationPendingIntent);
    }
}
