package jp.sinya.mvpdemo2.simple.demo13;


import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;

/**
 * 作者: Dream on 2017/8/29 22:43
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//代理对象：代理实现（MvpCallbackProxy）
public class MvpCallbackProxy_13<V extends MvpView_13, P extends MvpPresenter_13<V>> implements MvpCallback_13<V, P> {

    //持有目标对象引用
    private MvpCallback_13<V, P> callback;

    public MvpCallbackProxy_13(MvpCallback_13<V, P> callback) {
        this.callback = callback;
    }

    @Override
    public P createPresenter() {
        P presenter = this.callback.getPresenter();
        if (presenter == null) {
            presenter = this.callback.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        this.callback.setPresenter(presenter);
        return presenter;
    }

    @Override
    public V createView() {
        V view = this.callback.getMvpView();
        if (view == null) {
            view = this.callback.createView();
        }
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        this.callback.setMvpView(view);
        return view;
    }

    @Override
    public P getPresenter() {
        P presenter = this.callback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.callback.setPresenter(presenter);
    }

    @Override
    public V getMvpView() {
        V view = this.callback.getMvpView();
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        return view;
    }

    @Override
    public void setMvpView(V view) {
        this.callback.setMvpView(view);
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
    }

}
