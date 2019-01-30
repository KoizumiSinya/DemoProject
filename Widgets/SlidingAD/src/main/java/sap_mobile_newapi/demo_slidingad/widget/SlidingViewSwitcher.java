package sap_mobile_newapi.demo_slidingad.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sap_mobile_newapi.demo_slidingad.R;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/14 17:09
 * editor：
 * updateDate：2015/10/14 17:09
 */
public class SlidingViewSwitcher extends RelativeLayout implements View.OnTouchListener {

    /**
     * 让菜单滚动 手指滑动需要达到的速率
     */
    private static final int SNAP_VELOCITY = 200;

    /**
     * 图片滑动容器的宽度
     */
    private int switcherViewWidth;

    /**
     * 当前显示的下标
     */
    private int currentItemIndex;

    /**
     * 图片滑动容器的元素总数
     */
    private int itemsCount;

    /*;
     * 各个元素的偏移边界值
     */
    private int[] borders;

    /**
     * 向左滑动的最大边缘值（由容器中包含的元素总数决定，并且marginLeft到达此值之后，不能再减少）
     */
    private int leftEdge = 0;

    /**
     * 向右滑动的最大边缘值（恒定为0，marginLeft到达此值之后，不能再增加）
     */
    private int rightEdge = 0;

    private float xDown, xMove, xUp;

    /**
     * 容器布局
     */
    private LinearLayout itemsLayout;

    /**
     * 指示下标布局
     */
    private LinearLayout dotsLayout;

    /**
     * 容器中的第一个元素的布局参数
     * （用于改变leftMargin值，决定当前显示的是哪一个元素）
     */
    private MarginLayoutParams firstItemParams;

    /**
     * 容器中的第一个元素
     */
    private View firstItem;

    /**
     * 手指滑动速率
     */
    private VelocityTracker velocityTracker;


    public SlidingViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 滚动到下一个元素
     */
    public void scrollToNext() {
        new ScrollTask().execute(-20);
    }

    /**
     * 滚动到上一个元素
     */
    public void scrollToPrevious() {
        new ScrollTask().execute(20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            initItems();
            initDots();
        }
    }

    /**
     * 初始化容器里面的元素
     * ①每一个元素都添加监听事件
     * ②改变所有元素的宽度，与父元素的宽度一致
     */
    private void initItems() {

        //getWidth()拿到的是屏幕的宽度
        switcherViewWidth = getWidth();
        Log.i("Sinya", "switcherViewWidth:" + switcherViewWidth);

        itemsLayout = (LinearLayout) getChildAt(0);
        itemsCount = itemsLayout.getChildCount();
        borders = new int[itemsCount];

        for (int i = 0; i < itemsCount; i++) {
            borders[i] = -i * switcherViewWidth;
            Log.i("Sinya", " borders[" + i + "]" + borders[i]);

            View item = itemsLayout.getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) item.getLayoutParams();
            //设置容器里的每一个图片宽度都为屏幕宽度
            params.width = switcherViewWidth;
            item.setLayoutParams(params);
            item.setOnTouchListener(this);
        }

        leftEdge = borders[itemsCount - 1];
        Log.i("Sinya", "leftEdge:" + leftEdge);
        firstItem = itemsLayout.getChildAt(0);
        firstItemParams = (MarginLayoutParams) firstItem.getLayoutParams();
    }


    private void initDots() {
        dotsLayout = (LinearLayout) getChildAt(1);
        refreshDotsLayout();
    }

    /**
     * 刷新下标签布局
     * 每次currentItemIndex改变的同时都要刷新
     */
    private void refreshDotsLayout() {
        dotsLayout.removeAllViews();
        for (int i = 0; i < itemsCount; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT);
            layoutParams.weight = 1;

            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            ImageView imageView = new ImageView(getContext());

            if (i == currentItemIndex) {
                imageView.setBackgroundResource(R.mipmap.viewpager_indicator_cur);
            } else {
                imageView.setImageResource(R.mipmap.viewpager_indicator);
            }

            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            relativeLayout.addView(imageView, relativeParams);

            dotsLayout.addView(relativeLayout, layoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整左侧布局的leftMargin值，从而显示和隐藏左侧布局
                xMove = event.getRawX();
                int distanceX = (int) ((xMove - xDown) - (currentItemIndex * switcherViewWidth));

                firstItemParams.leftMargin = distanceX;
                if (beAbleToScroll()) {
                    firstItem.setLayoutParams(firstItemParams);
                }
                break;

            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();

                if (beAbleToScroll()) {

                    //手指右滑结束后（向上滚动）
                    if (wantScrollToPrevious()) {
                        if (shouldScrollToPrevious()) {
                            currentItemIndex--;
                            scrollToPrevious();
                            refreshDotsLayout();
                        } else {
                            scrollToNext();
                        }

                        //手指左滑结束后（向右滚动）
                    } else if (wantScrollToNext()) {
                        if (shouldScrollToNext()) {
                            currentItemIndex++;
                            scrollToNext();
                            refreshDotsLayout();
                        } else {
                            scrollToPrevious();
                        }
                    }
                }
                recycleVelocityTracker();
                break;
        }
        return false;
    }

    /**
     * 判断当前是否能滚动
     * 当滚动到容器的第一个和最后一个时，不能再滚动
     *
     * @return
     */
    private boolean beAbleToScroll() {
        //Log.i("Sinya", "firstItemParams.leftMargin:" + firstItemParams.leftMargin + "; rightEdge:" + rightEdge + "; leftEdge:" + leftEdge);
        return firstItemParams.leftMargin < rightEdge && firstItemParams.leftMargin > leftEdge;
    }

    /**
     * 判断手势意图是否是想滚动到上一个元素
     * 如果手指移动的距离是正数:true; 否则 false
     *
     * @return
     */
    private boolean wantScrollToPrevious() {
        //手指右划（向上滚动）
        return xUp - xDown > 0;
    }

    /**
     * 判断手势意图是否是想滚动到下一个元素
     * 如果手指移动的距离是负数：true；否则false
     *
     * @return
     */
    private boolean wantScrollToNext() {
        //手指左划（向下滚动）
        return xUp - xDown < 0;
    }

    /**
     * 判断是否符合向下滚动
     * ①如果手指一动的距离大于屏幕的1/2
     * ②手指移动的速率超过SNAP_VELOCITY
     *
     * @return
     */
    private boolean shouldScrollToNext() {
        return xDown - xUp > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否符合向上滚动
     * ①如果手指一动的距离大于屏幕的1/2
     * ②手指移动的速率超过SNAP_VELOCITY
     *
     * @return
     */
    private boolean shouldScrollToPrevious() {
        return xUp - xDown > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 容器滚动时，是否有穿越border
     *
     * @param leftMargin 第一个元素的左偏移值
     * @param speed      滚动速度
     * @return
     */
    private boolean isCrossBorder(int leftMargin, int speed) {
        for (int border : borders) {
            //速度>0 表示手指右划（容器元素向上一个滚动）
            if (speed > 0) {
                if (leftMargin >= border && leftMargin - speed < border) {
                    return true;
                }

                //速度<0 表示手指左划（容器元素向下一个滚动）
            } else {
                if (leftMargin <= border && leftMargin - speed > border) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 找到离当前leftMargin最近的border值
     *
     * @param leftMargin
     * @return
     */
    private int findClosestBorder(int leftMargin) {
        int absLeftMargin = Math.abs(leftMargin);
        int closestBorder = borders[0];
        int closestMargin = Math.abs(Math.abs(closestBorder) - absLeftMargin);

        for (int border : borders) {
            int margin = Math.abs(Math.abs(border) - absLeftMargin);
            if (margin < closestMargin) {
                closestBorder = border;
                closestMargin = margin;
            }
        }
        return closestBorder;
    }

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

    /**
     * 异步线程
     * 作用就是根据传入的正负数速率，来动态的改变leftMargin参数
     * 使得布局产生一种渐渐移动的回笼效果
     */
    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = firstItemParams.leftMargin;
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (isCrossBorder(leftMargin, speed[0])) {
                    leftMargin = findClosestBorder(leftMargin);
                    break;
                }
                publishProgress(leftMargin);
                sleep(10);
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            firstItemParams.leftMargin = leftMargin[0];
            firstItem.setLayoutParams(firstItemParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            firstItemParams.leftMargin = leftMargin;
            firstItem.setLayoutParams(firstItemParams);
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
