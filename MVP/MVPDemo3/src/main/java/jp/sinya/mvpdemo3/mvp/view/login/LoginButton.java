package jp.sinya.mvpdemo3.mvp.view.login;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import jp.sinya.mvpdemo3.mvp.view.base.BaseButton;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.LoginPresenter;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 17:19
 * @edithor
 * @date
 */
public class LoginButton extends BaseButton<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {
    public LoginButton(Context context) {
        super(context);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
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
        //登陆返回
    }

    @Override
    public void onClick(View v) {
        getPresenter().login("Sinya", "123456");
    }
}
