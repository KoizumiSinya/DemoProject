package jp.sinya.mvpdemo3.mvp.view.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import jp.sinya.mvpdemo3.mvp.proxy.Mvp;
import jp.sinya.mvpdemo3.mvp.proxy.MvpImpl;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/22. 17:10
 * @edithor
 * @date
 */
public abstract class BaseButton<V extends BaseView, P extends BasePresenter<V>> extends Button implements Mvp<V, P> {
    private MvpImpl<V, P> mvpImpl;

    public P getPresenter() {
        return mvpImpl.getPresenter();
    }

    public BaseButton(Context context) {
        super(context);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mvpImpl.onDestroy();
    }

    private void init() {
        mvpImpl = new MvpImpl<>(this);
    }
}
