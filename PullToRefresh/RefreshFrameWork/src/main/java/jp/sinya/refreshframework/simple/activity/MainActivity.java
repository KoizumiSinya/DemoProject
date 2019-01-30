package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.pullableview.PullableListView;
import jp.sinya.refreshframework.simple.adapter.TestCommonAdapter;

/**
 * 更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38868463
 *
 * @author 陈靖
 */
public class MainActivity extends Activity {

    private PullToRefreshLayout refreshLayout;
    private PullableListView listView;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    break;
                case 1:
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
        setContentView(R.layout.common_refresh_listview);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                handler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        });

        listView = (PullableListView) findViewById(R.id.content_view);
        listView.setIsCanLoad(true);

        initListView();
    }

    /**
     * ListView初始化方法
     */
    private void initListView() {
        List<String> items = new ArrayList<>();
        items.add("ListView");
        items.add("GridView");
        items.add("ExpandableListView");
        items.add("SrcollView");
        items.add("WebView");
        items.add("ImageView");
        items.add("TextView");
        items.add("SwipeListView");
        items.add("StickyExpandListView");
        items.add("SwipeStickyExpandListView");
        items.add("SwipeMenuListView");

        TestCommonAdapter adapter = new TestCommonAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, " LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent();
                switch (position) {
                    case 0:
                        it.setClass(MainActivity.this, PullableListViewActivity.class);
                        break;
                    case 1:
                        it.setClass(MainActivity.this, PullableGridViewActivity.class);
                        break;
                    case 2:
                        it.setClass(MainActivity.this, PullableExpandableListViewActivity.class);
                        break;
                    case 3:
                        it.setClass(MainActivity.this, PullableScrollViewActivity.class);
                        break;
                    case 4:
                        it.setClass(MainActivity.this, PullableWebViewActivity.class);
                        break;
                    case 5:
                        it.setClass(MainActivity.this, PullableImageViewActivity.class);
                        break;
                    case 6:
                        it.setClass(MainActivity.this, PullableTextViewActivity.class);
                        break;
                    case 7:
                        it.setClass(MainActivity.this, PullableSwipeListViewActivity.class);
                        break;
                    case 8:
                        it.setClass(MainActivity.this, PullableStickyExpandListViewActivity.class);
                        break;
                    case 9:
                        it.setClass(MainActivity.this, PullableSwipeStickyExpandListViewActivity.class);
                        break;
                    case 10:
                        it.setClass(MainActivity.this, PullableSwipeMenuListViewActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(it);
            }
        });
    }
}
