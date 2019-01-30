package jp.sinya.mvpdemo3.mvp.proxy.activity;

import android.os.Bundle;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * 这个接口用来对接 activity自身的生命周期，对其进行代理
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 18:47
 * @edithor
 * @date
 */
public interface ActivityMvpInterface<V extends BaseView, P extends BasePresenter<V>> {
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onRestart();

    void onStop();

    void onDestroy();
}
