package jp.sinya.test.arcbar.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import jp.sinya.test.arcbar.R;
import jp.sinya.test.arcbar.widget.AutoChangeTextView;
import jp.sinya.test.arcbar.widget.SeekArc;

public class TargetDemoActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.target_progress_chart);

        init();
    }

    private void init() {

        RelativeLayout rl_continer = (RelativeLayout) findViewById(R.id.rl_continer);
        RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(screenWidth(this), screenWidth(this));
        rlParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl_continer.setLayoutParams(rlParam);

        SeekArc seekArcBig = (SeekArc) View.inflate(context, R.layout.item_arcbar_big, null);
        RelativeLayout.LayoutParams rlBigParam = new RelativeLayout.LayoutParams(screenWidth(this) - dip2px(50), screenWidth(this) - dip2px(50));
        rlBigParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        seekArcBig.setLayoutParams(rlBigParam);
        seekArcBig.setStartAngle(30);
        seekArcBig.setSweepAngle(300);
        seekArcBig.setPadding(dip2px(50), dip2px(50), dip2px(50), dip2px(50));
        seekArcBig.setArcRotation(180);
        seekArcBig.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        SeekArc seekArcSmall = (SeekArc) View.inflate(context, R.layout.item_arcbar_small, null);
        RelativeLayout.LayoutParams rlSmallParam = new RelativeLayout.LayoutParams(screenWidth(this) - dip2px(160), screenWidth(this) - dip2px(160));
        rlSmallParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        seekArcSmall.setLayoutParams(rlSmallParam);
        seekArcSmall.setStartAngle(30);
        seekArcSmall.setSweepAngle(300);
        seekArcSmall.setPadding(dip2px(50), dip2px(50), dip2px(50), dip2px(50));
        seekArcSmall.setArcRotation(180);

        seekArcSmall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        rl_continer.addView(seekArcBig);
        rl_continer.addView(seekArcSmall);

        AutoChangeTextView tv_targetprogress = (AutoChangeTextView) findViewById(R.id.tv_targetprogress);
        tv_targetprogress.showNumberWithAnimation(60.9f, 2000);

        AnimatorSet setGroup = new AnimatorSet();
        setGroup.playTogether(//
                ObjectAnimator.ofFloat(seekArcBig, "progress", 0.1f, 50.0f), //
                ObjectAnimator.ofFloat(seekArcSmall, "progress", 0.1f, 60.9f)//
        );
        setGroup.setDuration(2000).start();
    }


    /**
     * 获取 屏幕 宽度
     */
    private int screenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
