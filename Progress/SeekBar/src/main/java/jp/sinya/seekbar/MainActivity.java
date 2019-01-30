package jp.sinya.seekbar;

import android.app.Activity;
import android.os.Bundle;

import jp.sinya.seekbar.widget.RangeSeekBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RangeSeekBar seek_bar = (RangeSeekBar) findViewById(R.id.seek_bar);
        seek_bar.setSelectedMinValue(0);
        seek_bar.setSelectedMaxValue(50);
        seek_bar.invalidate();
    }
}
