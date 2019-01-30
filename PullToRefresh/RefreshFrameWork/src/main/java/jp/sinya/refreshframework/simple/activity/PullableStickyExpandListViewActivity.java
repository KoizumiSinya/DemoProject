package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.pullableview.PullableScrollView;
import jp.sinya.refreshframework.pullableview.PullableStickyExpandableListView;
import jp.sinya.refreshframework.simple.bean.Group;
import jp.sinya.refreshframework.simple.bean.People;
import jp.sinya.refreshframework.stickyexpandlistview.StickyExpandableListView.OnHeaderUpdateListener;
import jp.sinya.refreshframework.stickyexpandlistview.StickyLayout;
import jp.sinya.refreshframework.stickyexpandlistview.StickyLayout.OnGiveUpTouchEventListener;

public class PullableStickyExpandListViewActivity extends Activity implements OnChildClickListener, OnGroupClickListener, OnHeaderUpdateListener, OnGiveUpTouchEventListener {

    private Context context;
    private PullableStickyExpandableListView expandableListView;
    private StickyLayout stickyLayout;

    private ArrayList<Group> groupList;
    private ArrayList<List<People>> childList;

    private PullToRefreshLayout refreshLayout;
    private PullableScrollView layout;

    private MyexpandableListAdapter adapter;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_sticky_expand_listview);
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


        initData();
        expandableListView = (PullableStickyExpandableListView) findViewById(R.id.expandablelist);
        adapter = new MyexpandableListAdapter(this);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setIsCanLoad(true);

        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

    /**
     * InitData
     */
    void initData() {
        groupList = new ArrayList<>();
        Group group = null;
        for (int i = 0; i < 3; i++) {
            group = new Group();
            group.setTitle("group-" + i);
            groupList.add(group);
        }

        childList = new ArrayList<>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<People> childTemp;
            if (i == 0) {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 13; j++) {
                    People people = new People();
                    people.setName("人名-" + j);
                    people.setAge(30);
                    people.setAddress("公司名-" + j);

                    childTemp.add(people);
                }
            } else if (i == 1) {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    People people = new People();
                    people.setName("人名-" + j);
                    people.setAge(40);
                    people.setAddress("公司名-" + j);

                    childTemp.add(people);
                }
            } else {
                childTemp = new ArrayList<>();
                for (int j = 0; j < 23; j++) {
                    People people = new People();
                    people.setName("人名-" + j);
                    people.setAge(20);
                    people.setAddress("公司名-" + j);

                    childTemp.add(people);
                }
            }
            childList.add(childTemp);
        }
    }

    /***
     * 数据源
     *
     * @author Administrator
     */
    class MyexpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MyexpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
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
            GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.simple_sticky_expand_listview_group, null);
                groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
                groupHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(((Group) getGroup(groupPosition)).getTitle());
            // ture is Expanded or false is not isExpanded
            if (isExpanded) {
                groupHolder.imageView.setImageResource(R.drawable.expanded);
            } else {
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.simple_sticky_expand_listview_child, null);

                childHolder.textName = (TextView) convertView.findViewById(R.id.name);
                childHolder.textAge = (TextView) convertView.findViewById(R.id.age);
                childHolder.textAddress = (TextView) convertView.findViewById(R.id.address);
                childHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                Button button = (Button) convertView.findViewById(R.id.button1);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PullableStickyExpandListViewActivity.this, "clicked pos=", Toast.LENGTH_SHORT).show();
                    }
                });

                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.textName.setText(((People) getChild(groupPosition, childPosition)).getName());
            childHolder.textAge.setText(String.valueOf(((People) getChild(groupPosition, childPosition)).getAge()));
            childHolder.textAddress.setText(((People) getChild(groupPosition, childPosition)).getAddress());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v, int groupPosition, final long id) {

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(PullableStickyExpandListViewActivity.this, childList.get(groupPosition).get(childPosition).getName(), Toast.LENGTH_SHORT).show();

        return false;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textName;
        TextView textAge;
        TextView textAddress;
        ImageView imageView;
    }

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.simple_sticky_expand_listview_group, null);
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (expandableListView.getFirstVisiblePosition() == 0) {
            View view = expandableListView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

}
