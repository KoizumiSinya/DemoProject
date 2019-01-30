package jp.sinya.refreshframework.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author KoizumiSinya
 * @date 2016/4/9.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {

    private boolean isCanLoad;
    private boolean isCanRefresh = true;


    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public void setIsCanRefresh(boolean isCanRefresh) {
        this.isCanRefresh = isCanRefresh;
    }

    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {

        if (!isCanRefresh) {
            return false;
        }

        if (getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        return isCanLoad;
    }

}
