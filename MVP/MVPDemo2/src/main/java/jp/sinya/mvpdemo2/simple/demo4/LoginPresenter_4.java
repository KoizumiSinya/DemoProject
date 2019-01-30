package jp.sinya.mvpdemo2.simple.demo4;


import jp.sinya.mvpdemo2.simple.demo4.base.BasePresenter_4;
import jp.sinya.mvpdemo2.utils.HttpUtils;

/**
 * 作者: Dream on 2017/8/28 21:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层搞定了
//和V层进行交互->接口
public class LoginPresenter_4 extends BasePresenter_4 {

    private LoginModel_4 loginModel;

    public LoginPresenter_4() {
        this.loginModel = new LoginModel_4();
    }

    public void login(String username, String password) {
        this.loginModel.login(username, password, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                if (getLoginView() != null) {
                    LoginView_4 view = (LoginView_4) getLoginView();
                    view.onLoginResult(result);
                }
            }
        });
    }

}
