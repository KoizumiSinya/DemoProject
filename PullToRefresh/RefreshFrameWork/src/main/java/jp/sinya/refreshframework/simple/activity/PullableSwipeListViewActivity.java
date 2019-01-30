package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullableSwipeListView;
import jp.sinya.refreshframework.simple.adapter.SwipeListViewAdapter;
import jp.sinya.refreshframework.swipelistview.SwipeMenu;
import jp.sinya.refreshframework.swipelistview.SwipeMenuCreator;
import jp.sinya.refreshframework.swipelistview.SwipeMenuItem;
import jp.sinya.refreshframework.swipelistview.SwipeMenuListView;

public class PullableSwipeListViewActivity extends Activity {

    private Context mContext;
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableSwipeListView listView;

    private List<String> strs;
    private SwipeListViewAdapter adapter;
    private int count;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    break;

                case 1:
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    if (count == 10) {
                        listView.setIsCanLoad(false);
                        Toast.makeText(mContext, "已经全部加载完毕", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    strs.add("上拉加载Item" + 1);
                    strs.add("上拉加载Item" + 2);
                    strs.add("上拉加载Item" + 3);
                    count++;
                    adapter.setData(strs);
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
        setContentView(R.layout.common_refresh_swipe_listview);
        mContext = this;

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                handler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        });

        listView = (PullableSwipeListView) findViewById(R.id.content_view);

        strs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            strs.add("Item " + i);
        }
        adapter = new SwipeListViewAdapter(mContext, strs);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(mContext);
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(180);
                openItem.setTitle("删除");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    //点击删除
                    case 0:
                        Toast.makeText(mContext, "删除" + position, Toast.LENGTH_SHORT).show();
                        strs.remove(position);
                        adapter.setData(strs);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "点击" + position, Toast.LENGTH_SHORT).show();
            }
        });
        pullToRefreshLayout.autoLoadView(listView);
    }


}
