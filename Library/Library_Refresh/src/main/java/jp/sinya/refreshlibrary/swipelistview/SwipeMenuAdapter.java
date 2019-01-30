package jp.sinya.refreshlibrary.swipelistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import jp.sinya.refreshlibrary.swipelistview.SwipeMenuListView.OnMenuItemClickListener;

import static jp.sinya.refreshlibrary.swipelistview.SwipeMenuView.OnSwipeItemClickListener;


public class SwipeMenuAdapter implements WrapperListAdapter, OnSwipeItemClickListener {

    private ListAdapter mAdapter;
    private Context mContext;
    private OnMenuItemClickListener onMenuItemClickListener;

    public SwipeMenuAdapter(Context context, ListAdapter adapter) {
        //Log.i("Sinya", "初始化SwipeMenuApdater构造函数");
        mAdapter = adapter;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i("Sinya", "设置getView - " + position);

        SwipeMenuLayout layout = null;
        if (convertView == null) {
            View contentView = mAdapter.getView(position, convertView, parent);

            SwipeMenu menu = new SwipeMenu(mContext);
            menu.setViewType(mAdapter.getItemViewType(position));
            createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu, (SwipeMenuListView) parent);

            // [+] Sinya 只适用于消息列表组
            /*LinearLayout adapterItem = (LinearLayout) contentView.findViewById(R.id.topcount);
            if (adapterItem != null) {
                if (adapterItem.getTag() != null) {
                    int topCount = (int) adapterItem.getTag();
                    if (topCount > 0) {
                        Log.i("Sinya", "位置:" + position + "是否置顶:" + (topCount > 0 ? true : false));
                        SwipeMenu menu2 = new SwipeMenu(mContext);
                        menu2.setViewType(mAdapter.getItemViewType(position));
                        createMenu2(menu2);
                        menuView = new SwipeMenuView(menu2, (SwipeMenuListView) parent);
                    }
                }
            } */
            // [+] Sinya

            menuView.setOnSwipeItemClickListener(this);
            SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(), listView.getOpenInterpolator());
            layout.setPosition(position);

        } else {
            layout = (SwipeMenuLayout) convertView;
            layout.closeMenu();
            layout.setPosition(position);
            // 一定不能注释下面这行代码....
            View view = mAdapter.getView(position, layout.getContentView(), parent);
        }
        return layout;
    }

    private void createMenu2(SwipeMenu menu) {
        SwipeMenuItem itemUp = new SwipeMenuItem(mContext);
        itemUp.setBackground(new ColorDrawable(Color.parseColor("#969696")));
        itemUp.setWidth(dip2px(mContext, 90));
        itemUp.setTitle("取消置顶");
        itemUp.setTitleSize(18);
        itemUp.setTitleColor(Color.WHITE);
        menu.addMenuItem(itemUp);

        SwipeMenuItem itemDel = new SwipeMenuItem(mContext);
        itemDel.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        itemDel.setWidth(dip2px(mContext, 90));
        itemDel.setTitle("删除");
        itemDel.setTitleSize(18);
        itemDel.setTitleColor(Color.WHITE);
        menu.addMenuItem(itemDel);
    }

    public void createMenu(SwipeMenu menu) {
        // Test Code
        SwipeMenuItem item = new SwipeMenuItem(mContext);
        item.setTitle("Item 1");
        item.setBackground(new ColorDrawable(Color.GRAY));
        item.setWidth(300);
        menu.addMenuItem(item);

        item = new SwipeMenuItem(mContext);
        item.setTitle("Item 2");
        item.setBackground(new ColorDrawable(Color.RED));
        item.setWidth(300);
        menu.addMenuItem(item);
    }

    @Override
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return mAdapter.isEnabled(position);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return mAdapter;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
