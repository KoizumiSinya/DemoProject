package jp.sinya.swipeexpandlistviewdemo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import jp.sinya.swipeexpandlistviewdemo.R;

/**
 * @author KoizumiSinya
 * @date 2016/4/10.
 */
public class MyAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private Map<String, List<String>> childMap;

    public MyAdapter() {
    }

    public  MyAdapter(Context context) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group_item, null);
            convertView.setClickable(true);
            viewHolder.friendGroup = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.friendGroup.setText(groupList.get(groupPosition));
        // 禁止伸展
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_child, null);
            viewHolder.friendName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.head = (ImageView) convertView.findViewById(R.id.head);
            viewHolder.deleteFriend = (TextView) convertView.findViewById(R.id.delete_friend);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.friendName.setText(childMap.get(groupList.get(groupPosition)).get(childPosition));
        viewHolder.deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除" + viewHolder.friendName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        TextView friendGroup;
        TextView friendName;
        TextView deleteFriend;
        ImageView head;
    }

}
