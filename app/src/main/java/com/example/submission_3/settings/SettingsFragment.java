package com.example.submission_3.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.example.submission_3.R;
import com.example.submission_3.reminder.DailyReminder;
import com.example.submission_3.reminder.UpcomingReminder;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    UpcomingReminder upcomingReminder;
    DailyReminder dailyReminder;

    String language;

    String daily;
    String upcoming;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_settings);
        language = getResources().getString(R.string.key_change_language);

        daily = getResources().getString(R.string.key_daily_reminder);
        upcoming = getResources().getString(R.string.key_upcoming_reminder);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            upcomingReminder = new UpcomingReminder();
            dailyReminder = new DailyReminder();
            findPreference(daily).setOnPreferenceChangeListener(this);
            findPreference(language).setOnPreferenceClickListener(this);

            findPreference(upcoming).setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isOn = (boolean) newValue;
        if (key.equals(daily)) {
            if (isOn) {
                dailyReminder.setRepeatingAlarm(getContext());
                Toast.makeText(getContext(), "Daily reminder is turned on", Toast.LENGTH_SHORT).show();
            } else {
                dailyReminder.cancelAlarm(getContext());
                Toast.makeText(getContext(), "Daily reminder is turned off", Toast.LENGTH_SHORT).show();
            }
        } else if (key.equals(upcoming)) {
            if (isOn) {
                upcomingReminder.setRepeatingUpcomingAlarm(getContext());
                Toast.makeText(getContext(), "Upcoming reminder is turned on", Toast.LENGTH_SHORT).show();
            } else {
                upcomingReminder.cancelAlarm(getContext());
                Toast.makeText(getContext(), "Upcoming reminder is turned off", Toast.LENGTH_SHORT).show();
            }
        }


        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(language)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }
        return false;
    }


}
