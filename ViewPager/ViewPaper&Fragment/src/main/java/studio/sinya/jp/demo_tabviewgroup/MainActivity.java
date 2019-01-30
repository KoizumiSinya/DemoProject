package studio.sinya.jp.demo_tabviewgroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import studio.sinya.jp.demo_tabviewgroup.fragment.FragmentFive;
import studio.sinya.jp.demo_tabviewgroup.fragment.FragmentFour;
import studio.sinya.jp.demo_tabviewgroup.fragment.FragmentOne;
import studio.sinya.jp.demo_tabviewgroup.fragment.FragmentThree;
import studio.sinya.jp.demo_tabviewgroup.fragment.FragmentTwo;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private LinearLayout tab1, tab2, tab3, tab4, tab5;
    private LinearLayout[] tabs = new LinearLayout[5];
    private Fragment fragmentOne, fragmentTwo, fragmentThree, fragmentFour, fragmentFive;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

        initViewPager();

    }

    private void initTab() {

        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab4 = (LinearLayout) findViewById(R.id.tab4);
        tab5 = (LinearLayout) findViewById(R.id.tab5);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);

        tabs[0] = tab1;
        tabs[1] = tab2;
        tabs[2] = tab3;
        tabs[3] = tab4;
        tabs[4] = tab5;

    }


    private void initViewPager() {

        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentFour = new FragmentFour();
        fragmentFive = new FragmentFive();

        fragments = new ArrayList<>();
        fragments.add(fragmentOne);
        fragments.add(fragmentTwo);
        fragments.add(fragmentThree);
        fragments.add(fragmentFour);
        fragments.add(fragmentFive);

        TabFragmentViewPagerAdapter adapter = new TabFragmentViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager_fragment);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setCurrentView(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                setCurrentView(0);
                break;

            case R.id.tab2:
                setCurrentView(1);
                break;

            case R.id.tab3:
                setCurrentView(2);
                break;

            case R.id.tab4:
                setCurrentView(3);
                break;

            case R.id.tab5:
                setCurrentView(4);
                break;
        }
    }

    private void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
        selectTab(index);
    }

    private void selectTab(int index) {
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setSelected(false);
        }
        tabs[index].setSelected(true);
    }

    class TabFragmentViewPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
