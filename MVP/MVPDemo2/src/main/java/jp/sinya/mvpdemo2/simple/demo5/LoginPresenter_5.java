package jp.sinya.mvpdemo2.simple.demo5;


import jp.sinya.mvpdemo2.simple.demo5.base.BasePresenter_5;
import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_5 extends BasePresenter_5<LoginView_5> {

    private LoginModel_5 loginModel;

    public LoginPresenter_5() {
        this.loginModel = new LoginModel_5();
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
