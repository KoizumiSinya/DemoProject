package jp.sinya.mvpdemo3.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBack;
import jp.sinya.mvpdemo3.mvp.proxy.fragment.FragmentMvpImpl;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 12:10
 * @edithor
 * @date
 */
public class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment implements MvpCallBack<V, P> {

    private P presenter;
    private V view;
    private FragmentMvpImpl<V, P> impl;

    public FragmentMvpImpl<V, P> getImpl() {
        if (impl == null) {
            impl = new FragmentMvpImpl<>(this);
        }
        return impl;
    }

    @Override
    public P createPresenter() {
        return presenter;
    }

    @Override
    public V createMvpView() {
        return view;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public V getMvpView() {
        return view;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setMvpView(V view) {
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getImpl().onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getImpl().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getImpl().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getImpl().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getImpl().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getImpl().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getImpl().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getImpl().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getImpl().onDestroy();
    }
}
