package jp.sinya.mvpdemo2.simple.demo13.login;


import jp.sinya.mvpdemo2.simple.demo13.base.impl.MvpBasePresenter_13;
import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_13 extends MvpBasePresenter_13<LoginView_13> {

    private LoginModel_13 loginModel;

    public LoginPresenter_13() {
        this.loginModel = new LoginModel_13();
    }

    public void login(String username, String password) {
        this.loginModel.login(username, password, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (getView() != null) {
                    getView().onLoginResult(result);
                }
            }
        });
    }

}
