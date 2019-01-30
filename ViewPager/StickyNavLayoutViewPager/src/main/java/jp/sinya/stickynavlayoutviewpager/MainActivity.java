package jp.sinya.stickynavlayoutviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.stickynavlayoutviewpager.widget.SimpleViewPagerIndicator;
import jp.sinya.stickynavlayoutviewpager.widget.TabBandViewPagerScrollView;

public class MainActivity extends Activity {

    private List<View> views;
    private TabBandViewPagerScrollView tabScroll;
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private String[] tabs = new String[]{"微博", "腾讯"};


    private TabFragment2[] mFragments = new TabFragment2[tabs.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views = new ArrayList<>();
        List<String> item = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            item.add("Item" + i);
        }

        ListView listView1 = (ListView) View.inflate(this, R.layout.listview, null);
        ListView listView2 = (ListView) View.inflate(this, R.layout.listview, null);

        listView1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item));
        listView2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item));

        views.add(listView1);
        views.add(listView2);

        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        mViewPager.setAdapter(new ViewPagerAdpater());


        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mIndicator.setTitles(tabs);
        //tabScroll = (TabBandViewPagerScrollView) findViewById(R.id.id_stickynavlayout_indicator);
        //tabScroll.setViewPager(mViewPager);
        //tabScroll.setTabTips(new int[]{10, 0});

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class FragmentViewPagerAdapter extends FragmentPagerAdapter {
        public FragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

    }

    ;


    class ViewPagerAdpater extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (tabs != null && tabs.length > 0) {
                return tabs[position % tabs.length];
            }
            return "";
        }
    }
}
