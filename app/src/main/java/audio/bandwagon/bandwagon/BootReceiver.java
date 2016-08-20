package audio.bandwagon.bandwagon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.d("Boot", "Setting Alarm at Boot");
            AlarmReceiver alarm = new AlarmReceiver();
            alarm.resetAlarm(context);
        }
    }
}