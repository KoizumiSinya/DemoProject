package jp.sinya.mvpdemo2.simple.demo13.base.impl;


import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;

/**
 * 作者: Dream on 2017/8/29 22:26
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class MvpBasePresenter_13<V extends MvpView_13> implements MvpPresenter_13<V> {

    private V view;

    public V getView() {
        return view;
    }

    public void attachView(V view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }

}
