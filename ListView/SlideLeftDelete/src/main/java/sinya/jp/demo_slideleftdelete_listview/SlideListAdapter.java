package sinya.jp.demo_slideleftdelete_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/10 17:46
 * editor：
 * updateDate：2015/9/10 17:46
 */
public class SlideListAdapter extends ArrayAdapter<String> {

    public SlideListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, null);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.txt_view);
        textView.setText(getItem(position));
        return view;
    }
}
