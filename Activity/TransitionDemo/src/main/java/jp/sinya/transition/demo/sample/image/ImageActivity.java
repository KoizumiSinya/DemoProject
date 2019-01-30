package jp.sinya.transition.demo.sample.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/27.
 * email : mr_immortalz@qq.com
 */

public class ImageActivity extends BaseActivity {


    ImageView iv1;

    String imgUrl = "http://imga.mumayi.com/android/wallpaper/2012/01/02/sl_600_2012010206150877826134.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(imgUrl).centerCrop().into(iv1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initViews() {
        iv1 = findViewById(R.id.iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHelper.startActivity(ImageActivity.this, ImageDetailActivity.class, iv1, imgUrl);
            }
        });
    }

}
