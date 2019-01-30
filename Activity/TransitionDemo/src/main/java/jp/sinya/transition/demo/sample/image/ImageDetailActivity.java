package jp.sinya.transition.demo.sample.image;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.method.ColorShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/28.
 * email : mr_immortalz@qq.com
 */

public class ImageDetailActivity extends BaseActivity {
    ImageView ivDetail;
    TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_imagedetail;
    }

    @Override
    protected void initViews() {
        ivDetail = findViewById(R.id.iv_detail);
        tv = findViewById(R.id.tv);

        TransitionsHelper.build(this).setShowMethod(new ColorShowMethod(R.color.bg_teal_light, R.color.bg_purple) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                Glide.with(ImageDetailActivity.this).load(bean.getLoad()).centerCrop().into(placeholder);
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {
                Glide.with(ImageDetailActivity.this).load(bean.getLoad()).centerCrop().into((ImageView) targetView);
                tv.setText("immortalz");
            }
        }).setExposeColor(getResources().getColor(R.color.bg_purple)).intoTargetView(ivDetail).show();
    }
}
