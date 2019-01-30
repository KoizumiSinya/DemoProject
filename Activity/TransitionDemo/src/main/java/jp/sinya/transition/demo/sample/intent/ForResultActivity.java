package jp.sinya.transition.demo.sample.intent;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2017/11/27.
 * email : mr_immortalz@qq.com
 */

public class ForResultActivity extends BaseActivity {

    TextView tv;
    Button btnGo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_for_result;
    }

    @Override
    protected void initViews() {
        tv = findViewById(R.id.tv);

        btnGo = findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForResultActivity.this, ForResultDetailActivity.class);
                intent.putExtra(ForResultDetailActivity.TRANSITION_DATA, "This is immortalZ");
                TransitionsHelper.startActivityForResult(ForResultActivity.this, intent, ForResultDetailActivity.REQUEST_CODE, null, btnGo, null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ForResultDetailActivity.REQUEST_CODE && resultCode == ForResultDetailActivity.RESULT_CODE) {
            String str = data.getStringExtra(ForResultDetailActivity.TRANSITION_DATA);
            tv.setText(str);
        }
    }
}
