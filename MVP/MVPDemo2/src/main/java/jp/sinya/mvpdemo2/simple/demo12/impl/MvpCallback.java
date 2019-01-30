package jp.sinya.mvpdemo2.simple.demo12.impl;


import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/29 21:28
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public interface MvpCallback<V extends BaseView_8, P extends BasePresenter_8<V>> {

    P createPresenter();

    V createView();

}
