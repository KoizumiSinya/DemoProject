package jp.sinya.mvpdemo2.simple.demo15.impl.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;

import jp.sinya.mvpdemo2.R;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpPresenter_13;
import jp.sinya.mvpdemo2.simple.demo13.base.MvpView_13;
import jp.sinya.mvpdemo2.simple.demo15.impl.MvpLceActivity_15;
import jp.sinya.mvpdemo2.simple.demo15.impl.refresh.adapter.BaseRefreshAdapter;

/**
 * 作者: Dream on 2017/8/30 22:25
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//集成下拉刷新组件(自己定义->采用任意下拉刷新组件)
//默认情况下，我写了一个(演示如何实现)
public abstract class MvpLceRefreshActivity<D, V extends MvpView_13, P extends MvpPresenter_13<V>> extends MvpLceActivity_15<D, V, P> {

    private XRefreshView refreshView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BaseRefreshAdapter recyclerAdapter;
    private boolean isDownRefresh;
    private boolean isPullToRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshView(getWindow().getDecorView());
    }

    /**
     * 初始化下拉刷新组件
     *
     * @param contentView
     */
    public void initRefreshView(View contentView) {
        refreshView = (XRefreshView) contentView.findViewById(R.id.lceRefreshView);
        //是否可以下拉刷新,true代表可以,false代表不支持
        refreshView.setPullRefreshEnable(true);
        //是否允许加载更多
        refreshView.setPullLoadEnable(true);
        //设置下拉刷新完成之后,刷新头部停留的时间
        refreshView.setPinnedTime(1000);
        //设置是否自动刷新(进入页面自动刷新)
        refreshView.setAutoRefresh(false);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.lceRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        //设置列表为垂直方向显示
        recyclerView.setLayoutManager(linearLayoutManager);

        //绑定Adapter
        recyclerAdapter = bindAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        //给我们的Adapter添加加载更多的布局
        recyclerAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));

        //添加下拉刷新监听
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                super.onRefresh();
                refreshData(true);
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                super.onLoadMore(isSlience);
                refreshData(false);
            }
        });
    }

    public abstract BaseRefreshAdapter bindAdapter();

    public void refreshData(boolean isDownRefresh) {
        this.isDownRefresh = isDownRefresh;
        this.isPullToRefresh = true;
    }

}
