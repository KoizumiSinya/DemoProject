package jp.sinya.mvpdemo2.simple.demo1;


import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_1 {

    private LoginModel_1 loginModel;
    private LoginView_1 loginView;

    public LoginPresenter_1(LoginView_1 loginView) {
        this.loginModel = new LoginModel_1();
        this.loginView = loginView;
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
