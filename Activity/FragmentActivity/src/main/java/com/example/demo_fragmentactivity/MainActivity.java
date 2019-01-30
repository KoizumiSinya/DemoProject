package com.example.demo_fragmentactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 使用这种方式搭建Fragment的activity，必须是继承FragmentActivity
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout ll_tab_one, ll_tab_two;
    private FragmentManager fragmentManager;
    private Bundle bundle = new Bundle();
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        ll_tab_one = (LinearLayout) findViewById(R.id.ll_tab_one);
        ll_tab_one.setOnClickListener(this);
        ll_tab_two = (LinearLayout) findViewById(R.id.ll_tab_two);
        ll_tab_two.setOnClickListener(this);

        ll_tab_one.setSelected(true);
        ll_tab_two.setSelected(false);
        bundle.putString("fragment", "第一页");
        fragment = FragmentOne.newInstance(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commitAllowingStateLoss();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_tab_one:
                ll_tab_one.setSelected(true);
                ll_tab_two.setSelected(false);
                bundle.putString("fragment", "第一页");
                fragment = FragmentOne.newInstance(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commitAllowingStateLoss();
                break;

            case R.id.ll_tab_two:
                ll_tab_two.setSelected(true);
                ll_tab_one.setSelected(false);
                bundle.putString("fragment", "第二页");
                fragment = FragmentTwo.newInstance(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
}
