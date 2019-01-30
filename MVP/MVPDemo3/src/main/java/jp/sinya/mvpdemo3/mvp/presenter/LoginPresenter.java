package jp.sinya.mvpdemo3.mvp.presenter;

import jp.sinya.mvpdemo3.mvp.model.Login;
import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.login.LoginView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/21. 12:15
 * @edithor
 * @date
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private Login login;

    public LoginPresenter() {
        this.login = new Login();
    }

    public void login(String name, String password) {
        /**
         * 在这里处理登陆的请求代码，并且登陆成功之后回调 登陆成功
         */
        getMvpView().onLoginResponse(new UserConfig());
    }

}
