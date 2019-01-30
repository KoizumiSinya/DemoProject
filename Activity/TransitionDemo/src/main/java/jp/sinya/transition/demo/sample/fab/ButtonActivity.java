package jp.sinya.transition.demo.sample.fab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.method.ColorShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/30.
 * email : mr_immortalz@qq.com
 */

public class ButtonActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_fab_detail;
    }

    @Override
    protected void initViews() {
        TransitionsHelper.build(this).setShowMethod(new ColorShowMethod(R.color.bg_purple, R.color.bg_teal) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(//
                        ObjectAnimator.ofFloat(placeholder, "alpha", 1f, 0f), //
                        ObjectAnimator.ofFloat(placeholder, "scaleX", 1f, 0f), //
                        ObjectAnimator.ofFloat(placeholder, "scaleY", 1f, 0f));
                set.setInterpolator(new AccelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }
                });
                set.setDuration(showDuration / 4 * 5).start();
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {

            }
        }).setExposeColor(getResources().getColor(R.color.bg_teal)).show();
    }

}
