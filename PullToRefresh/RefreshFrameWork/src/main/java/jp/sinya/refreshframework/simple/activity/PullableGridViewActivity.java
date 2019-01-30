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
import jp.sinya.refreshframework.pullableview.PullableGridView;
import jp.sinya.refreshframework.simple.adapter.TestCommonAdapter;
import jp.sinya.refreshframework.simple.listener.MyListener;


public class PullableGridViewActivity extends Activity {

    PullableGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_gridview);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new MyListener());
        gridView = (PullableGridView) findViewById(R.id.content_view);
        initGridView();
    }

    /**
     * GridView初始化方法
     */
    private void initGridView() {
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            items.add("这里是item " + i);
        }
        TestCommonAdapter adapter = new TestCommonAdapter(this, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullableGridViewActivity.this, "LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullableGridViewActivity.this, " Click on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setIsCanLoad(true);
    }
}
