package jp.sinya.mvpdemo2.simple.demo14.impl;

import android.os.Bundle;
import android.view.View;

import jp.sinya.mvpdemo2.simple.demo13.MvpCallbackProxy_13;
import jp.sinya.mvpdemo2.simple.demo13.MvpCallback_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;
import jp.sinya.mvpdemo2.simple.demo14.FragmentMvpDelegate_14;

/**
 * 作者: Dream on 2017/8/30 20:54
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//目标对象
public class FragmentMvpDelegateImpl_14<V extends MvpView_13, P extends MvpPresenter_13<V>> implements FragmentMvpDelegate_14<V, P> {

    //实现功能->绑定UI层和接触绑定
    private MvpCallback_13<V, P> callback;
    private MvpCallbackProxy_13<V, P> callbackProxy;

    public FragmentMvpDelegateImpl_14(MvpCallback_13<V, P> callback) {
        this.callback = callback;
    }

    private MvpCallbackProxy_13<V, P> getCallbackProxy() {
        //代理对象
        if (callback != null) {
            this.callbackProxy = new MvpCallbackProxy_13<V, P>(callback);
        }
        return this.callbackProxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //绑定实现
        //回调
        getCallbackProxy().createPresenter();
        getCallbackProxy().createView();
        getCallbackProxy().attachView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
    public void onStop() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {
        getCallbackProxy().detachView();
    }
}
