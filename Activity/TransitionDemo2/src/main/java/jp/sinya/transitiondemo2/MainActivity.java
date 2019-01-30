package jp.sinya.transitiondemo2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        tv = findViewById(R.id.activity_main_tv);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startAnim2(v, (int) v.getX(), (int)v.getY());
//            }
//        });
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                startAnim2(tv, (int) e.getX(), (int) e.getY());
                transition3(tv, (int) e.getX(), (int) e.getY());
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void startAnim(View view) {
        //缩放X 轴的
        ObjectAnimator revealAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        //缩放Y 轴的
        ObjectAnimator revealAnimator1 = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);//设置播放时间
        set.setInterpolator(new LinearInterpolator());//设置播放模式，这里是平常模式
        set.playTogether(revealAnimator, revealAnimator1);//设置一起播放
        set.start();
    }

    private void startAnim2(View view, int x, int y) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view,//作用在哪个View上面
                x, //扩散的中心点
                y,//扩散的中心点
                0,//开始扩散初始半径
                view.getHeight());//扩散结束半径
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void startAnim3(View view) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view,//作用于view
                0, 0, //扩散中心点：左上角
                0,//扩散开始半径
                (float) Math.hypot(view.getWidth(), view.getHeight()));//结束半径
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }


    private void transition1() {
        // 系统默认  必须在 StartActivity() 或 finish() 之后立即调用；而且在 2.1 以上版本有效
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_top_out);
    }

    private void transition2() {
        //不共享元素
       /* ActivityOptionsCompat.makeSceneTransitionAnimation();
        ActivityOptionsCompat.makeThumbnailScaleUpAnimation();
        ActivityOptionsCompat.makeCustomAnimation();
        ActivityOptionsCompat.makeScaleUpAnimation();
        ActivityOptionsCompat.makeClipRevealAnimation();*/

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent, optionsCompat.toBundle());
    }

    private void transition3(View view, int x, int y) {
        //让新的Activity从一个小的范围(比如第一个activity的一个按钮)扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, x, y, //拉伸开始的坐标
                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent, options.toBundle());
    }

    private void transition4(View view) {
        //类似于：overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out); 还不如直接用这个全版本的overridePendingTransition
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this, R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent, options.toBundle());
    }

    private void transition5(View view) {
        //共享一个元素
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view, "mybtn1").toBundle();
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent, bundle);
    }

    private void transition6(View view1, View view2) {
        //共享多个元素
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(//
                MainActivity.this, //
                Pair.create(view1, "mybtn1"), //
                Pair.create(view2, "mybtn2")).toBundle();
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent, bundle);
    }

    private void startActivity(View view) {


    }
}
