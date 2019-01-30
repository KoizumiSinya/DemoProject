package sinya.jp.demo_slideleftdelete_listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private SlideListView listView;
    private SlideListAdapter adapter;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();

        listView = (SlideListView) findViewById(R.id.list_view);
        adapter = new SlideListAdapter(this, 0, data);
        listView.setAdapter(adapter);

        listView.setOnDeleteListener(new SlideListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                data.remove(index);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initList() {
        for (int i = 0; i < 20; i++) {
            data.add("item " + i);
        }
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
