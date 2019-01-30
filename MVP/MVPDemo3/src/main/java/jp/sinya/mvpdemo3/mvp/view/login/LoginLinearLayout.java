package jp.sinya.mvpdemo3.mvp.view.login;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import jp.sinya.mvpdemo3.mvp.view.base.BaseLinearLayout;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.LoginPresenter;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 17:02
 * @edithor
 * @date
 */
public class LoginLinearLayout extends BaseLinearLayout<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {

    public LoginLinearLayout(Context context) {
        super(context);
    }

    public LoginLinearLayout(Context context, @Nullable AttributeSet attrs) {
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
    public void onClick(View v) {
        getPresenter().login("Sinya", "123456");
    }

    @Override
    public void onLoginResponse(UserConfig config) {

    }
}
