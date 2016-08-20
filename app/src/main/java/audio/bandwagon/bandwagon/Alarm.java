package audio.bandwagon.bandwagon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;


public class Alarm {

    public static void setAlarm(Context context){
        Log.d("Alarm", "Setting Alarm");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean set = prefs.getBoolean("pref_alarm_enable", false);
        if(set) {
            AlarmManager alarmMgr;
            PendingIntent alarmIntent;

            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmService.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            Long time = prefs.getLong("time", -1);
            Log.d("time", time.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.SECOND,0);
            Log.d("calendar", calendar.getTime().toString());

            alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent
            );
        }
    }

    public static void cancelAlarm(Context context){
        Log.d("Alarm", "Canceling Alarm");
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public static void resetAlarm(Context context){
        cancelAlarm(context);
        setAlarm(context);
    }
}