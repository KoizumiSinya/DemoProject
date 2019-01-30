package jp.sinya.mvpdemo2.simple.demo6.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.sinya.mvpdemo2.simple.demo6.LoginPresenter_6;
import jp.sinya.mvpdemo2.simple.demo6.LoginView_6;

/**
 * 作者: Dream on 2017/8/28 22:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseActivity_6 extends Activity {

    private LoginPresenter_6 presenter;

    public LoginPresenter_6 getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        presenter = createPresenter();
        presenter.attachView(createView());
    }

    public abstract LoginPresenter_6 createPresenter();

    public abstract LoginView_6 createView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
