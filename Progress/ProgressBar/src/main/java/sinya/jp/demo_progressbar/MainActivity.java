package sinya.jp.demo_progressbar;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;


public class MainActivity extends Activity {
    private AnimationDrawable animDrawable;
    private Animation anin;

    private View bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.bar);
    }

    public void Click(View view) {
        switch (view.getId()) {

            //使用多张图片绘制动态图的方法，创建一个progressbar
            case R.id.btn1:

                bar.setVisibility(View.VISIBLE);
                bar.setBackgroundResource(R.drawable.anim_progress_01);

                animDrawable = (AnimationDrawable) bar.getBackground();
                animDrawable.start();
                break;

            case R.id.btn2:

                bar.setVisibility(View.VISIBLE);
                bar.setBackgroundResource(R.mipmap.progressbar_circle);

                anin = AnimationUtils.loadAnimation(this, R.anim.anim_progress_02);
                LinearInterpolator lator = new LinearInterpolator();
                anin.setInterpolator(lator);

                bar.startAnimation(anin);
                break;

            case R.id.btnCancel:
                if (animDrawable != null)
                    animDrawable.stop();
                bar.clearAnimation();
                bar.setVisibility(View.GONE);
                break;
        }
    }

}
