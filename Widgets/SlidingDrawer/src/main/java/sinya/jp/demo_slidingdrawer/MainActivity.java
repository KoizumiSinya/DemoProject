package sinya.jp.demo_slidingdrawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_layout);
        context = this;

        final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);

        LinearLayout map = (LinearLayout) findViewById(R.id.map_layout);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingDrawer.isOpened()) {
                    slidingDrawer.animateClose();
                }
            }
        });

        final LinearLayout hander = (LinearLayout) findViewById(R.id.handle);
        TextView textView = (TextView) findViewById(R.id.cur_address);
        final ImageView img = (ImageView) findViewById(R.id.img_arow);

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                img.setImageResource(R.drawable.abc_spinner_mtrl_am_alpha);
//                hander.setVisibility(View.GONE);
            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                img.setImageResource(R.drawable.abc_ic_go_search_api_mtrl_alpha);
//                hander.setVisibility(View.VISIBLE);
            }
        });

        slidingDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollStarted() {

            }

            @Override
            public void onScrollEnded() {

            }
        });


        ListView listview = (ListView) findViewById(R.id.content);
        listview.setAdapter(new AddressAdapter());

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

    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.item_address, null);
            return view;
        }
    }
}
