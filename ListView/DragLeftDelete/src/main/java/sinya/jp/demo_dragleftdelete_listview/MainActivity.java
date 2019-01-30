package sinya.jp.demo_dragleftdelete_listview;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sinya.jp.demo_dragleftdelete_listview.widget.DragListView;
import sinya.jp.demo_dragleftdelete_listview.widget.DragListViewItem;


public class MainActivity extends Activity {

    private List<ApplicationInfo> mAppList;
    private DragListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draglist);

        mAppList = getPackageManager().getInstalledApplications(0);

        mListView = (DragListView) findViewById(R.id.listView);
        mListView.setAdapter(new AppAdapter(mAppList));
    }

    class AppAdapter extends BaseAdapter {
        private List<ApplicationInfo> data;

        public AppAdapter(List<ApplicationInfo> appList) {
            data = appList;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int loc = position;
            ViewHolder holder = null;
            View menuView = null;
            View titleView = null;

            if (convertView == null) {

                convertView = View.inflate(getApplicationContext(), R.layout.item_swipe_content, null);
                menuView = View.inflate(getApplicationContext(), R.layout.item_swipe_menu, null);
                titleView = View.inflate(getApplicationContext(), R.layout.item_swipe_title, null);


                convertView = new DragListViewItem(titleView, convertView, menuView);

                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ApplicationInfo item = getItem(position);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            holder.tv_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(MainActivity.this, "open:" + loc, Toast.LENGTH_SHORT).show();
                }
            });
            holder.tv_del.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;
            TextView tv_open, tv_del;
            TextView txt_title;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_open = (TextView) view.findViewById(R.id.tv_open);
                tv_del = (TextView) view.findViewById(R.id.tv_del);
                txt_title = (TextView) view.findViewById(R.id.title);
                view.setTag(this);
            }
        }
    }
}
