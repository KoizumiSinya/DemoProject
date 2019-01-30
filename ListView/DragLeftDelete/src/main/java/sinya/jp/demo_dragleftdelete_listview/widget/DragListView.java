package sinya.jp.demo_dragleftdelete_listview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/23 11:32
 * editor：
 * updateDate：2015/9/23 11:32
 */
public class DragListView extends ListView {

    private Context context;
    private boolean moveable = false;
    private boolean isClose = true;

    private float mDownX, mDownY;

    private int mTouchPosition, mOldPosition = -1;

    private DragListViewItem mTouchView, mOldView;

    public DragListView(Context context) {
        super(context);
        init(context);
    }

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //获取手指点击下的listview的position
                mTouchPosition = pointToPosition((int)ev.getX(), (int)ev.getY());

                //获取点击的View （其实就是手机屏幕中可见的View中，对应的手指触摸的View）
                mTouchView = (DragListViewItem) getChildAt(mTouchPosition - getFirstVisiblePosition());
                mDownX = ev.getX();
                mDownY = ev.getY();

                //如果点击的是同一个位置， 或者listview所有item为关闭状态
                if (mOldPosition == mTouchPosition || isClose) {
                    moveable = true;
                    mTouchView.mDownX = (int)mDownX;
                }else{
                    moveable = false;
                    if (mOldView != null) {
                        //打开的oldview归位
                        mOldView.smoothCloseMenu();
                    }
                }

                mOldPosition = mTouchPosition;
                mOldView = mTouchView;
                break;

            case MotionEvent.ACTION_MOVE:

                //判断手指X Y方向的滑动 是否归属侧滑
                if(Math.abs(mDownX -ev.getX()) < Math.abs(mDownY - ev.getY() * dp2px(2))){
                    break;
                }

                if (moveable) {
                    int dis = (int)(mTouchView.mDownX - ev.getX());

                    if (mTouchView.state == mTouchView.STATE_OPEN) {
                        dis += mTouchView.mMenuView.getWidth();
                    }
                    mTouchView.swipe(dis);
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (moveable) {
                    if ((mTouchView.mDownX - ev.getX()) > (mTouchView.mMenuView.getWidth() / 2)) {
                        //open
                        mTouchView.smoothOpenMenu();
                        isClose = false;
                    }else{
                        mTouchView.smoothCloseMenu();
                        isClose = true;
                    }

                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}
