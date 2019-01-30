package jp.sinya.mvpdemo2.simple.demo8.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者: Dream on 2017/8/28 22:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseActivity_8<V extends BaseView_8, P extends BasePresenter_8<V>> extends Activity {

    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        view = createView();
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        presenter.attachView(view);
    }

    public abstract P createPresenter();

    public abstract V createView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
