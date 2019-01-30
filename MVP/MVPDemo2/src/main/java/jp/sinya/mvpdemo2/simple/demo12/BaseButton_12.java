package jp.sinya.mvpdemo2.simple.demo12;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import jp.sinya.mvpdemo2.simple.demo12.impl.MvpCallback;
import jp.sinya.mvpdemo2.simple.demo12.impl.MvpImpl;
import jp.sinya.mvpdemo2.simple.demo8.base.BasePresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.base.BaseView_8;

/**
 * 作者: Dream on 2017/8/29 21:08
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class BaseButton_12<V extends BaseView_8, P extends BasePresenter_8<V>> extends Button implements MvpCallback<V, P> {

    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    public BaseButton_12(Context context) {
        super(context);
    }

    public BaseButton_12(Context context, AttributeSet attrs) {
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
