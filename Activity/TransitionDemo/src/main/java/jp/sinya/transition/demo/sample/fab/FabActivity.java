package jp.sinya.transition.demo.sample.fab;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FabActivity extends BaseActivity {
    FloatingActionButton btnCircle;
    FloatingActionButton btnNo;
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab;
    }

    @Override
    protected void initViews() {
        btnCircle = findViewById(R.id.btn_circle);
        btnNo = findViewById(R.id.btn_no);
        btn = findViewById(R.id.btn);

        btnCircle.setOnClickListener(listener);
        btnNo.setOnClickListener(listener);
        btn.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_no:
                    TransitionsHelper.startActivity(FabActivity.this, FabNoCircleActivity.class, btnNo);
                    break;
                case R.id.btn_circle:
                    TransitionsHelper.startActivity(FabActivity.this, FabCircleActivity.class, btnCircle);
                    break;
                case R.id.btn:
                    TransitionsHelper.startActivity(FabActivity.this, ButtonActivity.class, btn);
                    break;
            }

        }
    };
}
