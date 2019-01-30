package com.sap360.demo_springlistviewscrollview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.sap360.demo_springlistviewscrollview.widget.OverScrollListView;
import com.sap360.demo_springlistviewscrollview.widget.OverScrollView;
import com.sap360.demo_springlistviewscrollview.widget.SpringListView;

public class MainActivity extends Activity {

    private Context mContext;
    private OverScrollView mScrollView;
    private OverScrollListView mListView;
    private SpringListView mSpringListView;

    private String[] texts = new String[]{"GGGGGGG", "TTTTTTT", "VVVVVVV", "LLLLLLL", "EEEEEEE", "SSSSSS", "ZZZZZZZ", //
            "GGGGGGG", "TTTTTTT", "VVVVVVV", "LLLLLLL", "EEEEEEE", "SSSSSS", "ZZZZZZZ", //
            "GGGGGGG", "TTTTTTT", "VVVVVVV", "LLLLLLL", "EEEEEEE", "SSSSSS", "ZZZZZZZ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initOverScrollView();
        initOverListView();
        initSpringListView();

    }

    private void initOverScrollView() {
        mScrollView = (OverScrollView) findViewById(R.id.over_scrollview);

        //设置弹动的距离是多少 默认是150
        mScrollView.setOverScrollOffsetY(400);
        //可以对view添加头、或尾 界面图片等
        mScrollView.setOverscrollHeaderView(View.inflate(mContext, R.layout.view_header, null));
        //还可以设置监听
        mScrollView.setOverScrollListener(new OverScrollView.OverScrolledListener() {
            @Override
            public void overScrolledTop(int scrollY, int maxY, boolean clampedY, boolean didFinishOverScroll) {

            }

            @Override
            public void overScrolledBottom(int scrollY, int maxY, boolean clampedY, boolean didFinishOverScroll) {

            }
        });
    }

    private void initOverListView() {
        mListView = (OverScrollListView) findViewById(R.id.over_listview);
        mListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, texts));

        mListView.setOverScrollListener(new OverScrollListView.OverScrolledListener() {
            @Override
            public void overScrolled(int scrollY, int maxY, boolean exceededOffset, boolean didFinishOverScroll) {
                // You can check the scrollY value and use it however you need. (0-maxY)
                if (scrollY < (maxY / 2)) { // you are on the lower half of the offset.
                    // do something
                } else if (scrollY == maxY) { // you are at full offset.
                    // do Something
                } else {  // anything below half offset.
                    // do Something
                }

                // Still pulling down after full offset. Value satying at maxY.
                if (exceededOffset) {
                    // do something
                }

                // You can check if the view is back to 0 offset after its been pulled.
                // This will currently be true if you swipe it back or let it go and let
                // the view bounce.
                if (didFinishOverScroll) {
                    // do someting
                }
            }
        });
    }

    private void initSpringListView() {
        mSpringListView = (SpringListView) findViewById(R.id.spring_listview);
        mSpringListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, texts));
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn1:
                mScrollView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                mSpringListView.setVisibility(View.GONE);
                break;

            case R.id.btn2:
                mListView.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
                mSpringListView.setVisibility(View.GONE);
                break;

            case R.id.btn3:
                mSpringListView.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
                mListView.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }
}
