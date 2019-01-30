package jp.sinya.mvpdemo3.mvp.presenter.base;

import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/21. 12:18
 * @edithor
 * @date
 */
public abstract class BasePresenter<V extends BaseView> {
    private V view;

    public V getMvpView() {
        return view;
    }

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

}
