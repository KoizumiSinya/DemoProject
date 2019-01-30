package jp.sinya.shareelementanimation.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

    public void start(View view) {
        Intent intent = new Intent(this, ShareSecondActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,//
                new Pair<>(findViewById(R.id.activity_share_img), "img"));

        //Slide slide = new Slide();
        //slide.setDuration(500);
        //getWindow().setEnterTransition(slide);
        startActivity(intent, optionsCompat.toBundle());
    }

    /**
     * 元素共享动画，
     * 1. 如果两个activity什么都不设置，将会默认是平缓的移动/放大到指定的位置
     *
     */
}
