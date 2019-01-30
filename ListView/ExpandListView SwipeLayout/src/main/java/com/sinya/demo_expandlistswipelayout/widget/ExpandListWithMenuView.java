package com.sinya.demo_expandlistswipelayout.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinya.demo_expandlistswipelayout.R;

import java.util.HashMap;
import java.util.List;

/**
 * 自定义可折叠并附带侧滑菜单的ExpandList
 *
 * @author KoizumiSinya
 * @date 2016/1/15.
 */
public class ExpandListWithMenuView extends ExpandableListView {

    private Context context;

    private String[] groupTitles;
    private HashMap<String, List<String>> childMap;
    private HashMap<String, SwipeLayout> swipeMap;

    private onExpandListItemClickLister itemClickLister;
    private MyAdapter adapter;

    private float mTouchX, mTouchY, mMoveX, mMoveY, mUpx, mUpy;
    private long mTouchTime, mUpTime;

    // [+] Constructor

    public ExpandListWithMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandListWithMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // [-] Constructor
    // [+] 对外提供的方法

    public void setListData(Context context, String[] groupTitles, HashMap<String, List<String>> childs) {
        this.context = context;
        this.groupTitles = groupTitles;
        this.childMap = childs;

        swipeMap = new HashMap<>();

        if (adapter == null) {
            adapter = new MyAdapter();
        }
        setAdapter(adapter);
    }

    public void setOnExpandListClickListener(onExpandListItemClickLister listener){
        this.itemClickLister = listener;
    }

    // [-] 对外提供的方法

    // [+] click


    // [-] click
    // [+] Adapter

    public class MyAdapter implements ExpandableListAdapter {

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return groupTitles.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childMap.get(groupTitles[groupPosition]).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return childMap.get(groupTitles[groupPosition]).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.view_list_group, null);
            TextView group = (TextView) convertView.findViewById(R.id.tv_group_name);
            group.setText(groupTitles[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final String companyName = childMap.get(groupTitles[groupPosition]).get(childPosition);

            final HolderView holderView;
            if (convertView == null) {
                holderView = new HolderView();
                convertView = View.inflate(context, R.layout.view_list_child, null);


                holderView.img_icon = (ImageView) convertView.findViewById(R.id.iv_img);
                holderView.tv_company = (TextView) convertView.findViewById(R.id.tv_name);
                holderView.tv_address = (TextView) convertView.findViewById(R.id.tv_path);
                holderView.delete_button = (RelativeLayout) convertView.findViewById(R.id.delete_button);

                final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe_layout);
                swipeLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

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
                                    requestDisallowInterceptTouchEvent(true);
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
                                        if (itemClickLister != null) {
                                            itemClickLister.onLongClick(swipeLayout, groupPosition, childPosition);
                                        }
                                    } else {
                                        if (!isOpen()) {
                                            if (itemClickLister != null) {
                                                itemClickLister.onClick(swipeLayout, groupPosition, childPosition);
                                            }
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
                swipeMap.put(groupPosition + companyName + childPosition, swipeLayout);

                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }

            holderView.tv_company.setText(companyName);
            holderView.img_icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickLister != null) {
                        itemClickLister.onClick(holderView.img_icon, groupPosition, childPosition);
                    }
                }
            });
            holderView.delete_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickLister != null) {
                        itemClickLister.onClick(holderView.delete_button, groupPosition, childPosition);
                    }
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

        private class HolderView {
            ImageView img_icon;
            TextView tv_company;
            TextView tv_address;
            RelativeLayout delete_button;
        }
    }

    // [-] Adapter

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

    // [+] Interface

    public interface onExpandListItemClickLister {
        public void onClick(View v, int groupPosition, int childPosition);

        public void onLongClick(View v, int groupPosition, int childPosition);
    }

    // [-] Interface

}
