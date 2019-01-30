package jp.sinya.mvpdemo3.mvp.proxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBack;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBackImpl;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 11:52
 * @edithor
 * @date
 */
public class FragmentMvpImpl<V extends BaseView, P extends BasePresenter<V>> implements FragmentMvpInterface<V, P> {

    private MvpCallBack<V, P> callBack;
    private MvpCallBackImpl<V, P> impl;

    public FragmentMvpImpl(MvpCallBack<V, P> callBack) {
        this.callBack = callBack;
    }

    public MvpCallBackImpl<V, P> getImpl() {
        if (callBack != null) {
            impl = new MvpCallBackImpl<>(callBack);
        }
        return impl;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getImpl().createPresenter();
        getImpl().createMvpView();
        getImpl().attachView();
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
        getImpl().detachView();
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void setUserVisibleHint() {

    }
}
