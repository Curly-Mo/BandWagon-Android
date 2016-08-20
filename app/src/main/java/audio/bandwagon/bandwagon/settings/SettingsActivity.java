package audio.bandwagon.bandwagon.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import audio.bandwagon.bandwagon.Alarm;
import audio.bandwagon.bandwagon.AlarmReceiver;

public class SettingsActivity extends PreferenceActivity
                              implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(key, String.valueOf(sharedPreferences));
        AlarmReceiver alarm = new AlarmReceiver();
        alarm.resetAlarm(this.getApplicationContext());
    }
}