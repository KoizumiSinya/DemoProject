package jp.sinya.mvpdemo3.mvp.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.view.base.BaseMvpFragment;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.LoginPresenter;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 12:09
 * @edithor
 * @date
 */
public class LoginFragment2 extends BaseMvpFragment<LoginView, LoginPresenter> implements LoginView {

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView createMvpView() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    public void login(View view) {
        getPresenter().login("Sinya", "123456");
    }

    @Override
    public void onLoginResponse(UserConfig config) {
        //登陆返回
    }
}
