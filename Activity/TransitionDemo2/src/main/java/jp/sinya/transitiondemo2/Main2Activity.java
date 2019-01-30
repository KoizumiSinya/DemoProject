package jp.sinya.transitiondemo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //设置允许使用转场动画
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


//        滑动进入
//        Slide slide = new Slide();
//        slide.setDuration(2000);
//        getWindow().setExitTransition(slide);//出去1动画
//        getWindow().setEnterTransition(slide);//进来的动画

//        分解
//        Explode explode = new Explode();
//        explode.setDuration(2000);
//        getWindow().setExitTransition(explode);//出去的动画
//        getWindow().setEnterTransition(explode);//进来的动画

//        淡入淡出
//        Fade fade = new Fade();
//        fade.setDuration(2000);
//        getWindow().setExitTransition(fade);//出去的动画
//        getWindow().setEnterTransition(fade);//进来的动画
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        //overridePendingTransition(0, R.anim.slide_bottom_out);
    }


}
