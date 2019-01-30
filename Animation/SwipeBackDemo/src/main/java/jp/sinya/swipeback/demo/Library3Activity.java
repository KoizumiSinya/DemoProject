package jp.sinya.swipeback.demo;

import android.os.Bundle;

import jp.sinya.swipeback.demo.library3.core.anim.DefaultHorizontalAnimator;
import jp.sinya.swipeback.demo.library3.core.anim.FragmentAnimator;
import jp.sinya.swipeback.demo.library3.swipe.SwipeBackActivity;

public class Library3Activity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library3);

        if (findFragment(Library3ItemFragment.class) == null) {
            loadRootFragment(R.id.activity_library3_frame, new Library3ItemFragment());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
