package jp.sinya.refreshlibrary.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 配给应用界面使用的下拉刷新LinearLayout
 * @author KoizumiSinya
 * @date 2016/04/16.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {

    private View mTarget;

    private boolean isCanLoad;
    private boolean isCanRefresh = true;

    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public void setIsCanRefresh(boolean isCanRefresh) {
        this.isCanRefresh = isCanRefresh;
    }

    @Override
    public boolean canPullDown() {
        return isCanRefresh;
    }

    @Override
    public boolean canPullUp() {
        return isCanLoad;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureTarget();

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        mTarget.layout(left, top + mTarget.getTop(), left + width - right, top + height - bottom + mTarget.getTop());
    }

    private void ensureTarget() {
        if (mTarget != null) {
            return;
        }
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != null) {
                    mTarget = child;
                }
            }
        }
    }
}
