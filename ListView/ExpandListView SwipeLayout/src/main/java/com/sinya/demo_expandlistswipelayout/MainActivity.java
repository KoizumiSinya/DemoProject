package com.sinya.demo_expandlistswipelayout;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinya.demo_expandlistswipelayout.widget.SwipeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private Context context;
    private ExpandableListView expand_list;

    private String[] groupTitle = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private Map<String, List<String>> data;
    private List<String> childString;

    private Map<String, SwipeLayout> swipeMap;
    private List<SwipeLayout> swipes;

    private float mTouchX, mTouchY, mMoveX, mMoveY, mUpx, mUpy;
    private long mTouchTime, mUpTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        initData();
        expand_list = (ExpandableListView) findViewById(R.id.expand_list);
        expand_list.setAdapter(new MyAdapter());
        expand_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                shouldClose();
            }
        });

        // 默认展开
        /*for (int i = 0; i < groupTitle.length; i++) {
            expand_list.expandGroup(i);
        }

        // group不可折叠
        expand_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });*/
    }

    private void initData() {
        data = new HashMap<>();
        swipes = new ArrayList<>();
        swipeMap = new HashMap<>();

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("阿里巴巴");
        childString.add("爱心伟业大药房");
        childString.add("安体普专卖");
        childString.add("阿依艾工程软件(大连)有限公司北京办事处");
        //}
        data.put("A", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("北京盛彩佳印彩印设计有限公司");
        childString.add("北京恒通永昌建材经营部");
        childString.add("边航轮船有限公司");
        childString.add("贝乐香港实业有限公司");
        //}
        data.put("B", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("参生堂");
        childString.add("晨星广告");
        childString.add("城市漫步网站");
        //}
        data.put("C", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("东莞市高强信实业有限公司深圳办事处");
        childString.add("大中华国际集团");
        childString.add("东园演艺吧");
        childString.add("东信网络技术有限公司");
        childString.add("大地展示");
        childString.add("迪哥保健品有限公司");
        childString.add("大宇電子科技有限公司");
        childString.add("多科水力分析有限公司");
        //}
        data.put("D", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("恩曼技术有限公司");
        childString.add("二连浩特市北方国际贸易公司北京经营部");
        //}
        data.put("E", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("福临装饰工程部");
        childString.add("奋进达福田店");
        childString.add("富创广告");
        childString.add("方圆达会计师事务所");
        childString.add("飞顺达实业有限公司");
        childString.add("佛尘阁佛具流通处");
        childString.add("芬优公司");
        childString.add("福田区金地工业区");
        childString.add("风笛商务调查机构");
        childString.add("丰颖企业有限公司");
        //}
        data.put("F", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("国辉通讯");
        childString.add("盖拉斯电子有限公司深圳代表处");
        childString.add("格多达科技有限公司");
        childString.add("广兴翌地毯工艺品商店");
        childString.add("国墙广告");
        childString.add("功明设计广告公司");
        childString.add("港边公司");
        childString.add("国际通");
        childString.add("冠图信息咨询有限公司");
        //}
        data.put("G", childString);

        childString = new ArrayList<>();
        //for (int i = 0; i < 1000; i++) {
        childString.add("海大承网络技术有限公司(深圳办事处)");
        childString.add("环球商旅网");
        childString.add("华理汽车维修中心");
        //}
        data.put("H", childString);
    }

    private void shouldClose() {
        for (SwipeLayout layout : swipeMap.values()) {
            if (layout.isOpen()) {
                layout.close();
            }
        }
    }

    private boolean isOpen() {
        for (SwipeLayout layout : swipeMap.values()) {
            if (layout.isOpen()) {
                shouldClose();
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有长按动作发生
     *
     * @param lastX         按下时X坐标
     * @param lastY         按下时Y坐标
     * @param thisX         移动时X坐标
     * @param thisY         移动时Y坐标
     * @param lastDownTime  按下时间
     * @param thisEventTime 移动时间
     * @param longPressTime 判断长按时间的阀值
     */
    private boolean isLongPressed(float lastX, float lastY, float thisX, float thisY, long lastDownTime, long thisEventTime, long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }

    private class MyAdapter implements ExpandableListAdapter {

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return groupTitle.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return data.get(groupTitle[groupPosition]).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return groupTitle[groupPosition];
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return data.get(groupTitle[groupPosition]).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition + childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.view_list_group, null);
            TextView group = (TextView) convertView.findViewById(R.id.tv_group_name);
            group.setText(groupTitle[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            convertView = View.inflate(context, R.layout.view_list_child, null);

            //侧滑
            final SwipeLayout swipe_layout = (SwipeLayout) convertView.findViewById(R.id.swipe_layout);
            swipeMap.put(groupPosition + getChild(groupPosition, childPosition) + childPosition, swipe_layout);
           /* swipe_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });*/
            swipe_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            /*if (isOpen()) {
                                shouldClose();
                                return false;
                            }*/

                            mTouchX = event.getX();
                            mTouchY = event.getY();
                            mTouchTime = event.getEventTime();

                            break;

                        case MotionEvent.ACTION_MOVE:

                            mMoveX = event.getX();
                            mMoveY = event.getY();

                            int xDiff = (int) Math.abs(mMoveX - mTouchX);
                            int yDiff = (int) Math.abs(mMoveY - mTouchY);

                            if (xDiff > yDiff) {
                                shouldClose();
                                expand_list.requestDisallowInterceptTouchEvent(true);
                            } else {
                                shouldClose();
                            }
                            break;

                        case MotionEvent.ACTION_UP:

                            mUpTime = event.getEventTime();
                            mUpx = event.getX();
                            mUpy = event.getY();
                            //点击事件

                            if (mUpx == mTouchX && mUpy == mTouchY) {

                                //如果不是长按 才成功判定为单次点击事件
                                if (isLongPressed(mTouchX, mTouchY, mUpx, mUpy, mTouchTime, mUpTime, 500)) {
                                    Toast.makeText(context, "长按：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!isOpen()) {
                                        Toast.makeText(context, "点击：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }

                    return false;
                }
            });

            //公司名
            TextView child = (TextView) convertView.findViewById(R.id.tv_name);
            child.setText(data.get(groupTitle[groupPosition]).get(childPosition));

            //头像
            convertView.findViewById(R.id.iv_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isOpen()) {
                        Toast.makeText(context, "点击头像：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //删除
            convertView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "删除：" + data.get(groupTitle[groupPosition]).get(childPosition), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }

}
