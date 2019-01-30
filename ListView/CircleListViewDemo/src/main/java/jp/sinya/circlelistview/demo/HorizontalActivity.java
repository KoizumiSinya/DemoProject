package jp.sinya.circlelistview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;

import java.lang.reflect.Field;

public class HorizontalActivity extends Activity {

    private ListView lv;
    private HorizontalListView lvHor;
    Gallery gallery;
    private int[] images = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3};
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            for (int i = 0; i < lvHor.getChildCount(); i++) {
                lvHor.getChildAt(i).invalidate();
            }
            sendEmptyMessageDelayed(0, 40);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list);

        lv = (ListView) findViewById(R.id.lv);
        lvHor = (HorizontalListView) findViewById(R.id.lv_hor);
        gallery = (Gallery) findViewById(R.id.lv_ga);
        lv.setVisibility(View.GONE);
        gallery.setVisibility(View.GONE);
        // lv.setAdapter(new MyAdapter());
        lvHor.setAdapter(new MyAdapter());

        // lvHor.setOnScrollListener(new OnScrollListener() {
        //
        // @Override
        // public void onScrollStateChanged(AbsListView view, int scrollState) {
        //
        // }
        //
        // @Override
        // public void onScroll(AbsListView view, int firstVisibleItem, int
        // visibleItemCount, int totalItemCount) {
        // for (int i = 0; i < lv.getChildCount(); i++) {
        // lv.getChildAt(i).invalidate();
        // }
        // }
        // });
        handler.sendEmptyMessageDelayed(0, 50);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                lvHor.setSelection(6);

            }
        }, 100);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9999;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressWarnings("null")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                MatrixView m = (MatrixView) LayoutInflater.from(HorizontalActivity.this).inflate(R.layout.view_list_item, null);
                // View m =
                // LayoutInflater.from(MainActivity.this).inflate(R.layout.view_list_item_test,
                // null);
                m.setParentHeight(lvHor.getHeight());
                m.setParentWidth(lvHor.getWidth());
                convertView = m;
            }
            ((MatrixView) convertView).setMode(MatrixView.MODE_HORIZONTAL);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            imageView.setImageResource(images[position % images.length]);

            return convertView;
        }

    }


    public void changeGroupFlag(Object obj) throws Exception {
        Field[] f = obj.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredFields();
        for (Field tem : f) {
            if (tem.getName().equals("mGroupFlags")) {
                tem.setAccessible(true);
                Integer mGroupFlags = (Integer) tem.get(obj);
                int newGroupFlags = mGroupFlags & 0xfffff8;
                tem.set(obj, newGroupFlags);
            }
        }
    }
}
