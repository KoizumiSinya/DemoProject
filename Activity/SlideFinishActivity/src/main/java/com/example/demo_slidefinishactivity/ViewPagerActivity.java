package com.example.demo_slidefinishactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.demo_slidefinishactivity.widget.SlideFinishLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private Context context;
    private List<View> views;
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        context = this;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        views = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TextView tx = new TextView(context);
            tx.setText(i + "页");
            tx.setTextSize(20);
            tx.setGravity(Gravity.CENTER);
            views.add(tx);
        }

        adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);

        ((SlideFinishLayout) findViewById(R.id.root_layout)).setFinishListener(new SlideFinishLayout.onSlideFinishListener() {
            @Override
            public void onSlideFinish() {
                ViewPagerActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(views.get(position), 0);

            return views.get(position);
        }
    }
}
