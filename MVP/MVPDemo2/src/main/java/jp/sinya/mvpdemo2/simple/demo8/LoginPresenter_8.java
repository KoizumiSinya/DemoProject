package jp.sinya.mvpdemo2.simple.demo8;


import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_8 extends BasePresenter_8<LoginView_8> {

    private LoginModel_8 loginModel;

    public LoginPresenter_8() {
        this.loginModel = new LoginModel_8();
    }

    public void login(String username, String password) {
        this.loginModel.login(username, password, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (getLoginView() != null) {
                    getLoginView().onLoginResult(result);
                }
            }
        });
    }

}
