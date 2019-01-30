package jp.sinya.transition.demo.sample.recyclerview;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.method.InflateShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvDetailActivity extends BaseActivity {

    ImageView ivDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview_detail;
    }

    @Override
    protected void initViews() {
        ivDetail = findViewById(R.id.iv_detail);

        TransitionsHelper.build(this).setShowMethod(new InflateShowMethod(this, R.layout.activity_rv_inflate) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                Glide.with(RvDetailActivity.this).load(bean.getLoad()).fitCenter().into(placeholder);
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {
                Glide.with(RvDetailActivity.this).load(bean.getLoad()).fitCenter().into((ImageView) targetView);
            }
        }).setExposeColor(getResources().getColor(R.color.bg_teal)).intoTargetView(ivDetail).show();
    }

}
