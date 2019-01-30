package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.pullableview.PullableStickySwipeExpandableListView;
import jp.sinya.refreshframework.stickyexpandlistview.StickyLayout.OnGiveUpTouchEventListener;
import jp.sinya.refreshframework.stickyexpandlistview.SwipeStickyExpandListView;

public class PullableSwipeStickyExpandListViewActivity extends Activity implements OnChildClickListener, OnGroupClickListener, SwipeStickyExpandListView.OnHeaderUpdateListener,
        OnGiveUpTouchEventListener {
    private Context context;

    private PullToRefreshLayout refreshLayout;
    private PullableStickySwipeExpandableListView listView;
    private MyAdapter adapter;

    private List<String> groupList;
    private Map<String, List<String>> childMap;

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
        setContentView(R.layout.common_refresh_sticky_swipe_expand_listview);
        context = this;

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
        listView = (PullableStickySwipeExpandableListView) findViewById(R.id.expandablelist);

        adapter = new MyAdapter(context);
        listView.setAdapter(adapter);
        initData();
        adapter.setDataList(groupList, childMap);

        // 展开所有group
        for (int i = 0, count = listView.getCount(); i < count; i++) {
            listView.expandGroup(i);
        }

        listView.setOnHeaderUpdateListener(this);
        listView.setOnChildClickListener(this);
        listView.setOnGroupClickListener(this);
        listView.setFocusable(false);
        listView.setIsCanLoad(true);
        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, final View v, int groupPosition, int childPosition, long id) {
                v.setPressed(true);
                v.setSelected(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setPressed(false);
                        v.setSelected(false);
                    }
                }, 500);
                Toast.makeText(context, "点击Item" + childMap.get(groupList.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //是否允许侧滑删除
        //listView.setIsCanSwipe(false);
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

    // [+] Override

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.simple_swipe_sticky_expand_listview_group, null);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        String groupStr = adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(groupStr);
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (listView.getFirstVisiblePosition() == 0) {
            View view = listView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    // [-] Override
    // [+] Class

    class MyAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> groupList;
        private Map<String, List<String>> childMap;

        public MyAdapter(Context context) {
            this.context = context;
        }

        public void setDataList(List<String> groupList, Map<String, List<String>> childMap) {
            this.groupList = groupList;
            this.childMap = childMap;
            notifyDataSetChanged();
        }

        @Override
        public int getGroupCount() {
            if (groupList != null) {
                return groupList.size();
            }
            return 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (childMap != null && groupList != null) {
                String key = groupList.get(groupPosition);
                if (!TextUtils.isEmpty(key) && childMap.get(key) != null) {
                    return childMap.get(key).size();
                }
            }
            return 0;
        }

        @Override
        public String getGroup(int groupPosition) {
            if (groupList != null) {
                return groupList.get(groupPosition);
            }
            return null;
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            if (childMap != null && groupList != null) {
                String key = groupList.get(groupPosition);
                if (!TextUtils.isEmpty(key) && childMap.get(key) != null) {
                    if (childMap.get(key).get(childPosition) != null) {
                        return childMap.get(key).get(childPosition);
                    }
                }
            }
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.simple_swipe_sticky_expand_listview_group, null);
                groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(groupList.get(groupPosition));
            // 禁止伸展
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.simple_swipe_sticky_expand_listview_child, null);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.head);
                viewHolder.tvDelete = (TextView) convertView.findViewById(R.id.delete_friend);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvName.setText(childMap.get(groupList.get(groupPosition)).get(childPosition));
            viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "删除" + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.imgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击头像" + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class ViewHolder {
            TextView tvName;
            TextView tvDelete;
            ImageView imgIcon;
        }

        class GroupHolder {
            TextView textView;
        }
    }

    // [-] Class
}
