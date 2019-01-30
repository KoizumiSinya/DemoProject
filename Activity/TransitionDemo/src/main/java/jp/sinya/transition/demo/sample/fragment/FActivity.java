package jp.sinya.transition.demo.sample.fragment;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FActivity extends BaseActivity {
    FloatingActionButton btnCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initViews() {
        btnCommit = findViewById(R.id.btn_circle);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHelper.startActivity(FActivity.this, FDetailActivity.class, btnCommit);
            }
        });

    }

}
