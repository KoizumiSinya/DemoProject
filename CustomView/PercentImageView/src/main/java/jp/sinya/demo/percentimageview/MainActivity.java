package jp.sinya.demo.percentimageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int percent;
    private TextView tvPercent;
    private PercentImageView percentImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvPercent = findViewById(R.id.tv_percent);
        percentImageView = findViewById(R.id.img_percent);
        percent = percentImageView.getPercent();
        tvPercent.setText("当前比例" + percent + "%");
    }

    public void click(View view) {
        percent += 10;
        if (percent > 100) {
            percent = 100;
            return;
        }
        //percentImageView.setPercent(percent);
        percentImageView.setPercentAnim(percent);
        tvPercent.setText("当前比例" + percent + "%");
    }

    public void click2(View view) {
        percent -= 10;
        if (percent < 0) {
            percent = 0;
            return;
        }
        //percentImageView.setPercent(percent);
        percentImageView.setPercentAnim(percent);
        tvPercent.setText("当前比例" + percent + "%");
    }


}
