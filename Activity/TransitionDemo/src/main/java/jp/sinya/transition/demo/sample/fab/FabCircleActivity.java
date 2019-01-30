package jp.sinya.transition.demo.sample.fab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.library.TransitionsHelper;
import jp.sinya.transition.demo.library.bean.InfoBean;
import jp.sinya.transition.demo.library.method.ColorShowMethod;
import jp.sinya.transition.demo.sample.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FabCircleActivity extends BaseActivity {
    TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab_detail;
    }

    @Override
    protected void initViews() {
        tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FabCircleActivity.this, "click", Toast.LENGTH_SHORT).show();

            }
        });

        TransitionsHelper.build(this).setShowMethod(new ColorShowMethod(R.color.bg_purple, R.color.bg_teal) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(placeholder, "rotation", 0, 180), ObjectAnimator.ofFloat(placeholder, "scaleX", 1, 0), ObjectAnimator.ofFloat(placeholder, "scaleY", 1, 0));
                set.setInterpolator(new AccelerateInterpolator());
                set.setDuration(showDuration / 4 * 5).start();
                //placeholder.setVisibility(View.GONE);
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {

            }
        }).setExposeColor(getResources().getColor(R.color.bg_teal)).show();
    }

}
