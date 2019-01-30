package jp.sinya.mvpdemo2.simple.demo13.support.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.sinya.mvpdemo2.simple.demo13.MvpCallback_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;


/**
 * 作者: Dream on 2017/8/29 22:40
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//
public abstract class MvpActivity_13<V extends MvpView_13, P extends MvpPresenter_13<V>> extends Activity implements MvpCallback_13<V, P> {

    //持有目的对象引用
    private ActivityMvpDelegateImpl_13<V, P> delegate;

    private ActivityMvpDelegateImpl_13<V, P> getDelegate() {
        if (delegate == null) {
            this.delegate = new ActivityMvpDelegateImpl_13<V, P>(this);
        }
        return delegate;
    }

    private P presenter;
    private V view;

    @Override
    public P createPresenter() {
        return this.presenter;
    }

    @Override
    public V createView() {
        return this.view;
    }

    @Override
    public P getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public V getMvpView() {
        return this.view;
    }

    @Override
    public void setMvpView(V view) {
        this.view = view;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDelegate().onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getDelegate().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }
}
