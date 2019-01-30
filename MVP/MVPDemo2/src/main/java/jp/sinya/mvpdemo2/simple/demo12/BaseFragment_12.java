package jp.sinya.mvpdemo2.simple.demo12;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import jp.sinya.mvpdemo2.simple.demo12.impl.MvpCallback;
import jp.sinya.mvpdemo2.simple.demo12.impl.MvpImpl;
import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/29 20:55
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseFragment_12<V extends BaseView_8, P extends BasePresenter_8<V>> extends Fragment implements MvpCallback<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mvpImpl = new MvpImpl<V, P>(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mvpImpl.onDestroy();
    }
}
