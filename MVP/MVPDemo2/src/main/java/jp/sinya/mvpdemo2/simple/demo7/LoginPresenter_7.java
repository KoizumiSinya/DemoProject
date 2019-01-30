package jp.sinya.mvpdemo2.simple.demo7;


import jp.sinya.mvpdemo2.simple.demo7.base.BasePresenter_7;
import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_7 extends BasePresenter_7<LoginView_7> {

    private LoginModel_7 loginModel;

    public LoginPresenter_7() {
        this.loginModel = new LoginModel_7();
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
