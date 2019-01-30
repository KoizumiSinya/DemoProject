package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.os.Bundle;
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
import jp.sinya.refreshframework.simple.listener.MyListener;


public class PullableListViewActivity extends Activity {
    private PullableListView listView;
    private PullToRefreshLayout refreshLayout;
    private boolean isFirstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        refreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        refreshLayout.setOnRefreshListener(new MyListener());
        listView = (PullableListView) findViewById(R.id.content_view);
        initListView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            refreshLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    /**
     * ListView初始化方法
     */
    private void initListView() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add("这里是item " + i);
        }
        TestCommonAdapter adapter = new TestCommonAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullableListViewActivity.this, "LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullableListViewActivity.this, " Click on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setIsCanLoad(true);
    }

}
