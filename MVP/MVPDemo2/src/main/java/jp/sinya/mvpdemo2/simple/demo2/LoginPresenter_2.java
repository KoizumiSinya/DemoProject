package jp.sinya.mvpdemo2.simple.demo2;


import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_2 {

    private LoginModel_2 loginModel;
    private LoginView_2 loginView;

    public LoginPresenter_2() {
        this.loginModel = new LoginModel_2();
    }

    public void attachView(LoginView_2 loginView) {
        this.loginView = loginView;
    }

    public void detachView() {
        this.loginView = null;
    }

    public void login(String username, String password) {
        this.loginModel.login(username, password, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (loginView != null) {
                    loginView.onLoginResult(result);
                }
            }
        });
    }

}
