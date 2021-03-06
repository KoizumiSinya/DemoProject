package jp.sinya.mvpdemo2.simple.demo15.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;
import jp.sinya.mvpdemo2.simple.demo13.support.activity.MvpActivity_13;
import jp.sinya.mvpdemo2.simple.demo15.MvpLceView_15;
import jp.sinya.mvpdemo2.simple.demo15.animator.ILceAnimator;

/**
 * 作者: Dream on 2017/8/30 21:14
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public abstract class MvpLceActivity_15<D, V extends MvpView_13, P extends MvpPresenter_13<V>> extends MvpActivity_13<V, P> implements MvpLceView_15<D> {

    //持有目标对象引用
    private MvpLceViewImpl_15<D> mvpLceView;

    private MvpLceViewImpl_15<D> getMvpLceView() {
        if (mvpLceView == null) {
            mvpLceView = new MvpLceViewImpl_15<D>();
        }
        return mvpLceView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定
        getMvpLceView().initLceView(getWindow().getDecorView());
        getMvpLceView().setOnErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickError(view);
            }
        });
    }

    public void onClickError(View view) {
        //默认重写加载数据
        loadData(false);
    }

    //子类重写该方法，重写配置策略
    public void setLceAnimator(ILceAnimator lceAnimator) {
        getMvpLceView().setLceAnimator(lceAnimator);
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {
        getMvpLceView().showLoading(isPullToRefresh);
    }

    @Override
    public void showContent(boolean isPullToRefresh) {
        getMvpLceView().showLoading(isPullToRefresh);
    }

    @Override
    public void showError(boolean isPullToRefresh) {
        getMvpLceView().showError(isPullToRefresh);
    }

    @Override
    public void bindData(D data, boolean isPullToRefresh) {
        getMvpLceView().bindData(data, isPullToRefresh);
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        getMvpLceView().loadData(isPullToRefresh);
    }

}
