package sap_mobile_newapi.demo_slidingmenudelete.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/14 11:32
 * editor：
 * updateDate：2015/10/14 11:32
 */
public class MySlidingMenu extends RelativeLayout implements View.OnTouchListener {

    public static final int SNAP_VELOCITY = 200;

    private int screenWidth;

    /**
     * 布局最多可以向左滑动到多少
     */
    private int leftEdge = 0;

    /**
     * 布局最多可以向右滑动到多少
     */
    private int rightEdge = 0;

    private int touchSlop;

    private float xDown;
    private float yDown;
    private float xMove;
    private float yMove;
    private float xUp;

    private boolean isRightLayoutVisible;
    private boolean isSliding;

    private View leftLayout;
    private View rightLayout;
    private View mBindView;

    private MarginLayoutParams leftLayoutParams;
    private MarginLayoutParams rightLayoutParams;

    private VelocityTracker velocityTracker;

    public MySlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = w.getDefaultDisplay().getWidth();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.i("Sinya", "屏幕宽度:" + screenWidth + "; touchSlop:" + touchSlop);
    }

    public void setScrollEvent(View bindView) {
        mBindView = bindView;
        mBindView.setOnTouchListener(this);
    }

    public boolean isRightLayoutVisible() {
        return isRightLayoutVisible;
    }

    /**
     * 向左滑动，展开menu
     */
    public void scrollToLeftLayout() {
        new ScrollTask().execute(-30);
    }

    /**
     * 向右滑动，关闭menu
     */
    public void scrollToRightLayout() {
        new ScrollTask().execute(30);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            leftLayout = getChildAt(1);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();

            rightLayout = getChildAt(2);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();

            //让左侧布局向左偏移的值
            leftEdge = -rightLayoutParams.width;

            //设置左则布局的宽度为屏幕宽度
            leftLayoutParams.width = screenWidth;
            leftLayout.setLayoutParams(leftLayoutParams);

            Log.i("Sinya", "左侧布局的宽高：" + leftLayoutParams.width + "; " + leftLayoutParams.height);
            Log.i("Sinya", "右侧布局的宽高：" + rightLayoutParams.width + "; " + rightLayoutParams.height);
            Log.i("Sinya", "leftEdge:" + leftEdge + "; rightEdge:" + rightEdge);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);

        if (rightLayout.getVisibility() != View.VISIBLE) {
            rightLayout.setVisibility(View.VISIBLE);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Sinya", "点击");
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("Sinya", "滑动");
                xMove = event.getRawX();
                yMove = event.getRawY();

                //xDown - xMove > 0 表示向左滑动； xDown - xMove < 0 表示向右滑动
                int moveDistanceX = (int) (xDown - xMove);
                Log.i("Sinya", "xDown - xMove = " + moveDistanceX);
                int distanceY = (int) (yMove - yDown);

                //向左侧滑 moveDistanceX此时是正数
                if (!isRightLayoutVisible && moveDistanceX >= touchSlop && (isSliding || Math.abs(distanceY) <= touchSlop)) {
                    isSliding = true;

                    leftLayoutParams.leftMargin = -moveDistanceX;

                    //如果左则布局 向左滑动低于leftEdge 也只能维持在leftEdge，不能继续向左偏移了
                    if (leftLayoutParams.leftMargin < leftEdge) {
                        leftLayoutParams.leftMargin = leftEdge;
                    }

                    leftLayout.setLayoutParams(leftLayoutParams);
                }

                //向右侧滑动 moveDistanceX此时是负数
                if (isRightLayoutVisible && -moveDistanceX >= touchSlop) {
                    isSliding = true;

                    leftLayoutParams.leftMargin = rightEdge - moveDistanceX;
                    //如果左则布局 向右滑动大于了rightEdge，也只能维持在rightEdge
                    if (leftLayoutParams.leftMargin > rightEdge) {
                        leftLayoutParams.rightMargin = rightEdge;
                    }

                    leftLayout.setLayoutParams(rightLayoutParams);
                }

                break;

            case MotionEvent.ACTION_UP:
                Log.i("Sinya", "手指离开");
                xUp = event.getRawX();
                int upDistanceX = (int) (xUp - xDown);

                if (isSliding) {

                    if (wantToShowLeftLayout()) {
                        if (shouldScrollToLeftLayout()) {
                            Log.i("Sinya", "手指意图是向左滑动，并执行向左滑动，展开右侧布局");
                            scrollToLeftLayout();
                        } else {
                            scrollToRightLayout();
                        }

                    } else if (wantToShowRightLayout()) {
                        if (shouldScrollToRightLayout()) {
                            Log.i("Sinya", "手指意图是向右滑动，并执行向右滑动，关闭右侧布局");
                            scrollToRightLayout();
                        } else {
                            scrollToLeftLayout();
                        }
                    }

                } else if (upDistanceX < touchSlop && isRightLayoutVisible) {
                    scrollToRightLayout();
                }

                recyleVelocityTracker();
                break;
        }


        return true;
    }

    /**
     * 手指的滑动意图是想要向左滑动 打开右侧布局
     *
     * @return
     */
    private boolean wantToShowLeftLayout() {
        return xUp - xDown < 0 && !isRightLayoutVisible;
    }

    /**
     * 手指的滑动意图是想要向右滑动 关闭右侧布局
     *
     * @return
     */
    private boolean wantToShowRightLayout() {
        return xUp - xDown > 0 && isRightLayoutVisible;
    }

    /**
     * 判断是否符合向左滑动的条件 打开右侧布局
     *
     * @return
     */
    private boolean shouldScrollToLeftLayout() {
        return xDown - xUp > rightLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否符合向右滑动的条件 关闭右侧布局
     *
     * @return
     */
    private boolean shouldScrollToRightLayout() {
        return xUp - xDown > rightLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private void unFocusBindView() {
        if (mBindView != null) {
            mBindView.setPressed(false);
            mBindView.setFocusable(false);
            mBindView.setFocusableInTouchMode(false);
        }
    }

    private void createVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

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

            int leftMargin = leftLayoutParams.leftMargin;

            //根据传入的速度来滚动界面，当滚动到达左边界 或 右边界时，自动跳出循环

            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }

                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }

                publishProgress(leftMargin);
                sleep(15);
            }

            if (speed[0] > 0) {
                isRightLayoutVisible = false;
            } else {
                isRightLayoutVisible = true;
            }

            isSliding = false;
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            leftLayoutParams.leftMargin = leftMargin[0];
            leftLayout.setLayoutParams(leftLayoutParams);
            unFocusBindView();
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            leftLayoutParams.leftMargin = leftMargin;
            leftLayout.setLayoutParams(leftLayoutParams);
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
