package jp.sinya.mvpdemo2.simple.demo5.base;

/**
 * 作者: Dream on 2017/8/28 22:30
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BasePresenter_5<V extends BaseView_5> {

    private V loginView;

    public V getLoginView() {
        return loginView;
    }

    public void attachView(V loginView) {
        this.loginView = loginView;
    }

    public void detachView() {
        this.loginView = null;
    }

}
