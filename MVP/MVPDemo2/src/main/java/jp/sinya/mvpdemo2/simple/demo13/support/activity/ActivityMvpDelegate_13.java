package jp.sinya.mvpdemo2.simple.demo13.support.activity;

import android.os.Bundle;

import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;

/**
 * 作者: Dream on 2017/8/29 22:51
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//目标接口：针对的是Activity目标接口->生命周期进行代理
public interface ActivityMvpDelegate_13<V extends MvpView_13, P extends MvpPresenter_13<V>> {

    public void onCreate(Bundle savedInstanceState);

    public void onStart();

    public void onPause();

    public void onResume();

    public void onRestart();

    public void onStop();

    public void onDestroy();

}
