package sinya.jp.demo_actionbar;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements DrawerLayout.DrawerListener {

    //这个ActionBar需要导入V7包的 否则会报错
    private android.support.v7.app.ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();
    }

    private void initActionBar() {
        //获取actionbar实例
        actionBar = getSupportActionBar();
        actionBar.setTitle("ActionBarDemo");
        actionBar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));
        actionBar.setDisplayHomeAsUpEnabled(true);

        //获取drawerlayout布局
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);

        //这个ActionBarToggle需要导入V4包的 否则会报错
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, android.R.drawable.ic_menu_add, R.string.toggele_open, R.string.toggele_close);

        drawerToggle.syncState();
        drawerLayout.setDrawerListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                boolean selected = drawerToggle.onOptionsItemSelected(item);
                if (selected) {
                    return true;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerToggle.onDrawerClosed(drawerView);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerToggle.onDrawerOpened(drawerView);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        drawerToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        drawerToggle.onDrawerStateChanged(newState);
    }
}
