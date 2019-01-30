package jp.sinya.transition.demo.sample.intent;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/11/1.
 * email : mr_immortalz@qq.com
 */

public class IntentActivity extends BaseActivity {
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_intent;
    }

    @Override
    protected void initViews() {
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentActivity.this, IntentDetailActivity.class);
                intent.putExtra(IntentDetailActivity.TRANSITION_DATA, "This is immortalZ");
                TransitionsHelper.startActivity(IntentActivity.this, intent, btn);
            }
        });
    }

}
