package jp.sinya.mvpdemo3.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.adapter.MvpLceRecyclerViewAdapter;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 16:29
 * @edithor
 * @date
 */
public abstract class BaseMvpLceRefreshActivity<D, V extends BaseView, P extends BasePresenter<V>> extends BaseMvpLceActivity<D, V, P> implements BaseMvpLceView<D> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MvpLceRecyclerViewAdapter adapter;

    public MvpLceRecyclerViewAdapter getAdapter() {
        if (adapter == null) {
            adapter = createAdapter();
        }
        return adapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefreshView(getWindow().getDecorView());
    }

    /**
     * @param contentView
     */
    private void initRefreshView(View contentView) {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = contentView.findViewById(R.id.lceRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = createAdapter();
        recyclerView.setAdapter(adapter);

        refreshLayout = contentView.findViewById(R.id.lceSwipeLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        });
    }

    private SwipeRefreshLayout getRefreshView(){
        return refreshLayout;
    }

    protected abstract MvpLceRecyclerViewAdapter createAdapter();

}
