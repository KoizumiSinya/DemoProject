package com.example.demo_tableitemviewpaper;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private Context context;
    private ViewPager viewpager;
    private MyViewPagerAdapter adapter;
    private ArrayList<View> views;

    private LinearLayout tabOne, tabTwo;
    private int tabItems;
    private int index;
    private int moveX;
    private int pointWidth;
    private ImageView point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initView();
        initTable();
        initViewPager();
    }

    private void initView() {
        views = new ArrayList<>();
        View view1 = View.inflate(context, R.layout.view_one, null);
        views.add(view1);
        View view2 = View.inflate(context, R.layout.view_two, null);
        views.add(view2);
        tabItems = views.size();
    }


    private void initTable() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        point = (ImageView) findViewById(R.id.img_point);
        tabOne = (LinearLayout) findViewById(R.id.ll_tab_one);
        tabTwo = (LinearLayout) findViewById(R.id.ll_tab_two);
        tabOne.setOnClickListener(this);
        tabTwo.setOnClickListener(this);

        //获取屏幕宽度
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        //指示条图片宽度
        pointWidth = BitmapFactory.decodeResource(getResources(), R.mipmap.img_point).getWidth();

        //计算偏移量
        moveX = (screenWidth / tabItems - pointWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(moveX, 0);
        //设置指示条的初始位置
        point.setImageMatrix(matrix);
    }

    private void initViewPager() {
        adapter = new MyViewPagerAdapter(views);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setCurrentTableItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setCurrentTableItem(0);
    }

    private void setCurrentTableItem(int postion) {

        tabOne.setSelected(false);
        tabTwo.setSelected(false);

        switch (postion) {
            case 0:
                tabOne.setSelected(true);
                break;

            case 1:
                tabTwo.setSelected(true);
                break;

            default:
                break;
        }
        imageStartAnimation(postion);
    }

    /**
     * 指示条移动的动画
     *
     * @param position
     */
    private void imageStartAnimation(int position) {

        int isWidth = tabItems % 2 == 0 ? 1 : 0;
        int x = moveX * tabItems + isWidth * pointWidth;

        Animation animation = new TranslateAnimation(x * index, x * position, 0, 0);
        index = position;
        // 设置动画停止在结束位置
        animation.setFillAfter(true);
        // 设置动画时间
        animation.setDuration(300);
        // 启动动画
        point.startAnimation(animation);
    }


    // [+] Click事件

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_one:
                viewpager.setCurrentItem(0);
                break;

            case R.id.ll_tab_two:
                viewpager.setCurrentItem(1);
                break;

            default:
                break;
        }
    }

    // [-] Click事件
    // [+] PagerAdapter

    class MyViewPagerAdapter extends PagerAdapter {
        ArrayList<View> viewLists;

        public MyViewPagerAdapter(ArrayList<View> lists) {
            viewLists = lists;
        }

        public void setViewLists(ArrayList<View> viewLists) {
            this.viewLists = viewLists;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (viewLists != null && viewLists.size() > 0) {
                return viewLists.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(viewLists.get(position));
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(viewLists.get(position), 0);

            return viewLists.get(position);
        }
    }

    // [-] PagerAdapter
}
