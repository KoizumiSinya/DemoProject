package jp.sinya.transition.demo.sample.intent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.expose.FoldExposeView;
import jp.sinya.transition.demo.library.method.ColorShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2017/11/27.
 * email : mr_immortalz@qq.com
 */

public class ForResultDetailActivity extends BaseActivity {
    TextView tv;
    Button btnBack;

    protected static final String TRANSITION_DATA = "data";
    protected static int REQUEST_CODE = 10050;
    protected static int RESULT_CODE = 10050;

    @Override
    public int getLayoutId() {
        return R.layout.activity_for_result_detail;
    }

    @Override
    protected void initViews() {
        tv = findViewById(R.id.tv);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ForResultDetailActivity.TRANSITION_DATA, "receive from ForResultDetailActivity");
                setResult(ForResultDetailActivity.RESULT_CODE, intent);
                finish();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            tv.setText(intent.getStringExtra(TRANSITION_DATA));
        }
        TransitionsHelper.build(this).setShowMethod(new ColorShowMethod(R.color.bg_teal_light, R.color.bg_teal) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(placeholder, "scaleX", 1, 0), ObjectAnimator.ofFloat(placeholder, "scaleY", 1, 0));
                set.setInterpolator(new AccelerateInterpolator());
                set.setDuration(showDuration / 4 * 5).start();
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {

            }
        }).setExposeView(new FoldExposeView(this, FoldExposeView.FOLD_TYPE_VERTICAL)).setExposeColor(getResources().getColor(R.color.bg_teal)).show();
    }

}
