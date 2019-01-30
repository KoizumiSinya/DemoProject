package jp.sinya.swipeback.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import jp.sinya.swipeback.demo.library2.SwipeBackActivity;
import jp.sinya.swipeback.demo.library2.SwipeBackLayout;

public class Library2FragmentActivity extends SwipeBackActivity {
    private int count = 0;
    private Button btnBack;
    private Button btnNext;
    private Button btnFinish;

    public int getCount() {
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initButtons();

        setSwipeBackEnable(true); // 是否允许滑动
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {
            @Override
            public void onDragStateChange(int state) {
                // Drag state
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                // 触摸的边缘flag
            }

            @Override
            public void onDragScrolled(float scrollPercent) {
                // 滑动百分比
            }
        });
    }

    private void initButtons() {
        btnBack = findViewById(R.id.activity_fragment_btn_back);
        btnFinish = findViewById(R.id.activity_fragment_btn_finish);
        btnNext = findViewById(R.id.activity_fragment_btn_next);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Library2ItemFragment();
                count++;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_fragment_frame, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnNext.performClick();
    }


    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
        // 下面是默认实现:
        // return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }
}
