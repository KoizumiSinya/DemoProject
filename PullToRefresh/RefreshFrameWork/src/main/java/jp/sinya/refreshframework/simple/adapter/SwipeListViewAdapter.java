package jp.sinya.refreshframework.simple.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SwipeListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> strs;

    public SwipeListViewAdapter(Context mContext, List<String> strs) {
        this.mContext = mContext;
        this.strs = strs;
    }

    public void setData(List<String> strs) {
        this.strs = strs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (strs != null && strs.size() > 0) {
            return strs.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (strs != null && strs.size() > 0) {
            return strs.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoderView hoderView;
        if (convertView == null) {
            hoderView = new HoderView();
            convertView = new TextView(mContext);
            hoderView.tv = (TextView) convertView;

            convertView.setTag(hoderView);
        } else {
            hoderView = (HoderView) convertView.getTag();
        }

        String value = strs.get(position);
        hoderView.tv.setPadding(50, 50, 50, 50);
        hoderView.tv.setText(value);
        hoderView.tv.setGravity(Gravity.CENTER);
        hoderView.tv.setTextColor(Color.BLACK);

        return convertView;
    }

    class HoderView {
        TextView tv;
    }
}