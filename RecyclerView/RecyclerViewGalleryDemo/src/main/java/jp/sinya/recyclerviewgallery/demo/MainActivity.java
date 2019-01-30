package jp.sinya.recyclerviewgallery.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.recyclerviewgallery.demo.lib1.BlurBitmapUtils;
import jp.sinya.recyclerviewgallery.demo.lib1.CardAdapter;
import jp.sinya.recyclerviewgallery.demo.lib1.CardScaleHelper;
import jp.sinya.recyclerviewgallery.demo.lib1.ViewSwitchUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageView mBlurView;
    private List<Integer> mList = new ArrayList<>();
    private CardScaleHelper mCardScaleHelper = null;
    private Runnable mBlurRunnable;
    private int mLastPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mList.add(R.mipmap.test_bake);
        mList.add(R.mipmap.test_boil);
        mList.add(R.mipmap.test_defrost);
        mList.add(R.mipmap.test_fry);
        mList.add(R.mipmap.test_fully);
        mList.add(R.mipmap.test_grill);
        mList.add(R.mipmap.test_keeping_warm);
        mList.add(R.mipmap.test_roasting);
        mList.add(R.mipmap.test_scaling);
        mList.add(R.mipmap.test_sous_vide);
        mList.add(R.mipmap.test_steaming);

        mRecyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CardAdapter(mList));

        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper(false);
        mCardScaleHelper.setCurrentItemPos(2);
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);

        initBlurBackground();
    }

    private void initBlurBackground() {
        mBlurView = findViewById(R.id.blurView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange();
                }
            }
        });

        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
        if (mLastPos == mCardScaleHelper.getCurrentItemPos()) {
            return;
        }
        mLastPos = mCardScaleHelper.getCurrentItemPos();
        final int resId = mList.get(mCardScaleHelper.getCurrentItemPos());
        mBlurView.removeCallbacks(mBlurRunnable);
        mBlurRunnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
                ViewSwitchUtils.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtils.getBlurBitmap(mBlurView.getContext(), bitmap, 15));
            }
        };
        mBlurView.postDelayed(mBlurRunnable, 500);
    }
}
