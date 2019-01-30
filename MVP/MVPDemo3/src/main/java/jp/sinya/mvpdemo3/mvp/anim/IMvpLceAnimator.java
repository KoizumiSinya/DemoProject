package jp.sinya.mvpdemo3.mvp.anim;

import android.view.View;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 13:24
 * @edithor
 * @date
 */
public interface IMvpLceAnimator {
    void showLoadingView(View loadView, View contentView, View errorView);

    void showContentView(View loadView, View contentView, View errorView);

    void showErrorView(View loadView, View contentView, View errorView);
}
