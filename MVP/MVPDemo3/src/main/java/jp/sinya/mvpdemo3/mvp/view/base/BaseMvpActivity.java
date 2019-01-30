package jp.sinya.mvpdemo3.mvp.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.proxy.MvpCallBack;
import jp.sinya.mvpdemo3.mvp.proxy.activity.ActivityMvpImpl;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 22:07
 * @edithor
 * @date
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends Activity implements MvpCallBack<V, P> {

    private P presenter;
    private V view;

    private ActivityMvpImpl<V, P> mvpImpl;

    public ActivityMvpImpl<V, P> getMvpImpl() {
        if (mvpImpl == null) {
            mvpImpl = new ActivityMvpImpl<>(this);
        }
        return mvpImpl;
    }

    protected abstract int initLayout();

    protected abstract P initPresenter();

    protected abstract V initMvpView();

    @Override
    public P createPresenter() {
        return initPresenter();
    }

    @Override
    public V createMvpView() {
        return initMvpView();
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        getMvpImpl().onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpImpl().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpImpl().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpImpl().onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpImpl().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpImpl().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpImpl().onDestroy();
    }
}
