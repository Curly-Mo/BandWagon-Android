package com.curlymo.bandwagon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("AlarmService", "Starting Alarm Service");
        setVolume(this.getApplicationContext());
    }

    private void setVolume(Context context){
        int targetVol = PreferenceManager.getDefaultSharedPreferences(context).getInt("volume", 11);
        int fadeRate = PreferenceManager.getDefaultSharedPreferences(context).getInt("fade_rate", 1);

        if(fadeRate > 0){
            fadeVolume(context, 0, targetVol, fadeRate);
        }else{
            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, targetVol, 0);
        }
    }

    private void fadeVolume(Context context, int currentVol, int targetVol, int fadeRate){
        int pauseTime = (fadeRate*60*1000) / targetVol;

        //Pause first to give app a chance to start up before increasing volume
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(currentVol < targetVol){
            Log.w("debug", Integer.toString(currentVol));
            am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0);
            currentVol += 1;
            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}