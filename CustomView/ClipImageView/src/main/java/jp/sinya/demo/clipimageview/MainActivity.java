package jp.sinya.demo.clipimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LoadingClipImageView clipImageView;
    private TextView tvPercent;
    private int percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        clipImageView = findViewById(R.id.clip_image_view);
        clipImageView.setMaskOrientation(LoadingClipImageView.MaskOrientation.LeftToRight);
        tvPercent = findViewById(R.id.tv_percent);
    }


    public void click(View view) {
        percent += 10;
        if (percent > 100) {
            percent = 100;
            return;
        }
        //percentImageView.setPercent(percent);
        clipImageView.setProgress(percent);
        tvPercent.setText("当前比例" + percent + "%");
    }

    public void click2(View view) {
        percent -= 10;
        if (percent < 0) {
            percent = 0;
            return;
        }
        //percentImageView.setPercent(percent);
        clipImageView.setProgress(percent);
        tvPercent.setText("当前比例" + percent + "%");
    }

}
