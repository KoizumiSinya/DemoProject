package jp.sinya.refreshframework.simple.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.pullableview.PullableSwipeMenuListView;
import jp.sinya.refreshframework.simple.adapter.SwipeMenuListViewAdapter;
import jp.sinya.refreshframework.swipemenulistview.SlidingItembean;

public class PullableSwipeMenuListViewActivity extends AppCompatActivity {

    private Context context;
    private PullToRefreshLayout refreshLayout;
    private PullableSwipeMenuListView listview;

    private SwipeMenuListViewAdapter adapter;
    private List<SlidingItembean> beanList;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (refreshLayout != null) {
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    adapter.setData(beanList);
                    break;
                default:
                    break;
            }

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_swipe_menu_listview);
        context = this;
        initData();

        initView();
    }

    private void initData() {
        beanList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            SlidingItembean slidingItembean = null;
            if (i % 3 == 0) {
                slidingItembean = new SlidingItembean(String.valueOf(i), "啊啊啊", "jlhjkh", "置顶");
            } else if (i % 3 == 1) {
                slidingItembean = new SlidingItembean(String.valueOf(i), "哦哦哦", "hjkhklhkljh", "置顶");
            } else {
                slidingItembean = new SlidingItembean(String.valueOf(i), "嘿嘿嘿", "gfyfgjhgdtrtddc", "置顶");
            }

            beanList.add(slidingItembean);
        }
    }

    private void initView() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                handler.sendEmptyMessageDelayed(0, 800);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });

        listview = (PullableSwipeMenuListView) findViewById(R.id.content_view);
        adapter = new SwipeMenuListViewAdapter(context, listview.getRightViewWidth());
        listview.setAdapter(adapter);
        adapter.setOnMenuClickListener(new SwipeMenuListViewAdapter.OnMenuClickListener() {
            @Override
            public void clickMenu(int position, int index) {
                switch (index) {
                    case 0:
                        if (beanList.get(position).getSetTop().equals("置顶")) {
                            beanList.get(position).setSetTop("取消置顶");
                        } else {
                            beanList.get(position).setSetTop("置顶");
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;

                }
            }
        });
    }
}
