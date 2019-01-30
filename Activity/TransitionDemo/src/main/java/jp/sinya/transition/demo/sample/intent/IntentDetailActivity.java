package jp.sinya.transition.demo.sample.intent;

import android.content.Intent;
import android.widget.TextView;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/11/1.
 * email : mr_immortalz@qq.com
 */

public class IntentDetailActivity extends BaseActivity {

    protected static final String TRANSITION_DATA = "data";
    TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_intent_detail;
    }

    @Override
    protected void initViews() {
        tv = findViewById(R.id.tv);

        Intent intent = getIntent();
        if (intent != null) {
            tv.setText(intent.getStringExtra(TRANSITION_DATA));
        }
        TransitionsHelper.build(this).show();
    }
}
