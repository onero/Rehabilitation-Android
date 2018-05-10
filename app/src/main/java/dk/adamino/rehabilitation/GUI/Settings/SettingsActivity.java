package dk.adamino.rehabilitation.GUI.Settings;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import dk.adamino.rehabilitation.GUI.Utils.AlarmService;
import dk.adamino.rehabilitation.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    public static final String TAG = "RehabSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {

                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("settings_text"));
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            // Setup notifications
            final SwitchPreference dailyNotification = (SwitchPreference) findPreference("notifications_daily_exercises");

            dailyNotification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                AlarmService alarmService = AlarmService.getInstance(getActivity());

                // When user changes the switch (turn on/off) react!
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // User turned notifications on
                    if ((boolean) newValue) {
                        alarmService.setAlarmForOneDay();
                        // Turned off
                    } else {
                        alarmService.cancelAlarm();
                    }
                    return true;
                }
            });

            // Setup pick notification time
            final Preference selectPreferredTimePreference = findPreference(getString(R.string.pref_key_set_notification_time));
            SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.pref_key_set_notification_time), MODE_PRIVATE);
            String restoredTime = prefs.getString(getString(R.string.pref_key_notification_time_value), "");
            if (!restoredTime.equals("")) {
                selectPreferredTimePreference.setTitle(restoredTime);
            }
            selectPreferredTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    displayTimePicker();
                    return true;
                }

                private void displayTimePicker() {
                    // Setup calendar for Time Picker
                    final Calendar mCurrentTime = Calendar.getInstance();
                    int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mCurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    // Open Time Picker Dialog
                    mTimePicker = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    selectNotificationTime(hourOfDay, minute, selectPreferredTimePreference);
                                }
                            },
                            hour,
                            minute,
                            true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });
        }

        /**
         * Save the preferred notification time provided by user
         * @param hourOfDay
         * @param minute
         * @param selectPreferredTimePreference
         */
        private void selectNotificationTime(int hourOfDay, int minute, Preference selectPreferredTimePreference) {
            // Setup string to display in app
            String hourOfDayString;
            String minuteOfDayString;
            // Check for neeed to add 0 (to ensure 24 hour format in string)
            if (hourOfDay < 10) {
                hourOfDayString = "0" + hourOfDay;
            } else {
                hourOfDayString = "" + hourOfDay;
            }

            if (minute < 10) {
                minuteOfDayString = "0" + minute;
            } else {
                minuteOfDayString = "" + minute;
            }
            // Create final string
            String timeToSaveAsString = hourOfDayString + ":" + minuteOfDayString;
            // Make sure to display new value to user
            selectPreferredTimePreference.setTitle(timeToSaveAsString);
            // Save value to preference
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.pref_key_set_notification_time), MODE_PRIVATE).edit();
            editor.putString(getString(R.string.pref_key_notification_time_value), timeToSaveAsString);
            editor.apply();

            // Setup alarm at provided time
            AlarmService.getInstance(getActivity()).setAlarmAtSpecificTime(hourOfDay, minute);
        }
    }

    /**
     * Create Intent to navigate to this activity
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }
}
