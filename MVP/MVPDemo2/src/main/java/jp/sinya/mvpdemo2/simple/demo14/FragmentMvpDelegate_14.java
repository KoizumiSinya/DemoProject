package jp.sinya.mvpdemo2.simple.demo14;

import android.os.Bundle;
import android.view.View;

import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;


/**
 * 作者: Dream on 2017/8/30 20:52
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public interface FragmentMvpDelegate_14<V extends MvpView_13, P extends MvpPresenter_13<V>> {

    void onCreate(Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroyView();

    void onDestroy();

}
