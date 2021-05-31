package jp.sinya.battery.demo1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

/**
 * @author: Sinya
 * @date: 2020-03-17. 11:10
 * @editor:
 * @date:
 * @description:
 */
public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null) {
            return;
        }

        if (intent.hasExtra(BatteryManager.EXTRA_VOLTAGE)) {
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            Log.i("Sinya", "voltage: " + voltage);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_TEMPERATURE)) {
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            Log.i("Sinya", "temperature: " + temperature);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_LEVEL)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Log.i("Sinya", "level: " + level);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_SCALE)) {
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            Log.i("Sinya", "scale: " + scale);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_STATUS)) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            Log.i("Sinya", "status: " + status);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_HEALTH)) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
            Log.i("Sinya", "health: " + health);
        }
        if (intent.hasExtra(BatteryManager.EXTRA_TECHNOLOGY)) {
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            Log.i("Sinya", "technology: " + technology);
        }

    }
}
