package jp.sinya.mvpdemo2.simple.demo10.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;


/**
 * 作者: Dream on 2017/8/29 20:55
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseFragment_8<V extends BaseView_8, P extends BasePresenter_8<V>> extends Fragment {

    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        view = createView();
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        presenter.attachView(view);
    }

    public abstract P createPresenter();

    public abstract V createView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
