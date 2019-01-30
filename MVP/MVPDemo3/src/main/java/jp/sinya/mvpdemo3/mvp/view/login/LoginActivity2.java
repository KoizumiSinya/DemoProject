package jp.sinya.mvpdemo3.mvp.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.view.base.BaseMvpActivity;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.LoginPresenter;

/**
 * ˚
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 17:31
 * @edithor
 * @date
 */
public class LoginActivity2 extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected LoginView initMvpView() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLoginResponse(UserConfig config) {
        //登陆返回
    }

    public void login(View view) {
        getPresenter().login("Sinya", "123456");
    }

}
