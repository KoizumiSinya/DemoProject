package jp.sinya.mvpdemo2.simple.demo14.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import jp.sinya.mvpdemo2.simple.demo13.MvpCallback_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;


/**
 * 作者: Dream on 2017/8/30 20:48
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class MvpFragment_14<V extends MvpView_13, P extends MvpPresenter_13<V>> extends Fragment implements MvpCallback_13<V, P> {

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

    //持有目标对象引用
    //持有目的对象引用
    private FragmentMvpDelegateImpl_14<V, P> delegate;

    private FragmentMvpDelegateImpl_14<V, P> getDelegate() {
        if (delegate == null) {
            this.delegate = new FragmentMvpDelegateImpl_14<V, P>(this);
        }
        return delegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDelegate().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDelegate().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

}
