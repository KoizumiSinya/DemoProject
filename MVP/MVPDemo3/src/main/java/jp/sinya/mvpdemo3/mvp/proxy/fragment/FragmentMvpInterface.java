package jp.sinya.mvpdemo3.mvp.proxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 11:35
 * @edithor
 * @date
 */
public interface FragmentMvpInterface<V extends BaseView, P extends BasePresenter<V>> {

    /**
     * 执行该方法时，Fragment与Activity已经完成绑定，该方法有一个Context类型的参数，代表绑定的Activity。
     *
     * @param context
     */
    void onAttach(Context context);

    /**
     * 初始化Fragment。可通过参数savedInstanceState获取之前保存的值。
     *
     * @param savedInstanceState
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 初始化Fragment的布局。加载布局和findViewById的操作通常在此函数内完成，但是不建议执行耗时的操作，比如读取数据库数据列表。
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，
     * 在该方法内可以进行与Activity交互的UI操作，所以在该方法之前Activity的onCreate方法并未执行完成，如果提前进行交互操作，会引发空指针异常。
     *
     * @param savedInstanceState
     */
    void onActivityCreated(Bundle savedInstanceState);

    /**
     * @param view
     * @param savedInstanceState
     */
    void onViewCreated(View view, Bundle savedInstanceState);

    /**
     * 执行该方法时，Fragment由不可见变为可见状态。
     */
    void onStart();

    /**
     * 执行该方法时，Fragment处于活动状态，用户可与之交互。
     */
    void onPause();

    /**
     * 执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。
     */
    void onResume();

    /**
     * 执行该方法时，Fragment完全不可见。
     */
    void onStop();

    /**
     * 销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。通常在ViewPager+Fragment的方式下会调用此方法。
     */
    void onDestroyView();

    /**
     * 销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。
     */
    void onDestroy();

    /**
     * 解除与Activity的绑定。在onDestroy方法之后调用。
     */
    void onDetach();

    /**
     * 保存当前Fragment的状态。该方法会自动保存Fragment的状态，比如EditText键入的文本，即使Fragment被回收又重新创建，一样能恢复EditText之前键入的文本。
     *
     * @param outState
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * 设置Fragment可见或者不可见时会调用此方法。在该方法里面可以通过调用getUserVisibleHint()获得Fragment的状态是可见还是不可见的，如果可见则进行懒加载操作。
     */
    void setUserVisibleHint();
}
