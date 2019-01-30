package jp.sinya.refreshframework.simple.activity;

import android.app.Activity;
import android.os.Bundle;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.pullableview.PullToRefreshLayout;
import jp.sinya.refreshframework.simple.listener.MyListener;


public class PullableScrollViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_scrollview);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new MyListener());
    }
}
