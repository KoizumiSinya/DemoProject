package jp.sinya.coordinatorlayout.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Sinya
 * @date 2018/10/30. 13:00
 * @edithor
 * @date
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> items;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.items = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",i);
        items.get(i).setArguments(bundle);
        return items.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
