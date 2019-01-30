package jp.sinya.mvpdemo2.simple.demo12.impl;


import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/29 21:24
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//如何抽象？
public class MvpImpl<V extends BaseView_8, P extends BasePresenter_8<V>> {

    private P presenter;
    private V view;

    private MvpCallback<V, P> callback;

    public P getPresenter() {
        return presenter;
    }

    public MvpImpl(MvpCallback<V, P> callback) {
        this.callback = callback;
        init();
    }

    private void init() {
        presenter = this.callback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        view = this.callback.createView();
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        presenter.attachView(view);
    }

    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
