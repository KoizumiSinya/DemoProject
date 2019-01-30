package jp.sinya.mvpdemo3.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import jp.sinya.mvpdemo3.mvp.proxy.Mvp;
import jp.sinya.mvpdemo3.mvp.proxy.MvpImpl;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 16:08
 * @edithor
 * @date
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment implements Mvp<V, P> {
    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mvpImpl.onDestroy();
    }

    private void init() {
        mvpImpl = new MvpImpl<>(this);
    }
}
