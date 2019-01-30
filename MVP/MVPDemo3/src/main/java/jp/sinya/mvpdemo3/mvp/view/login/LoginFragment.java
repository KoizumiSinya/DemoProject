package jp.sinya.mvpdemo3.mvp.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.view.base.BaseFragment;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.LoginPresenter;

/**
 * 编写一个Fragment 来实现LoginView
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 15:50
 * @edithor
 * @date
 */
public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }


    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView createMvpView() {
        return this;
    }

    @Override
    public void onLoginResponse(UserConfig config) {
        //登陆返回结果
    }

    public void login(View v) {
        getPresenter().login("Sinya", "123456");
    }


}
