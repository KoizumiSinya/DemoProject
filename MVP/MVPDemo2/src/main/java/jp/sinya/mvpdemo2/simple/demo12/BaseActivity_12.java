package jp.sinya.mvpdemo2.simple.demo12;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.sinya.mvpdemo2.simple.demo12.impl.MvpCallback;
import jp.sinya.mvpdemo2.simple.demo12.impl.MvpImpl;
import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/28 22:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseActivity_12<V extends BaseView_8, P extends BasePresenter_8<V>> extends Activity implements MvpCallback<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpImpl = new MvpImpl<V, P>(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mvpImpl.onDestroy();
    }
}
