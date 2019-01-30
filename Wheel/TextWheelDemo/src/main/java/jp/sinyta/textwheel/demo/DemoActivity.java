package jp.sinyta.textwheel.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {

    private TimerWheel timerMinute;
    private TimerWheel timerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        timerMinute = findViewById(R.id.activity_demo_wheel1);
        timerSecond = findViewById(R.id.activity_demo_wheel2);
        initMinute();
        initSecond();
        timerMinute.setCurIndex(19);
    }

    private void initMinute() {
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                minutes.add("0" + i);
            } else {
                minutes.add(i + "");
            }
        }
        timerMinute.setDataList(minutes);
    }

    private void initSecond() {
        ArrayList<String> seconds = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                seconds.add("0" + i);
            } else {
                seconds.add(i + "");
            }
        }
        timerSecond.setDataList(seconds);
    }
}
