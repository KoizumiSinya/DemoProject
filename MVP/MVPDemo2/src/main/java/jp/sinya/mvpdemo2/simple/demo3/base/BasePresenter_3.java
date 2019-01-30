package jp.sinya.mvpdemo2.simple.demo3.base;


import jp.sinya.mvpdemo2.simple.demo3.LoginView_3;

/**
 * 作者: Dream on 2017/8/28 22:30
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BasePresenter_3 {

    private LoginView_3 loginView;

    public LoginView_3 getLoginView() {
        return loginView;
    }

    public void attachView(LoginView_3 loginView) {
        this.loginView = loginView;
    }

    public void detachView() {
        this.loginView = null;
    }

}
