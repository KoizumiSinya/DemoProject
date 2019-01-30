package jp.sinya.circleseekbar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DemoActivity3 extends AppCompatActivity {
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        tvValue = findViewById(R.id.activity_main_tv);
        SeekArc3 seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekArcChangeListener(new SeekArc3.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc3 seekArc, int progress, boolean fromUser) {
                Log.i("Sinya", "progress = " + progress);
                tvValue.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekArc3 seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc3 seekArc) {

            }
        });
    }


}
