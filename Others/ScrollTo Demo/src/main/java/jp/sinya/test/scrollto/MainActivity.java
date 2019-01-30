package jp.sinya.test.scrollto;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private int xValue = 0;
    private int yValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.activity_main_tv_content);
    }

    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.activity_main_tv_content:
                Toast.makeText(this, "点击Text", Toast.LENGTH_SHORT).show();
                break;

            case R.id.activity_main_btn_left:
                xValue += 10;
                textView.scrollTo(xValue, yValue);
                break;

            case R.id.activity_main_btn_top:
                textView.scrollTo(xValue, yValue);
                yValue += 10;
                break;

            case R.id.activity_main_btn_right:
                xValue -= 10;
                textView.scrollTo(xValue, yValue);
                break;

            case R.id.activity_main_btn_bottom:
                yValue -= 10;
                textView.scrollTo(xValue, yValue);
                break;

            case R.id.activity_main_btn_amin_start:
                //向右
                ObjectAnimator.ofFloat(textView, "translationX", 0, 300).setDuration(500).start();
                break;

            case R.id.activity_main_btn_params_scroll:
                //向上
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
                params.bottomMargin += 10;
                textView.requestLayout();
                break;

            default:
                break;
        }

    }
}
