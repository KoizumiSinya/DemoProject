package sap_mobile_newapi.demo_slidingmenudelete;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Author : Sinya Koizumi
 * Editor：
 * CreateDate：2015/10/15 15:39
 * UpdateDate：2015/10/15 15:39
 * UpdateDescription；
 *
 * @Version
 */
public class FirstActivity extends Activity{

    private Context context;
    String[] items = {"item0","item1","item2","item3","item4","item5","item6","item7",
            "item8","item9","item10","item11","item12","item13","item14","item15",
            "item16","item17","item18","item19","item20","item21","item22","item23",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_list);
        context = this;

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new Myadapter());
    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holderView;

            if (convertView == null) {
                holderView = new HolderView();
                convertView = View.inflate(context, R.layout.activity_first, null);
                holderView.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holderView);

            } else {
                holderView = (HolderView) convertView.getTag();
            }

            holderView.name.setText(items[position]);

            return convertView;
        }


    }

    class HolderView{
        TextView name;
    }
}
