package com.curlymo.bandwagon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Starting Alarm");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean set = prefs.getBoolean("alarm_set", false);
        if(set) {
            Set<String> days = prefs.getStringSet("days", null);
            Log.d("days", days.toString());
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            int today = cal.get(Calendar.DAY_OF_WEEK) - 1;
            Log.d("today", String.valueOf(today));

            if(days.contains(String.valueOf(today))) {
                Log.d("AlarmReceiver", "Alarm confirmed, launching app.");

                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);

                Intent alarmService = new Intent(context, AlarmService.class);
                context.startService(alarmService);


                this.resetAlarm(context);
            }
        }
    }

    public void setAlarm(Context context){
        Log.d("Alarm", "Setting Alarm");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean set = prefs.getBoolean("alarm_set", false);
        Log.d("Alarm Set", set.toString());
        if(set) {
            AlarmManager alarmMgr;
            PendingIntent alarmIntent;

            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 123456789, intent, 0);

            Long time = prefs.getLong("time", -1);
            Log.d("time", time.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.SECOND,0);
            Log.d("calendar", calendar.getTime().toString());

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeInMillis(System.currentTimeMillis());
            Log.d("Current Time", currentTime.getTime().toString());

            while(System.currentTimeMillis() > calendar.getTimeInMillis()){
                Log.d("calendar", "Incrementing alarm date");
                calendar.add(Calendar.DATE, 1);
                Log.d("calendar", calendar.getTime().toString());
            }

            alarmMgr.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmIntent
            );
        }
    }

    public void cancelAlarm(Context context){
        Log.d("Alarm", "Canceling Alarm");
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void resetAlarm(Context context){
        cancelAlarm(context);
        setAlarm(context);
    }
}