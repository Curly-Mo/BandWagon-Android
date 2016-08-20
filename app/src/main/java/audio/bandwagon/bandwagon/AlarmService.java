package audio.bandwagon.bandwagon;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Set;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("AlarmService", "Starting Alarm");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean set = prefs.getBoolean("alarm_set", false);
        if(set) {
            Set<String> days = prefs.getStringSet("days", null);
            Log.d("days", days.toString());


            AlarmManager alarmMgr;
            PendingIntent alarmIntent;

            alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(this, AlarmService.class);
            alarmIntent = PendingIntent.getBroadcast(this, 0, i, 0);

            Long time = prefs.getLong("time", -1);

            alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent
            );

            Intent mainIntent = new Intent(this, MainActivity.class);
            this.startActivity(mainIntent);
        }
    }
}