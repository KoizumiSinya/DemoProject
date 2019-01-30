package jp.sinya.mvpdemo2.simple.demo11;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/29 21:04
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//集成了MVP
public abstract class BaseLinearLayout_11<V extends BaseView_8, P extends BasePresenter_8<V>> extends LinearLayout {

    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    public BaseLinearLayout_11(Context context) {
        super(context);
        init();
    }

    public BaseLinearLayout_11(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter，空指针异常...");
        }
        view = createView();
        if (view == null) {
            throw new NullPointerException("view，空指针异常...");
        }
        presenter.attachView(view);
    }

    public abstract P createPresenter();

    public abstract V createView();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
