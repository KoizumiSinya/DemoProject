package jp.sinya.speedbardemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MySeekBar seekBar;
    private RadioGroup radioGroup;
    private int minValue = 0;
    private int maxValue = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo1);
        radioGroup = findViewById(R.id.radio_group);
        seekBar = findViewById(R.id.seek_bar);
        Drawable drawable = LayoutToDrawable(String.valueOf(seekBar.getProgress() + 1));
        seekBar.setThumb(drawable);
        seekBar.setPadding(32, 0, 45, 0);
        seekBar.banClick(true);
        seekBar.setOnBanSeekBarChangeListener(new MySeekBar.OnBanSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //onStopSeekBar(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Sinya", "value = " + progress);

//                MainActivity.this.seekBar.setThumb(LayoutToDrawable(String.valueOf(progress + 1)));
//                ((RadioButton) (radioGroup.getChildAt(progress))).setChecked(true);
            }

        });
        ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);

    }

    public void onStopSeekBar(SeekBar seekBar) {
        int seekProgress = seekBar.getProgress();
        if (seekProgress < 6) {
            seekBar.setProgress(0);
        } else if (seekProgress >= 6 && seekProgress < 13) {
            seekBar.setProgress(8);
        } else if (seekProgress >= 13 && seekProgress < 22) {
            seekBar.setProgress(17);
        } else if (seekProgress >= 22 && seekProgress < 31) {
            seekBar.setProgress(27);
        } else if (seekProgress >= 31 && seekProgress < 41) {
            seekBar.setProgress(37);
        } else if (seekProgress >= 41 && seekProgress < 53) {
            seekBar.setProgress(48);
        } else if (seekProgress >= 53 && seekProgress < 65) {
            seekBar.setProgress(60);
        } else if (seekProgress >= 65 && seekProgress < 78) {
            seekBar.setProgress(72);
        } else if (seekProgress >= 78 && seekProgress < 92) {
            seekBar.setProgress(86);
        } else {
            seekBar.setProgress(100);
        }
    }


    public void onStopSeekBar150(SeekBar seekBar) {
        int seekProgress = seekBar.getProgress();
        if (seekProgress < 6) {
            seekBar.setProgress(0);
        } else if (seekProgress >= 6 && seekProgress < 19) {
            seekBar.setProgress(12);
        } else if (seekProgress >= 19 && seekProgress < 33) {
            seekBar.setProgress(25);
        } else if (seekProgress >= 33 && seekProgress < 47) {
            seekBar.setProgress(39);
        } else if (seekProgress >= 47 && seekProgress < 63) {
            seekBar.setProgress(55);
        } else if (seekProgress >= 63 && seekProgress < 80) {
            seekBar.setProgress(71);
        } else if (seekProgress >= 80 && seekProgress < 98) {
            seekBar.setProgress(89);
        } else if (seekProgress >= 98 && seekProgress < 118) {
            seekBar.setProgress(108);
        } else if (seekProgress >= 118 && seekProgress < 139) {
            seekBar.setProgress(128);
        } else {
            seekBar.setProgress(149);
        }
    }


    public Drawable LayoutToDrawable(String string) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
        TextView tv = new TextView(this);
        tv.setText(string);
        tv.setBackgroundResource(R.drawable.arrow);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setTextColor(Color.WHITE);
        Bitmap snapshot = convertViewToBitmap(tv);
        Drawable drawable = new BitmapDrawable(snapshot);

        return drawable;

    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, 80, 80);  //根据字符串的长度显示view的宽度
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();

        if (event.getRepeatCount() != 0 || action != KeyEvent.ACTION_DOWN) {
            return super.onKeyDown(keyCode, event);
        }

        //LogUtils.Sinya("KeyCode: " + keyCode + "   event: " + event.getRepeatCount());
        int progress = 0;
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_F6:
                //旋钮右转;
                progress = seekBar.getProgress() + 1;
                if (progress >= maxValue) {
                    progress = maxValue;
                }
                seekBar.setProgress(progress);
                break;

            case KeyEvent.KEYCODE_F7:
                //旋钮左转
                progress = seekBar.getProgress() - 1;
                if (progress <= minValue) {
                    progress = minValue;
                }
                seekBar.setProgress(progress);
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }


}
