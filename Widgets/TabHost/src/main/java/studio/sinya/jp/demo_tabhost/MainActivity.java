package studio.sinya.jp.demo_tabhost;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import studio.sinya.jp.demo_tabhost.fragment.FragmentFour;
import studio.sinya.jp.demo_tabhost.fragment.FragmentOne;
import studio.sinya.jp.demo_tabhost.fragment.FragmentThree;
import studio.sinya.jp.demo_tabhost.fragment.FragmentTwo;


public class MainActivity extends ActionBarActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(context, getSupportFragmentManager(), android.R.id.tabcontent);
        //取消每个导航的按钮bar之间的分割线
        tabHost.getTabWidget().setDividerDrawable(null);

        LinearLayout layout;
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab_one, null, false);
        tabHost.addTab(tabHost.newTabSpec("one").setIndicator(layout), FragmentOne.class, null);

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab_two, null, false);
        tabHost.addTab(tabHost.newTabSpec("two").setIndicator(layout), FragmentTwo.class, null);

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab_three, null, false);
        tabHost.addTab(tabHost.newTabSpec("three").setIndicator(layout), FragmentThree.class, null);

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tab_four, null, false);
        tabHost.addTab(tabHost.newTabSpec("four").setIndicator(layout), FragmentFour.class, null);

        tabHost.setCurrentTab(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
