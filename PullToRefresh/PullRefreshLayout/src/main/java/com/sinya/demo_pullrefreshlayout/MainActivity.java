package com.sinya.demo_pullrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sinya.demo_pullrefreshlayout.widget.PullRefreshLayout;
import com.sinya.demo_pullrefreshlayout.widget.PullRefreshLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private PullRefreshLayout refreshLayout;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    refreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (PullRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
    }

    public void changeType(View v) {
        if (refreshLayout.getRefreshStyle() == 3) {
            refreshLayout.setRefreshStyle(0);
        }else{
            refreshLayout.setRefreshStyle(refreshLayout.getRefreshStyle() + 1);
        }
    }
}
