package com.example.demo_popwindowmenu.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.demo_popwindowmenu.R;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Sinya
 * @Editor:
 * @Date: 2015/11/12. 10:42
 * @Update:
 */
public class MyPopWindow1 {
    private List<String> itemStringList;
    private Context context;
    private PopupWindow popupWindow;
    private ListView listView;
    private int popWidth;


    public MyPopWindow1(Context context, List<String> itemStringList, int popWidth) {
        this.context = context;
        this.popWidth = popWidth;
        this.itemStringList = itemStringList;

        View view = LayoutInflater.from(context).inflate(R.layout.view_popwindow_style1, null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());
        listView.setFocusable(true);
        listView.setFocusableInTouchMode(true);

        popupWindow = new PopupWindow(view, dip2px(context, popWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public MyPopWindow1(Context context, String[] itemStringList, int popWidth) {
        this.context = context;
        this.popWidth = popWidth;
        this.itemStringList = Arrays.asList(itemStringList);

        View view = LayoutInflater.from(context).inflate(R.layout.view_popwindow_style1, null);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new PopAdapter());
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);

        popupWindow = new PopupWindow(view, dip2px(context, popWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    /**
     * 显示popwindow在这个parent的正下方
     *
     * @param parent
     * @param padding
     */
    public void showAsRightDown(View parent, int padding) {
        popupWindow.showAsDropDown(parent, parent.getWidth() / 2 - dip2px(context, popWidth) / 2, dip2px(context, padding));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    /**
     * @param parent
     * @param x
     * @param y
     */
    public void showAsRightDown(View parent, int x, int y) {
        popupWindow.showAsDropDown(parent, x, y);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        popupWindow.setOnDismissListener(listener);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    private class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.view_pop_item_style1, null);
                viewHolder.groupItem = (TextView) convertView.findViewById(R.id.tx_pop_item_content);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.groupItem.setText(itemStringList.get(position));
            return convertView;
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }

        private class ViewHolder {
            TextView groupItem;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
