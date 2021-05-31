package jp.sinya.battery.demo1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver;
    private BatteryHandler batteryHandler;

    private TextView tvVoltage;
    private TextView tvTemperture;
    private TextView tvStatus;
    private TextView tvLevel;
    private TextView tvHealth;
    private TextView tvTechnology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        registerBatteryReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (batteryHandler != null) {
            batteryHandler.removeMessages(0);
            batteryHandler.removeCallbacksAndMessages(null);
            batteryHandler = null;
        }

        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
        }
    }

    private void initViews() {
        tvVoltage = findViewById(R.id.activity_main_tv_battery_voltage);
        tvTechnology = findViewById(R.id.activity_main_tv_battery_technology);
        tvTemperture = findViewById(R.id.activity_main_tv_battery_temperature);
        tvLevel = findViewById(R.id.activity_main_tv_battery_level);
        tvStatus = findViewById(R.id.activity_main_tv_battery_status);
        tvHealth = findViewById(R.id.activity_main_tv_battery_health);
    }

    private void registerBatteryReceiver() {
        batteryReceiver = new BatteryReceiver();
        this.registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    class BatteryHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            /*int voltage = arg1.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            mTvVoltage.setText("电压：" + voltage / 1000 + "." + voltage % 1000 + "V");

            int temperature = arg1.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            mTvTemperature.setText("温度：" + temperature / 10 + "." + temperature % 10 + "℃");
            if (temperature >= 300) {
                mTvTemperature.setTextColor(Color.RED);
            } else {
                mTvTemperature.setTextColor(Color.BLUE);
            }

            int level = arg1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = arg1.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int levelPercent = (int) (((float) level / scale) * 100);
            mTvLevel.setText("电量：" + levelPercent + "%");
            if (level <= 10) {
                mTvLevel.setTextColor(Color.RED);
            } else {
                mTvLevel.setTextColor(Color.BLUE);
            }

            int status = arg1.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            String strStatus = "未知状态";
            ;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    strStatus = "充电中……";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    strStatus = "放电中……";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    strStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    strStatus = "充电完成";
                    break;
            }
            mTvStatus.setText("状态：" + strStatus);

            int health = arg1.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
            String strHealth = "未知 :(";
            ;
            switch (status) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    strHealth = "好 :)";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    strHealth = "过热！";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD: // 未充电时就会显示此状态，这是什么鬼？
                    strHealth = "良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    strHealth = "电压过高！";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    strHealth = "未知 :(";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    strHealth = "过冷！";
                    break;
            }
            mTvHealth.setText("健康状况：" + strHealth);

            String technology = arg1.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            mTvTechnology.setText("电池技术：" + technology);*/
        }
    }
}
