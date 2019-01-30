package jp.sinya.stickyexpandlistview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

/**
 * @author KoizumiSinya
 * @date 2016/4/8.
 */
public class StudentListAdapter extends BaseExpandableListAdapter implements SectionIndexer {

    private List<Student> data;
    private Context mContext;

    public StudentListAdapter(List<Student> data, Context context) {
        this.data = data;
        mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_student_expande_listview, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_letter);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int section = getSectionForPosition(groupPosition);
        if (groupPosition == getPositionForSection(section)) {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.data.get(groupPosition).letter);
        viewHolder.tvName.setText(this.data.get(groupPosition).name);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        /*TextView tvName = new TextView(mContext);
        tvName.setVisibility(View.VISIBLE);
        tvName.setPadding(50, 50, 50, 50);
        tvName.setGravity(Gravity.CENTER | Gravity.LEFT);
        tvName.setTextColor(Color.BLACK);
        tvName.setText(data.get(groupPosition).name);
        convertView = tvName;*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getGroupCount(); i++) {
            char firstChar = data.get(i).letter.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (data != null && data.size() > 0) {
            if (!TextUtils.isEmpty(data.get(position).letter)) {
                return data.get(position).letter.charAt(0);
            }
        }
        return 0;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvName;
    }
}
