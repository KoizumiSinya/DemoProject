package jp.sinya.mvpdemo2.simple.demo13.base;

/**
 * 作者: Dream on 2017/8/29 22:25
 * QQ:510278658
 * E-mail:510278658@qq.com
 */
//P层：高度抽象
public interface MvpPresenter_13<V extends MvpView_13> {

    void attachView(V view);

    void detachView();

}
