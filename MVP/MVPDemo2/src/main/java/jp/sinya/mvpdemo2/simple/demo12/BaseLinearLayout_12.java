package jp.sinya.mvpdemo2.simple.demo12;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import jp.sinya.mvpdemo2.simple.demo12.impl.MvpCallback;
import jp.sinya.mvpdemo2.simple.demo12.impl.MvpImpl;
import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;


/**
 * 作者: Dream on 2017/8/29 21:04
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//集成了MVP
public abstract class BaseLinearLayout_12<V extends BaseView_8, P extends BasePresenter_8<V>> extends LinearLayout implements MvpCallback<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    public BaseLinearLayout_12(Context context) {
        super(context);
    }

    public BaseLinearLayout_12(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mvpImpl = new MvpImpl<V, P>(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mvpImpl.onDestroy();
    }
}
