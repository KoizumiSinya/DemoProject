package jp.sinya.mvpdemo2.simple.demo7.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者: Dream on 2017/8/28 22:50
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseActivity_7 extends Activity {

    private BasePresenter_7 presenter;

    public BasePresenter_7 getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        presenter = createPresenter();
        presenter.attachView(createView());
    }

    public abstract BasePresenter_7 createPresenter();

    public abstract BaseView_7 createView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
