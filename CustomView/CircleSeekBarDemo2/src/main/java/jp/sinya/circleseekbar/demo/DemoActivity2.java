package jp.sinya.circleseekbar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DemoActivity2 extends AppCompatActivity {
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        tvValue = findViewById(R.id.activity_main_tv);
        SeekArc2 seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekArcChangeListener(new SeekArc2.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc2 seekArc, int progress, boolean fromUser) {
                Log.i("Sinya", "progress = " + progress);
                tvValue.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekArc2 seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc2 seekArc) {

            }
        });
    }

}
