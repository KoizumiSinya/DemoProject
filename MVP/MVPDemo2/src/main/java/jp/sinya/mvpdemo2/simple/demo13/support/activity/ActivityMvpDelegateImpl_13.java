package jp.sinya.mvpdemo2.simple.demo13.support.activity;

import android.os.Bundle;

import jp.sinya.mvpdemo2.simple.demo13.MvpCallbackProxy_13;
import jp.sinya.mvpdemo2.simple.demo13.MvpCallback_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;

/**
 * 作者: Dream on 2017/8/29 22:55
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//目标对象：具体的实现->生命周期实现
public class ActivityMvpDelegateImpl_13<V extends MvpView_13, P extends MvpPresenter_13<V>> implements ActivityMvpDelegate_13<V, P> {

    private MvpCallback_13<V, P> callback;
    private MvpCallbackProxy_13<V, P> callbackProxy;

    public ActivityMvpDelegateImpl_13(MvpCallback_13<V, P> callback) {
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
        //绑定实现
        //回调
        getCallbackProxy().createPresenter();
        getCallbackProxy().createView();
        getCallbackProxy().attachView();
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
        getCallbackProxy().detachView();
    }

}
