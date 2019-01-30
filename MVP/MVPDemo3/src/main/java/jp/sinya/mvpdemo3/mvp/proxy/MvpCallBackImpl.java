package jp.sinya.mvpdemo3.mvp.proxy;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 18:52
 * @edithor
 * @date
 */
public class MvpCallBackImpl<V extends BaseView, P extends BasePresenter<V>> implements MvpCallBack<V, P> {

    private MvpCallBack<V, P> callBack;

    public MvpCallBackImpl(MvpCallBack<V, P> callBack) {
        this.callBack = callBack;
    }

    @Override
    public P createPresenter() {
        P presenter = callBack.getPresenter();
        if (presenter == null) {
            presenter = callBack.createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("initPresenter is null...");
        }
        callBack.setPresenter(presenter);
        return presenter;
    }

    @Override
    public V createMvpView() {
        V view = callBack.getMvpView();
        if (view == null) {
            view = callBack.createMvpView();
        }
        if (view == null) {
            throw new NullPointerException("view is null...");
        }
        callBack.setMvpView(view);
        return view;
    }

    @Override
    public P getPresenter() {
        P presenter = callBack.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("initPresenter is null...");
        }
        return presenter;
    }

    @Override
    public V getMvpView() {
        V view = callBack.getMvpView();
        if (view == null) {
            throw new NullPointerException("view is null...");
        }
        return view;
    }

    @Override
    public void setPresenter(P presenter) {
        callBack.setPresenter(presenter);
    }

    @Override
    public void setMvpView(V view) {
        callBack.setMvpView(view);
    }

    public void attachView() {
        getPresenter().attachView(getMvpView());
    }

    public void detachView() {
        getPresenter().detachView();
    }
}
