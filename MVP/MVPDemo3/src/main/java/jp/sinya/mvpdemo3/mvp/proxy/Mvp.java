package jp.sinya.mvpdemo3.mvp.proxy;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * 用于作为公共接口，把重复的那部分代码都提取出来，由实现类去操作
 *
 * @author Koizumi Sinya
 * @date 2018/01/22. 17:58
 * @edithor
 * @date
 */
@Deprecated
public interface Mvp<V extends BaseView, P extends BasePresenter<V>> {
    P createPresenter();

    V createMvpView();

    P getPresenter();
}