package jp.sinya.transition.demo.sample.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvActivity extends BaseActivity {

    RecyclerView rv;
    private List<String> mList;
    private RvAdapter mAdapter;
    String imgUrl = "https://avatars1.githubusercontent.com/u/14830574?s=460&v=4";

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initViews() {
        rv = findViewById(R.id.rv);


        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(imgUrl);
        }
        mAdapter = new RvAdapter(this, R.layout.item_rv, mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }
}
