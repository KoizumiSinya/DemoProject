package sap_mobile_newapi.demo_slidingmenudelete.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Author : Sinya Koizumi
 * Editor：
 * CreateDate：2015/10/15 15:55
 * UpdateDate：2015/10/15 15:55
 * UpdateDescription；
 *
 * @Version
 */
public class MySlidingMenu2 extends RelativeLayout implements View.OnTouchListener {

    private static final int SNAP_VELOCITY = 200;

    private int switchViewWidth;

    private int currentItemIndex;

    private int itemsCount;

    private int[] borders;

    private int leftEdge = 0;

    private int rightEdge = 0;

    private float xDown, xMove, xUp, yMove, yDown, yUp;

    private LinearLayout itemsLayout;

    private MarginLayoutParams contentParams;

    private View contentItem;

    private VelocityTracker velocityTracker;

    private boolean isOpenMenu;
    private boolean isSliding;

    private int touchSlop;

    public MySlidingMenu2(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            initMenu();
        }
    }

    private void initMenu() {
        switchViewWidth = getWidth();
        Log.i("Sinya", "switchViewWidth:" + switchViewWidth);

        itemsLayout = (LinearLayout) getChildAt(1);
        itemsCount = itemsLayout.getChildCount();
        borders = new int[itemsCount];

        borders[0] = 0;
        View contentView = itemsLayout.getChildAt(0);
        MarginLayoutParams layoutParams = (MarginLayoutParams) contentView.getLayoutParams();
        layoutParams.width = switchViewWidth;
        contentView.setLayoutParams(layoutParams);
        contentView.setOnTouchListener(this);

        View deleteView = itemsLayout.getChildAt(1);
        MarginLayoutParams deleteParams = (MarginLayoutParams) deleteView.getLayoutParams();
        deleteParams.width = dip2px(getContext(), 90);
        borders[1] = -deleteParams.width;
        deleteView.setLayoutParams(deleteParams);
        deleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });

        leftEdge = borders[1];
        Log.i("Sinya", "leftEdge:" + leftEdge);
        contentItem = contentView;
        contentParams = (MarginLayoutParams) contentItem.getLayoutParams();
    }

    public void onDelete() {
        Log.i("Sinya", "删除");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                Log.i("Sinya", "xDown:" + xDown);
                break;

            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
//                if (beAbleScroll()) {
//                    contentItem.setLayoutParams(contentParams);
//                } else {
//                    Log.i("Sinya", "Move无法继续划动");
//                }

                //xDown - xMove > 0 表示向左滑动； xDown - xMove < 0 表示向右滑动
                int moveDistanceX = (int) (xDown - xMove);
                Log.i("Sinya", "xDown - xMove = " + moveDistanceX);
                int distanceY = (int) (yMove - yDown);

                //向左侧滑 moveDistanceX此时是正数
                if (!isOpenMenu && moveDistanceX >= touchSlop && (isSliding || Math.abs(distanceY) <= touchSlop)) {
                    isSliding = true;

                    contentParams.leftMargin = -moveDistanceX;

                    //如果左则布局 向左滑动低于leftEdge 也只能维持在leftEdge，不能继续向左偏移了
                    if (contentParams.leftMargin < leftEdge) {
                        contentParams.leftMargin = leftEdge;
                    }

                    contentItem.setLayoutParams(contentParams);
                }

                //向右侧滑动 moveDistanceX此时是负数
                if (isOpenMenu && -moveDistanceX >= touchSlop) {
                    isSliding = true;

                    contentParams.leftMargin = rightEdge - moveDistanceX;
                    //如果左则布局 向右滑动大于了rightEdge，也只能维持在rightEdge
                    if (contentParams.leftMargin > rightEdge) {
                        contentParams.rightMargin = rightEdge;
                    }

                    contentItem.setLayoutParams(contentParams);
                }

                Log.i("Sinya", "手指划动是的contentParams.leftMargin：" + contentParams.leftMargin);
                break;

            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                int upDistanceX = (int) (xUp - xDown);

                if (isSliding) {

                    Log.i("Sinya", "产生了sliding效果");

                    if (wantOpenMenu()) {
                        Log.i("Sinya", "想要开启");
                        if (shouldOpenMenu()) {
                            Log.i("Sinya", "符合开启条件");
                            openMenu();
                        } else {
                            Log.i("Sinya", "不符合开启条件，让其关闭");
                            closeMenu();
                        }

                    } else if (wantCloseMenu()) {
                        Log.i("Sinya", "想要关闭");
                        if (shouldCloseMenu()) {
                            Log.i("Sinya", "符合关闭条件");
                            closeMenu();
                        } else {
                            Log.i("Sinya", "不符合关闭条件，让其开启");
                            openMenu();
                        }
                    }

                } else if (upDistanceX < touchSlop && isOpenMenu) {
                    closeMenu();
                }

                recycleVelocityTracker();

                break;

            default:
                break;
        }
        return true;
    }

    private void openMenu() {
        Log.i("Sinya", "执行打开menu线程");
        new ScrollTask().execute(-20);
    }

    private void closeMenu() {
        Log.i("Sinya", "执行关闭menu线程");
        new ScrollTask().execute(20);
    }

    private boolean shouldOpenMenu() {
        return xDown - xUp > dip2px(getContext(), 90) / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean shouldCloseMenu() {
        return xUp - xDown > dip2px(getContext(), 90) / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean wantCloseMenu() {
        return xUp - xDown > 0 && isOpenMenu;
    }

    private boolean wantOpenMenu() {
        return xUp - xDown < 0 && !isOpenMenu;
    }

    // [+] 手指速率

    /**
     * 创建手指滑动速率
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
     * 获取手指滑动速率
     *
     * @return
     */
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 回收
     */
    private void recycleVelocityTracker() {
        velocityTracker.recycle();
        velocityTracker = null;
    }

    // [-] 手指速率
    // [+] Task

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = contentParams.leftMargin;
            while (true) {
                leftMargin = leftMargin + speed[0];

                if (leftMargin < -dip2px(getContext(), 90)) {
                    leftMargin = -dip2px(getContext(), 90);
                    break;
                }
                if (leftMargin > 0) {
                    leftMargin = 0;
                    break;
                }
                publishProgress(leftMargin);
                sleep(10);
            }

            if (speed[0] > 0) {
                isOpenMenu = false;
            } else {
                isOpenMenu = true;
            }

            isSliding = false;
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            contentParams.leftMargin = leftMargin[0];
            contentItem.setLayoutParams(contentParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            contentParams.leftMargin = leftMargin;
            contentItem.setLayoutParams(contentParams);
            Log.i("Sinya", "滑动结束后-contentParams.leftMargin:" + contentParams.leftMargin);
        }
    }

    // [-]

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
