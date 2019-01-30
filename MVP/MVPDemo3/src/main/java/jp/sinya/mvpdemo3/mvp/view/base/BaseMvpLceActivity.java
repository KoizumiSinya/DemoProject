package jp.sinya.mvpdemo3.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import jp.sinya.mvpdemo3.mvp.anim.IMvpLceAnimator;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 16:03
 * @edithor
 * @date
 */
public abstract class BaseMvpLceActivity<D, V extends BaseView, P extends BasePresenter<V>> extends BaseMvpActivity<V, P> implements BaseMvpLceView<D> {

    private BaseMvpLceViewImpl<D> impl;

    public BaseMvpLceViewImpl<D> getImpl() {
        if (impl == null) {
            impl = new BaseMvpLceViewImpl<>();
        }
        return impl;
    }

    public void setLceAnimator(IMvpLceAnimator lceAnimator) {
        getImpl().setAnimInterface(lceAnimator);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getImpl().initLceView(getWindow().getDecorView());
        getImpl().setOnErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickError(v);
            }
        });
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {
        getImpl().showLoading(isPullToRefresh);
    }

    @Override
    public void showContent(boolean isPullToRefresh) {
        getImpl().showContent(isPullToRefresh);
    }

    @Override
    public void showError(boolean isPullToRefresh) {
        getImpl().showError(isPullToRefresh);
    }

    @Override
    public void bindData(D data, boolean isPullToRefresh) {
        getImpl().bindData(data, isPullToRefresh);
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        getImpl().loadData(isPullToRefresh);
    }

    public void clickError(View v) {
        loadData(false);
    }
}
