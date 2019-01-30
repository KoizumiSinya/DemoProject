package jp.sinya.mvpdemo3.mvp.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import jp.sinya.mvpdemo3.R;

/**
 * 用来实现页面加载三个状态的时候的动画实现类，可以根据不同的需求派生出不同的实现类
 *
 * @author Koizumi Sinya
 * @date 2018/01/23. 13:25
 * @edithor
 * @date
 */
public class MvpLceAnimatorImpl implements IMvpLceAnimator {
    @Override
    public void showLoadingView(View loadView, View contentView, View errorView) {
        Log.i("Sinya", "加载Loading");
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContentView(final View loadView, final View contentView, View errorView) {

        if (contentView.getVisibility() == View.VISIBLE) {
            Log.i("Sinya", "加载Content, 直接关闭load 和 error");
            errorView.setVisibility(View.GONE);
            loadView.setVisibility(View.GONE);
        } else {
            Log.i("Sinya", "加载Content, 关闭error，再通过动画开启content");
            errorView.setVisibility(View.GONE);

            //设置一个属性动画，作为过渡
            Resources resources = loadView.getResources();
            int translateInPixels = resources.getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y);

            AnimatorSet set = new AnimatorSet();
            final ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
            ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(contentView, "translationY", translateInPixels, 0);

            ObjectAnimator loadFadeOut = ObjectAnimator.ofFloat(loadView, "alpha", 1f, 0f);
            ObjectAnimator loadTranslateOut = ObjectAnimator.ofFloat(loadView, "translationY", 0, -translateInPixels);

            set.playTogether(contentFadeIn, contentTranslateIn, loadFadeOut, loadTranslateOut);
            set.setDuration(resources.getInteger(R.integer.lce_content_view_show_animation_time));
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    contentView.setTranslationY(0);
                    loadView.setTranslationY(0);
                    contentView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadView.setVisibility(View.GONE);
                    loadView.setAlpha(1f);
                    loadView.setTranslationY(0);
                    contentView.setTranslationY(0);
                }
            });

            set.start();
        }
    }

    @Override
    public void showErrorView(final View loadView, final View contentView, final View errorView) {
        Log.i("Sinya", "加载Error");

        contentView.setVisibility(View.GONE);

        //设置一个属性动画，作为过渡
        Resources resources = loadView.getResources();
        int translateInPixels = resources.getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y);

        AnimatorSet set = new AnimatorSet();
        final ObjectAnimator errorIn = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
        ObjectAnimator loadOut = ObjectAnimator.ofFloat(loadView, "alpha", 0f);

        set.playTogether(errorIn, loadOut);
        set.setDuration(resources.getInteger(R.integer.lce_content_view_show_animation_time));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                errorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                loadView.setVisibility(View.GONE);
                loadView.setAlpha(1f);
            }
        });

        set.start();
    }
}
