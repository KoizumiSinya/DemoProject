package sinya.jp.demo_dragleftdelete_listview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/23 11:34
 * editor：
 * updateDate：2015/9/23 11:34
 */
public class DragListViewItem extends LinearLayout {

    public static final int STATE_CLOSE = 0;
    public static final int STATE_OPEN = 1;

    private Context context;

    public View mTitleView;
    public View mContentView;
    public View mMenuView;

    public int mDownX;
    public int state = STATE_CLOSE;
    public boolean isFing;
    private int mBaseX;
    private Scroller scroller;

    public DragListViewItem(View mTitleView, View mContentView, View mMenuView) {

        super(mContentView.getContext());
        this.mTitleView = mTitleView;
        this.context = mContentView.getContext();
        this.scroller = new Scroller(getContext());
        this.mContentView = mContentView;
        this.mMenuView = mMenuView;

        init();
    }

    public DragListViewItem(Context context) {
        super(context);
    }

    public DragListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        LayoutParams contentParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams menuParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mContentView.setLayoutParams(contentParams);
        mMenuView.setLayoutParams(menuParams);

        addView(mContentView);
        addView(mMenuView);
    }

    private void init2() {

        setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        setOrientation(VERTICAL);

//        LinearLayout contentLayout = new LinearLayout(context);
//        contentLayout.setOrientation(HORIZONTAL);

//        LayoutParams contentParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        LayoutParams menuParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

//        mContentView.setLayoutParams(contentParams);
//        mMenuView.setLayoutParams(menuParams);
//        contentLayout.addView(mContentView);
//        contentLayout.addView(mMenuView);

        addView(mContentView);
        addView(mMenuView);
        addView(mTitleView);
    }

    public void swipe(int dis) {
        if (dis > mMenuView.getWidth()) {
            dis = mMenuView.getWidth();
        }

        if (dis < 0) {
            dis = 0;
        }

        mContentView.layout(-dis, mContentView.getTop(), mContentView.getWidth() - dis, getMeasuredHeight());
        mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(), mContentView.getWidth() + mMenuView.getWidth() - dis, mMenuView.getBottom());
    }

    @Override
    public void computeScroll() {
        if (state == STATE_OPEN) {
            if (scroller.computeScrollOffset()) {
                swipe(scroller.getCurrX());
                postInvalidate();
            }

        } else {
            if (scroller.computeScrollOffset()) {
                swipe(mBaseX - scroller.getCurrX());
                postInvalidate();
            }
        }
    }

    public void smoothCloseMenu() {
        state = STATE_CLOSE;
        mBaseX = -mContentView.getLeft();
        scroller.startScroll(0, 0, mBaseX, 0, 350);
        postInvalidate();
    }

    public void smoothOpenMenu() {
        state = STATE_OPEN;
        scroller.startScroll(-mContentView.getLeft(), 0, mMenuView.getWidth() / 2, 0, 350);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mContentView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        mMenuView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, 0, getMeasuredWidth(), mContentView.getMeasuredHeight());
        mMenuView.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mMenuView.getMeasuredWidth(), mContentView.getMeasuredHeight());
    }
}
