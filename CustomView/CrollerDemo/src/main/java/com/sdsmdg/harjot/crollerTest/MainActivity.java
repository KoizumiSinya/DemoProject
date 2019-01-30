package com.sdsmdg.harjot.crollerTest;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    Croller croller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        croller = (Croller) findViewById(R.id.croller);
        croller.setIndicatorWidth(10);
        croller.setBackCircleColor(android.graphics.Color.parseColor("#EDEDED"));
        croller.setMainCircleColor(android.graphics.Color.WHITE);
        croller.setMax(65);
        croller.setMin(0);
        croller.setStartOffset(-5);
        croller.setSweepAngle(340);

        croller.setIsContinuous(false);
        croller.setLabelColor(android.graphics.Color.BLACK);
        croller.setProgressPrimaryColor(android.graphics.Color.parseColor("#0B3C49"));
        croller.setIndicatorColor(android.graphics.Color.parseColor("#0B3C49"));
        croller.setProgressSecondaryColor(android.graphics.Color.parseColor("#EEEEEE"));
        croller.setProgressRadius(380);
        croller.setBackCircleRadius(300);

        croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                android.util.Log.i("Sinya","value = " + progress);
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                //Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                //Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
