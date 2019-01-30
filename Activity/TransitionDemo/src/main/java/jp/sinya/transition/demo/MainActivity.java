package jp.sinya.transition.demo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import jp.sinya.transition.R;
import jp.sinya.transition.demo.sample.BaseActivity;
import jp.sinya.transition.demo.sample.fab.FabActivity;
import jp.sinya.transition.demo.sample.fragment.FActivity;
import jp.sinya.transition.demo.sample.image.ImageActivity;
import jp.sinya.transition.demo.sample.intent.ForResultActivity;
import jp.sinya.transition.demo.sample.intent.IntentActivity;
import jp.sinya.transition.demo.sample.recyclerview.RvActivity;

public class MainActivity extends BaseActivity {

    Button btnImage;
    Button btnRecycleView;
    Button btnFab;
    Button btnFragment;
    Button btnIntent;
    Button btnForResult;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        btnImage = findViewById(R.id.btn_image);
        btnImage.setOnClickListener(listener);
        btnRecycleView = findViewById(R.id.btn_recycleview);
        btnRecycleView.setOnClickListener(listener);
        btnFab = findViewById(R.id.btn_fab);
        btnFab.setOnClickListener(listener);
        btnFragment = findViewById(R.id.btn_fragment);
        btnFragment.setOnClickListener(listener);
        btnIntent = findViewById(R.id.btn_intent);
        btnIntent.setOnClickListener(listener);
        btnForResult = findViewById(R.id.btn_for_result);
        btnForResult.setOnClickListener(listener);
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_image:
                    gotoNextActivity(ImageActivity.class);
                    break;
                case R.id.btn_recycleview:
                    gotoNextActivity(RvActivity.class);
                    break;
                case R.id.btn_fab:
                    gotoNextActivity(FabActivity.class);
                    break;
                case R.id.btn_fragment:
                    gotoNextActivity(FActivity.class);
                    break;
                case R.id.btn_intent:
                    gotoNextActivity(IntentActivity.class);
                    break;
                case R.id.btn_for_result:
                    gotoNextActivity(ForResultActivity.class);
                    break;
            }
        }
    };

    private void gotoNextActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}