package jp.sinya.stickyexpandlistviewdemo2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;

import jp.sinya.stickyexpandlistviewdemo2.R;

/**
 * @author KoizumiSinya
 * @date 2016/4/10.
 */
public class SwipeStickyExpandListView extends ExpandableListView implements OnScrollListener, GestureDetector.OnGestureListener, View.OnTouchListener {

    public interface OnHeaderUpdateListener {
        /**
         * 返回一个view对象即可
         * 注意：view必须要有LayoutParams
         */
        public View getPinnedHeader();

        public void updatePinnedHeader(View headerView, int firstVisibleGroupPos);

    }

    private Context context;

    private View mHeaderView;
    private int mHeaderWidth;
    private int mHeaderHeight;

    private View mTouchTarget;

    private OnScrollListener mScrollListener;
    private OnHeaderUpdateListener mHeaderUpdateListener;

    private boolean mActionDownHappened = false;
    protected boolean mIsHeaderGroupClickable = true;


    // [+] 侧滑删除部分 所需的成员变量

    private GestureDetector mDetector;

    private int standard_touch_target_size = 0;
    private float mLastMotionX;
    // 有item被拉出
    public boolean deleteView = false;
    // 当前拉出的view
    private ScrollLinerLayout mScrollLinerLayout = null;
    // 滑动着
    private boolean scroll = false;
    // 禁止拖动
    private boolean forbidScroll = false;
    // 禁止拖动
    private boolean clicksameone = false;
    // 当前拉出的位置
    private int position;
    // 消息冻结
    private boolean freeze = false;

    // [-] 侧滑删除部分 所需的成员变量

    public SwipeStickyExpandListView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SwipeStickyExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initView();
    }

    public SwipeStickyExpandListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    private void initView() {
        setFadingEdgeLength(0);
        setOnScrollListener(this);
        initSwipe();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (l != this) {
            mScrollListener = l;
        } else {
            mScrollListener = null;
        }
        super.setOnScrollListener(this);
    }

    /**
     * 给group添加点击事件监听
     *
     * @param onGroupClickListener   监听
     * @param isHeaderGroupClickable 表示header是否可点击<br/>
     *                               note : 当不想group可点击的时候，需要在OnGroupClickListener#onGroupClick中返回true，
     *                               并将isHeaderGroupClickable设为false即可
     */
    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener, boolean isHeaderGroupClickable) {
        mIsHeaderGroupClickable = isHeaderGroupClickable;
        super.setOnGroupClickListener(onGroupClickListener);
    }

    public void setOnHeaderUpdateListener(OnHeaderUpdateListener listener) {
        mHeaderUpdateListener = listener;
        if (listener == null) {
            mHeaderView = null;
            mHeaderWidth = mHeaderHeight = 0;
            return;
        }
        mHeaderView = listener.getPinnedHeader();
        int firstVisiblePos = getFirstVisiblePosition();
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        listener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
        requestLayout();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView == null) {
            return;
        }
        measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderView == null) {
            return;
        }
        int delta = mHeaderView.getTop();
        mHeaderView.layout(0, delta, mHeaderWidth, mHeaderHeight + delta);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderView != null) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int pos = pointToPosition(x, y);
        if (mHeaderView != null && y >= mHeaderView.getTop() && y <= mHeaderView.getBottom()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mTouchTarget = getTouchTarget(mHeaderView, x, y);
                mActionDownHappened = true;
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                View touchTarget = getTouchTarget(mHeaderView, x, y);
                if (touchTarget == mTouchTarget && mTouchTarget.isClickable()) {
                    mTouchTarget.performClick();
                    invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
                } else if (mIsHeaderGroupClickable) {
                    int groupPosition = getPackedPositionGroup(getExpandableListPosition(pos));
                    if (groupPosition != INVALID_POSITION && mActionDownHappened) {
                        if (isGroupExpanded(groupPosition)) {
                            collapseGroup(groupPosition);
                        } else {
                            expandGroup(groupPosition);
                        }
                    }
                }
                mActionDownHappened = false;
            }
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    private View getTouchTarget(View view, int x, int y) {
        if (!(view instanceof ViewGroup)) {
            return view;
        }

        ViewGroup parent = (ViewGroup) view;
        int childrenCount = parent.getChildCount();
        final boolean customOrder = isChildrenDrawingOrderEnabled();
        View target = null;
        for (int i = childrenCount - 1; i >= 0; i--) {
            final int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
            final View child = parent.getChildAt(childIndex);
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }
        if (target == null) {
            target = parent;
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view.isClickable() && y >= view.getTop() && y <= view.getBottom() && x >= view.getLeft() && x <= view.getRight()) {
            return true;
        }
        return false;
    }

    public void requestRefreshHeader() {
        refreshHeader();
        invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
    }

    protected void refreshHeader() {
        if (mHeaderView == null) {
            return;
        }
        int firstVisiblePos = getFirstVisiblePosition();
        int pos = firstVisiblePos + 1;
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        int group = getPackedPositionGroup(getExpandableListPosition(pos));

        if (group == firstVisibleGroupPos + 1) {
            View view = getChildAt(1);
            if (view == null) {
                return;
            }
            if (view.getTop() <= mHeaderHeight) {
                int delta = mHeaderHeight - view.getTop();
                mHeaderView.layout(0, -delta, mHeaderWidth, mHeaderHeight - delta);
            } else {
                //TODO : note it, when cause bug, remove it
                mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
            }
        } else {
            mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
        }

        if (mHeaderUpdateListener != null) {
            mHeaderUpdateListener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0) {
            refreshHeader();
        }
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }


    // [+] 侧滑删除相关方法

    private void initSwipe() {
        mDetector = new GestureDetector(context, this);
        // mDetector.setIsLongpressEnabled(false);
        standard_touch_target_size = (int) getResources().getDimension(R.dimen.leftlist_img_width);
        this.setOnTouchListener(this);
    }

    public void reset() {
        reset(false);
    }

    public void reset(boolean noaction) {
        position = -1;
        deleteView = false;
        if (mScrollLinerLayout != null) {
            if (!noaction) {
                mScrollLinerLayout.snapToScreen(0);
            } else {
                mScrollLinerLayout.scrollTo(0, 0);
            }
            mScrollLinerLayout = null;
        }
        scroll = false;
    }

    public boolean onDown(MotionEvent e) {
        mLastMotionX = e.getX();
        int p = this.pointToPosition((int) e.getX(), (int) e.getY()) - this.getFirstVisiblePosition();
        if (deleteView) {
            if (p != position) {
                // 吃掉，不在有消息
                freeze = true;
                return true;
            } else {
                clicksameone = true;
            }
        }
        position = p;
        scroll = false;
        return false;
    }

    public void onLongPress(MotionEvent e) {
        // Log.i(TAG, "onLongPress");
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Log.i(TAG, "onScroll" + e1.getX() + ":" + distanceX);
        // 第二次
        if (scroll) {
            int deltaX = (int) (mLastMotionX - e2.getX());
            if (deleteView) {
                deltaX += standard_touch_target_size;
            }
            if (deltaX >= 0 && deltaX <= standard_touch_target_size) {
                mScrollLinerLayout.scrollBy(deltaX - mScrollLinerLayout.getScrollX(), 0);
            }
            return true;
        }
        if (!forbidScroll) {
            forbidScroll = true;
            // x方向滑动，才开始拉动
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                View v = this.getChildAt(position);
                boolean ischild = v instanceof ScrollLinerLayout;
                if (ischild) {
                    mScrollLinerLayout = (ScrollLinerLayout) v;
                    scroll = true;
                    int deltaX = (int) (mLastMotionX - e2.getX());
                    if (deleteView) {
                        // 再次点击的时候，要把deltax增加
                        deltaX += standard_touch_target_size;
                    }
                    if (deltaX >= 0 && deltaX <= standard_touch_target_size) {
                        mScrollLinerLayout.scrollBy((int) (e1.getX() - e2.getX()), 0);
                    }
                }
            }
        }
        return false;
    }

    public void onShowPress(MotionEvent e) {
        // Log.i(TAG, "onShowPress");
    }

    public boolean onSingleTapUp(MotionEvent e) {
        // Log.i(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scroll || deleteView) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            boolean isfreeze = freeze;
            boolean isclicksameone = clicksameone;
            forbidScroll = false;
            clicksameone = false;
            freeze = false;
            if (isfreeze) {
                // 上一个跟当前点击不一致 还原
                reset();
                return true;
            }
            int deltaX2 = (int) (mLastMotionX - event.getX());
            // 不存在
            // Log.i(TAG, "scroll:" + scroll + "deltaX2:" + deltaX2);
            if (scroll && deltaX2 >= standard_touch_target_size / 2) {
                mScrollLinerLayout.snapToScreen(standard_touch_target_size);
                deleteView = true;
                scroll = false;
                return true;
            }
            if (deleteView && scroll && deltaX2 >= -standard_touch_target_size / 2) {
                mScrollLinerLayout.snapToScreen(standard_touch_target_size);
                deleteView = true;
                scroll = false;
                return true;
            }
            if (isclicksameone || scroll) {
                reset();
                return true;
            }
            reset();
        }
        if (freeze) {
            return true;
        }
        // Log.i(TAG, "onTouchEvent");
        return mDetector.onTouchEvent(event);

    }

    public void deleteItem() {
        reset(true);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    // [-] 侧滑删除相关方法


}