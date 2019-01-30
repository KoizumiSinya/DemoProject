package jp.sinya.mvpdemo3.mvp.view.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import jp.sinya.mvpdemo3.mvp.proxy.Mvp;
import jp.sinya.mvpdemo3.mvp.proxy.MvpImpl;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * 利用MVP的架构，自己实现一个abstract的BaseLinearLayout(假设这个控件用来做执行登陆的操作)
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 16:54
 * @edithor
 * @date
 */
public abstract class BaseLinearLayout<V extends BaseView, P extends BasePresenter<V>> extends LinearLayout implements Mvp<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    public BaseLinearLayout(Context context) {
        super(context);
        init();
    }

    public BaseLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mvpImpl = new MvpImpl<>(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mvpImpl.onDestroy();
    }
}
