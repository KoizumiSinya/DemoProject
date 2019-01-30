package jp.sinya.mvpdemo2.simple.demo4.base;

/**
 * 作者: Dream on 2017/8/28 22:30
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BasePresenter_4 {

    private BaseView_4 loginView;

    public BaseView_4 getLoginView() {
        return loginView;
    }

    public void attachView(BaseView_4 loginView){
        this.loginView = loginView;
    }

    public void detachView(){
        this.loginView = null;
    }

}
