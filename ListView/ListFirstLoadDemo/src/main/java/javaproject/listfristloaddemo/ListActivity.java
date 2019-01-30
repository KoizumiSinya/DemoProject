package javaproject.listfristloaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    private ListView listView;
    private MyAdapter adapter;
    private List<Bean> dataList;
    private boolean isFirstLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initData();
        initListView();
        setData();
    }

    private void initData() {
        dataList = new ArrayList<>();
    }

    private void setData() {
        for (int i = 0; i < 30; i++) {
            dataList.add(new Bean("优惠卷 1", 1));
        }
        dataList.add(30, new Bean("优惠卷 2", 2));
        adapter.setDataList(dataList);
    }

    private void initListView() {
        listView = findViewById(R.id.activity_list_view);
        adapter = new MyAdapter(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                adapter.setScrollEnd(i == SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                setAnimLoaded(totalItemCount == dataList.size(), visibleItemCount);

                Log.i(("Sinya"), "firstVisibleItem: " + firstVisibleItem);
                Log.i(("Sinya"), "visibleItemCount: " + visibleItemCount);
                Log.i(("Sinya"), "totalItemCount: " + totalItemCount);
            }
        });
        listView.setAdapter(adapter);
    }

    private void setAnimLoaded(boolean isLoad, int visibleCount) {
        if (!isFirstLoad && isLoad) {
            for (int i = 0; i < visibleCount; i++) {
                boolean isUsed = MainActivity.dataMap.containsKey(dataList.get(i).getType()) && MainActivity.dataMap.get(dataList.get(i).getType());
                if (isUsed) {
                    adapter.getMapAnimStatus().put(dataList.get(i).getType(), true);
                }
            }
            isFirstLoad = true;
        }
    }
}
