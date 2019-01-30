package jp.sinya.swipeexpandlistviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sinya.swipeexpandlistviewdemo.widget.DelSlideExpandableListView;
import jp.sinya.swipeexpandlistviewdemo.widget.MyAdapter;

public class MainActivity extends Activity {

    private Context context;
    private DelSlideExpandableListView listView;
    private MyAdapter adapter;

    private List<String> groupList;
    private Map<String, List<String>> childMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        listView = (DelSlideExpandableListView) findViewById(R.id.listView);
        adapter = new MyAdapter(context);
        listView.setAdapter(adapter);
        initData();
        adapter.setDataList(groupList, childMap);

        // 展开所有group
        for (int i = 0, count = listView.getCount(); i < count; i++) {
            listView.expandGroup(i);
        }
    }

    private void initData() {
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        groupList.add("A");
        groupList.add("G");
        groupList.add("V");
        groupList.add("O");
        groupList.add("S");
        groupList.add("M");

        if (childMap == null) {
            childMap = new HashMap<>();
        }

        List<String> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            temp.add("名称" + i);
        }
        childMap.put("A", temp);

        temp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            temp.add("名称" + i);
        }
        childMap.put("G", temp);

        temp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            temp.add("名称" + i);
        }
        childMap.put("V", temp);

        temp = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            temp.add("名称" + i);
        }
        childMap.put("O", temp);

        temp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            temp.add("名称" + i);
        }
        childMap.put("S", temp);

        temp = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            temp.add("名称" + i);
        }
        childMap.put("M", temp);
    }
}
