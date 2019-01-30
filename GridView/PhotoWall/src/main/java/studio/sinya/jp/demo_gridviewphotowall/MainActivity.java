package studio.sinya.jp.demo_gridviewphotowall;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import studio.sinya.jp.demo_gridviewphotowall.adapter.PhotoWallAdapter;
import studio.sinya.jp.demo_gridviewphotowall.bean.ImageBean;


public class MainActivity extends Activity {

    private GridView gridView;
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        gridView = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this, 0, ImageBean.imageUrl, gridView);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.cancelAllTasks();
    }
}
