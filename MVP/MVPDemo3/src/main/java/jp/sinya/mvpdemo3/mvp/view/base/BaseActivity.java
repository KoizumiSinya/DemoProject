package jp.sinya.mvpdemo3.mvp.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.sinya.mvpdemo3.mvp.proxy.Mvp;
import jp.sinya.mvpdemo3.mvp.proxy.MvpImpl;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * 初次对P的接入的 BaseActivity
 *
 * @author Koizumi Sinya
 * @date 2018/01/21. 12:55
 * @edithor
 * @date
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends Activity implements Mvp<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mvpImpl = new MvpImpl<>(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpImpl.onDestroy();
    }


}
