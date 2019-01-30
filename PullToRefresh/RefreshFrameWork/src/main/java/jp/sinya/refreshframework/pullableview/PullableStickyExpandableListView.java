package jp.sinya.refreshframework.pullableview;

import android.content.Context;
import android.util.AttributeSet;

import jp.sinya.refreshframework.stickyexpandlistview.StickyExpandableListView;

/**
 * @author KoizumiSinya
 * @date 2016/4/9.
 */
public class PullableStickyExpandableListView extends StickyExpandableListView implements Pullable {

    private boolean isCanLoad;
    private boolean isCanRefresh = true;

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public void setIsCanRefresh(boolean isCanRefresh) {
        this.isCanRefresh = isCanRefresh;
    }

    public PullableStickyExpandableListView(Context context) {
        super(context);
    }

    public PullableStickyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableStickyExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {

        if (!isCanRefresh) {
            return false;
        }

        if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (!isCanLoad) {
            return false;
        }

        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }
}
