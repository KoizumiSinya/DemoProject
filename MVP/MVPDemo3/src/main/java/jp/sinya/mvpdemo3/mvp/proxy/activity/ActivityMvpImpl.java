package jp.sinya.mvpdemo3.mvp.proxy.activity;

import android.os.Bundle;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBack;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBackImpl;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 18:43
 * @edithor
 * @date
 */
public class ActivityMvpImpl<V extends BaseView, P extends BasePresenter<V>> implements ActivityMvpInterface<V, P> {

    private MvpCallBack<V, P> callBack;
    private MvpCallBackImpl<V, P> impl;

    public ActivityMvpImpl(MvpCallBack<V, P> callBack) {
        this.callBack = callBack;
    }

    public MvpCallBackImpl<V, P> getImpl() {
        if (callBack != null) {
            impl = new MvpCallBackImpl<>(callBack);
        }
        return impl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getImpl().createPresenter();
        getImpl().createMvpView();
        getImpl().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getImpl().detachView();
    }
}
