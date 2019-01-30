package jp.sinya.mvpdemo3.mvp.proxy;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * 持有MVPCallBack作为成员变量，由外部的实现对象传入
 * 这个类对象的作用 是将各个LoginView V层实现类的Activity/Fragment/View 中重复通用的代码提取出来
 * <p>
 * 但是这个Impl，不能友好的区分当前的View层是属于 哪种类型
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 18:02
 * @edithor
 * @date
 */
@Deprecated
public class MvpImpl<V extends BaseView, P extends BasePresenter<V>> {
    private P presenter;
    private V view;

    private Mvp<V, P> callBack;

    public P getPresenter() {
        return presenter;
    }

    public MvpImpl(Mvp<V, P> callBack) {
        this.callBack = callBack;
        init();
    }

    private void init() {
        presenter = callBack.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("initPresenter is null...");
        }
        view = callBack.createMvpView();
        if (view == null) {
            throw new NullPointerException("view is null...");
        }
        presenter.attachView(view);
    }

    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
