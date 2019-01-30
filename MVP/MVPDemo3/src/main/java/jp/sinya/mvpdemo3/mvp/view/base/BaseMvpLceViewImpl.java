package jp.sinya.mvpdemo3.mvp.view.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.anim.MvpLceAnimatorImpl;
import jp.sinya.mvpdemo3.mvp.anim.IMvpLceAnimator;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 13:23
 * @edithor
 * @date
 */
public class BaseMvpLceViewImpl<D> implements BaseMvpLceView<D> {

    private View loadView;
    private View refreshLayout;
    private View contentView;
    private View errorView;

    private IMvpLceAnimator animInterface;

    public void setAnimInterface(IMvpLceAnimator animInterface) {
        this.animInterface = animInterface;
    }

    public IMvpLceAnimator getAnimInterface() {
        if (animInterface == null) {
            animInterface = new MvpLceAnimatorImpl();
        }
        return animInterface;
    }


    public void initLceView(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView is null...");
        }

        if (loadView == null) {
            loadView = rootView.findViewById(R.id.loadView);
        }
        if (loadView == null) {
            throw new NullPointerException("loadView is null...");
        }

        if (contentView == null) {
            contentView = rootView.findViewById(R.id.contentView);
            if (contentView == null) {
                contentView = rootView.findViewById(R.id.lceRecyclerView);
            }
        }

        if (refreshLayout == null) {
            refreshLayout = rootView.findViewById(R.id.lceSwipeLayout);
        }

        if (contentView == null) {
            throw new NullPointerException("contentView is null...");
        }

        if (errorView == null) {
            errorView = rootView.findViewById(R.id.errorView);
        }
        if (errorView == null) {
            throw new NullPointerException("errorView is null...");
        }
    }

    public void setOnErrorViewClickListener(View.OnClickListener listener) {
        if (errorView != null) {
            errorView.setOnClickListener(listener);
        }
    }

    @Override
    public void showLoading(boolean isPullToRefresh) {
        if (!isPullToRefresh) {
            getAnimInterface().showLoadingView(loadView, contentView, errorView);
        }
    }

    @Override
    public void showContent(boolean isPullToRefresh) {
        if (!isPullToRefresh) {
            getAnimInterface().showContentView(loadView, contentView, errorView);
        } else {
            if (refreshLayout != null) {
                if (refreshLayout instanceof SwipeRefreshLayout) {
                    ((SwipeRefreshLayout) refreshLayout).setRefreshing(false);
                }
            }
        }
    }

    @Override
    public void showError(boolean isPullToRefresh) {
        if (!isPullToRefresh) {
            getAnimInterface().showErrorView(loadView, contentView, errorView);
        }
    }

    @Override
    public void bindData(D data, boolean isPullToRefresh) {

    }

    @Override
    public void loadData(boolean isPullToRefresh) {

    }
}
