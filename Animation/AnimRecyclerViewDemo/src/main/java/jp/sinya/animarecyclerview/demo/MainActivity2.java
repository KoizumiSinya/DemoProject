package jp.sinya.animarecyclerview.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private LinearLayout llParent;
    private HorizontalScrollView scrollView;
    private TextView tvContent;

    private float childWidth = 80;
    private int stopPosition = 6;
    private boolean isStartAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvContent = findViewById(R.id.activity_main2_tv_position);
        llParent = findViewById(R.id.activity_main_ll);
        scrollView = findViewById(R.id.activity_main2_scroll);
        setContent();

        reset();
    }

    private void reset() {
        initView();
        scrollView.scrollTo(0, 0);
    }

    private void initView() {
        llParent.removeAllViews();

        //一共9个View， 第1个和第9个是空视图，占位
        for (int i = 0; i < 9; i++) {
            if (i == 0 || i == 8) {
                View view = new View(this);
                view.setLayoutParams(new LinearLayout.LayoutParams(screenWidth(this), ViewGroup.LayoutParams.MATCH_PARENT));
                llParent.addView(view);

            } else {
                View view = LayoutInflater.from(this).inflate(R.layout.item_layout, null);
                TextView tv = view.findViewById(R.id.item_layout_tv);
                String content = "Day " + i;
                tv.setText(content);
                llParent.addView(view);
            }
        }
    }

    private int getMaxScrollX() {
        //7个item的宽度
        int value = 7 * dp2px(this, childWidth) - screenWidth(this);
        return value;
    }

    private int getDistance(int stopPosition) {
        int position = stopPosition - 1;
        if (position < 0) {
            position = 0;
        }
        int itemDistance = (position) * dp2px(MainActivity2.this, childWidth);

        if (itemDistance >= getMaxScrollX()) {
            itemDistance = getMaxScrollX();
        }
        return itemDistance;
    }

    private void startEnterAnimation(final int stopPosition) {
        isStartAnim = true;

        final int distance = getDistance(stopPosition);
        SpringForce spring = new SpringForce(screenWidth(this) + distance) //
                .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY) //
                .setStiffness(SpringForce.STIFFNESS_LOW);

        SpringAnimation anim = new SpringAnimation(scrollView, DynamicAnimation.SCROLL_X).setSpring(spring);

        anim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b, float v, float v1) {
                llParent.removeViewAt(0);
                scrollView.scrollTo(distance, 0);
                llParent.removeViewAt(7);
                isStartAnim = false;
            }
        });

        anim.start();
    }

    public int screenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void start(View view) {
        if (isStartAnim) {
            return;
        }
        reset();
        /**
         *
         * 测试 动画结束后，停留在哪个item的位置上
         * position 0， 动画应该停止在0item上
         * position 1， 动画应该停止在0item上
         * position 2， 动画应该停止在1item上
         * ...类推
         *
         * 其它的情况视最终的可滑动区域而定
         */
        startEnterAnimation(stopPosition);
    }

    public void last(View view) {
        stopPosition--;
        if (stopPosition <= 0) {
            stopPosition = 0;
        }
        setContent();
    }

    public void next(View view) {
        stopPosition++;
        if (stopPosition >= 6) {
            stopPosition = 6;
        }
        setContent();
    }

    private void setContent() {
        tvContent.setText("选中 Day: " + (stopPosition + 1) + "，但动画停止在position: " + (stopPosition - 1 < 0 ? 0 : stopPosition - 1) + " 上（特殊除外）");
    }

}
