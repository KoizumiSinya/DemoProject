package jp.sinya.drawerlayout.demo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

public class MainActivity extends AppCompatActivity {

    private SlidingDrawer slidingDrawer;
    private ImageView imgArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);

        imgArrow = findViewById(R.id.img_arrow);
        slidingDrawer = findViewById(R.id.slide_drawer);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(imgArrow, "rotationY", 0.0F, 180F);
                anim.setDuration(300);
                anim.setInterpolator(new LinearInterpolator());
                anim.start();
            }
        });
        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(imgArrow, "rotationY", 180, 0F);
                anim.setDuration(300);
                anim.setInterpolator(new LinearInterpolator());
                anim.start();
            }
        });
    }

}
