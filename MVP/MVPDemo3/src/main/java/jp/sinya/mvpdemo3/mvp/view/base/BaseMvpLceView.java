package jp.sinya.mvpdemo3.mvp.view.base;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 12:52
 * @edithor
 * @date
 */
public interface BaseMvpLceView<D> extends BaseView {

    /**
     * 是否显示加载控件
     *
     * @param isPullToRefresh 是否是下拉刷新（下拉刷新的话，本身已经自带刷新组件）
     */
    void showLoading(boolean isPullToRefresh);

    void showContent(boolean isPullToRefresh);

    void showError(boolean isPullToRefresh);

    void bindData(D data, boolean isPullToRefresh);

    void loadData(boolean isPullToRefresh);
}
