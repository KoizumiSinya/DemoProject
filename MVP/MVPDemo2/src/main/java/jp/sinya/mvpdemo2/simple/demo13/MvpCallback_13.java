package jp.sinya.mvpdemo2.simple.demo13;


import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;

/**
 * 作者: Dream on 2017/8/29 22:23
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//第一个代理->针对->MVP
//MVP绑定和解绑->目标接口
public interface MvpCallback_13<V extends MvpView_13, P extends MvpPresenter_13<V>> {

    //创建P层
    P createPresenter();

    //创建V层
    V createView();

    //得到P层
    P getPresenter();

    //设置P层
    void setPresenter(P presenter);

    //得到V层
    V getMvpView();

    //设置V层
    void setMvpView(V view);
}
