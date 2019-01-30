package jp.sinya.rotateanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.activity_main_img);
        linearLayout = findViewById(R.id.activity_main_ll);


    }

    public void start(View view) {
        MyYAnimation myYAnimation = new MyYAnimation();
        myYAnimation.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
        imageView.startAnimation(myYAnimation);
    }
}
