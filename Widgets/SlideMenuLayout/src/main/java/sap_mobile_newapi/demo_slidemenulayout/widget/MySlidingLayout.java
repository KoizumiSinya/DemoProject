package sap_mobile_newapi.demo_slidemenulayout.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.BitSet;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/13 18:05
 * editor：
 * updateDate：2015/10/13 18:05
 */
public class MySlidingLayout extends RelativeLayout implements View.OnTouchListener {

    /**
     * 滚动显示和隐藏左侧布局时 手指滑动的速率
     */
    public static final int SNAP_VELOCITY = 200;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 布局最多可以向右滑动到的左边缘
     */
    private int leftEdge = 0;

    /**
     * 布局最多可以滑动到的右边缘
     */
    private int rightEdge = 0;

    /**
     * 在判定为滚动之前 手指可以移动的最大值
     */
    private int touchSlop;

    private float xDown;
    private float yDown;
    private float xMove;
    private float yMove;
    private float xUp;

    /**
     * 左侧布局现实状况
     * 只有在完全显示或隐藏的时候 才会更改这个boolean值
     * 滑动过程中无效
     */
    private boolean isLeftLayoutVisible;

    private boolean isSliding;

    private View leftLayout;

    private View rightLayout;

    private View mBindView;

    /**
     * 左侧布局的参数 通过修改这个参数来重新确定左侧布局的宽度、更改leftMargin值
     */
    private MarginLayoutParams leftLayoutParams;

    private MarginLayoutParams rightLayoutParams;

    /**
     * 计算手指移动速率
     */
    private VelocityTracker velocityTracker;

    public MySlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = w.getDefaultDisplay().getWidth();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setScrollEvent(View bindView) {
        mBindView = bindView;
        mBindView.setOnTouchListener(this);
    }

    public boolean isLeftLayoutVisible() {
        return isLeftLayoutVisible;
    }

    public void scrollToLeftLayout() {
        new ScrollTask().execute(-30);
    }

    public void scrollToRightLayout() {
        new ScrollTask().execute(30);
    }

    /**
     * 重新设定左侧布局和右侧布局的参数
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            //获取左侧布局的对象
            leftLayout = getChildAt(0);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();

            //让左侧布局向左偏移进去
            rightEdge = -leftLayoutParams.width;

            //获取右侧布局的对象
            rightLayout = getChildAt(1);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();

            //设置右侧布局的宽度是屏幕宽度
            rightLayoutParams.width = screenWidth;
            rightLayout.setLayoutParams(rightLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);

        if (leftLayout.getVisibility() != View.VISIBLE) {
            leftLayout.setVisibility(View.VISIBLE);
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();

                int moveDistanceX = (int) (xMove - xDown);
                int ditanceY = (int) (yMove - yDown);

                //如果左侧布局没有展开 且 手指滑动的距离大于可移动值 且 是滑动状态或者 手指的Y轴移动小于可移动值
                if (!isLeftLayoutVisible && moveDistanceX >= touchSlop && (isSliding || Math.abs(ditanceY) <= touchSlop)) {
                    isSliding = true;
                    rightLayoutParams.rightMargin = -moveDistanceX;

                    if (rightLayoutParams.rightMargin > leftEdge) {
                        rightLayoutParams.rightMargin = leftEdge;
                    }

                    rightLayout.setLayoutParams(rightLayoutParams);
                }

                if (isLeftLayoutVisible && -moveDistanceX >= touchSlop) {
                    isSliding = true;
                    rightLayoutParams.rightMargin = rightEdge - moveDistanceX;

                    if (rightLayoutParams.rightMargin < rightEdge) {
                        rightLayoutParams.rightMargin = rightEdge;
                    }

                    rightLayout.setLayoutParams(rightLayoutParams);
                }
                break;

            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                int upDistanceX = (int) (xUp - xDown);

                if (isSliding) {

                    if (wantToShowLeftLayout()) {
                        if (shouldScrollToLeftLayout()) {
                            scrollToLeftLayout();
                        } else {
                            scrollToRightLayout();
                        }
                    } else if (wantToShowRightLayout()) {
                        if (shouldScrollToRightLayout()) {
                            scrollToRightLayout();
                        } else {
                            scrollToLeftLayout();
                        }
                    }

                } else if (upDistanceX < touchSlop && isLeftLayoutVisible) {
                    scrollToRightLayout();
                }

                recyleVelocityTracker();
                break;
        }

        if (v.isEnabled()) {
            if (isSliding) {
                unFocusBindView();
                return true;
            }
            if (isLeftLayoutVisible) {
                return true;
            }
            return false;
        }
        return true;
    }



    /**
     * 使用可以获得焦点的控件， 在滑动的时候失去焦点
     */
    private void unFocusBindView() {
        if (mBindView != null) {
            mBindView.setPressed(false);
            mBindView.setFocusable(false);
            mBindView.setFocusableInTouchMode(false);
        }
    }

    /**
     * 判断手势意图是不是要打开左侧布局
     *
     * @return
     */
    private boolean wantToShowLeftLayout() {
        return xUp - xDown > 0 && !isLeftLayoutVisible;
    }

    /**
     * 判断手势意图是不是要打开右侧布局
     *
     * @return
     */
    private boolean wantToShowRightLayout() {
        return xUp - xDown < 0 && isLeftLayoutVisible;
    }

    /**
     * 判定展开左侧布局的条件
     *
     * @return
     */
    private boolean shouldScrollToLeftLayout() {
        return xUp - xDown > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断展开右侧布局的条件
     *
     * @return
     */
    private boolean shouldScrollToRightLayout() {
        return xDown - xUp > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }


    /**
     * 判断手指触摸速率
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 回收
     */
    private void recyleVelocityTracker() {
        velocityTracker.recycle();
        velocityTracker = null;
    }

    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int rightMargin = rightLayoutParams.rightMargin;
            //根据传入的速度来滚动界面，当滚动到达左边界 或 右边界时，自动跳出循环
            while (true) {
                rightMargin = rightMargin + speed[0];
                if (rightMargin < rightEdge) {
                    rightMargin = rightEdge;
                    break;
                }

                if (rightMargin > leftEdge) {
                    rightMargin = leftEdge;
                    break;
                }

                publishProgress(rightMargin);
                sleep(15);
            }

            if (speed[0] > 0) {
                isLeftLayoutVisible = false;
            } else {
                isLeftLayoutVisible = true;
            }
            isSliding = false;
            return rightMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... rightMargin) {
            rightLayoutParams.rightMargin = rightMargin[0];
            rightLayout.setLayoutParams(rightLayoutParams);
            unFocusBindView();
        }

        @Override
        protected void onPostExecute(Integer rightMargin) {
            rightLayoutParams.rightMargin = rightMargin;
            rightLayout.setLayoutParams(rightLayoutParams);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
