package jp.sinya.transition.demo.sample.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.method.ColorShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FDetailActivity extends BaseActivity {
    LinearLayout container;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_detail;
    }

    @Override
    protected void initViews() {
        container = findViewById(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailFragment.newInstance()).commit();

        TransitionsHelper.build(this).setShowMethod(new ColorShowMethod(R.color.bg_purple, R.color.bg_teal) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(placeholder, "rotation", 0, 180), ObjectAnimator.ofFloat(placeholder, "scaleX", 1, 0), ObjectAnimator.ofFloat(placeholder, "scaleY", 1, 0));
                set.setInterpolator(new AccelerateInterpolator());
                set.setDuration(showDuration / 4 * 5).start();
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {

            }
        }).setExposeColor(getResources().getColor(R.color.bg_teal)).show();

    }
}
