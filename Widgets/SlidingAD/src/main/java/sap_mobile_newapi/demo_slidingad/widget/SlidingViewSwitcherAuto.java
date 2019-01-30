package sap_mobile_newapi.demo_slidingad.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import sap_mobile_newapi.demo_slidingad.R;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/15 10:31
 * editor：
 * updateDate：2015/10/15 10:31
 * <p/>
 * 开发思路：
 * ①自定义一个容器，继承自RelativeLayout，并且自身附带触摸监听
 * ②需要重写onLayout方法，主要是为了重构布局的宽高params参数
 * ③创建需要的成员变量
 */
public class SlidingViewSwitcherAuto extends RelativeLayout implements View.OnTouchListener {

    /**
     * 手指划动的速率
     */
    private static final int SNAP_VELOCITY = 200;

    /**
     * 容器的宽度
     */
    private int switcherViewWidth;

    /**
     * 对应容器中第几个元素的下标
     */
    private int currentItemIndex;

    /**
     * 容器中的元素总数
     */
    private int itemsCount;

    /**
     * 存放各个元素的左偏移量
     */
    private int[] borders;

    /**
     * 容器可以向左移动的最大边缘值
     * （由容器的元素总数决定，且marginLeft到达此值之后，不能再减少。也就是手指右划不再起作用，元素不能继续左移）
     */
    private int leftEdge = 0;

    /**
     * 容器可以向右移动的最大边缘值
     * （恒定为0，marginLeft到达此值之后，不能再增加。也就是手指左划不再起作用，元素不能继续右移）
     */
    private int rightEdge = 0;

    /**
     * 记录触摸点的位置
     */
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
     * 容器的第一个元素的布局参数
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

    private Handler handler = new Handler();

    public SlidingViewSwitcherAuto(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingViewSwitcherAuto);
        boolean isAutoPlay = a.getBoolean(R.styleable.SlidingViewSwitcherAuto_auto_play, false);
        if (isAutoPlay) {
            startAutoPlay();
        }
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            initItems();
            initDots();
        }
    }

    private void initItems() {
        switcherViewWidth = getWidth();
        Log.i("Sinya", "switcherViewWidth:" + switcherViewWidth);

        itemsLayout = (LinearLayout) getChildAt(0);
        itemsCount = itemsLayout.getChildCount();

        borders = new int[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            borders[i] = -i * switcherViewWidth;
            Log.i("Sinya", "borders[" + i + "] = " + borders[i]);

            View item = itemsLayout.getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) item.getLayoutParams();
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

    private void refreshDotsLayout() {
        dotsLayout.removeAllViews();
        for (int i = 0; i < itemsCount; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;

            RelativeLayout indexLayout = new RelativeLayout(getContext());
            ImageView indexImage = new ImageView(getContext());

            if (i == currentItemIndex) {
                indexImage.setBackgroundResource(R.mipmap.viewpager_indicator_cur);
            } else {
                indexImage.setImageResource(R.mipmap.viewpager_indicator);
            }

            RelativeLayout.LayoutParams indexItemParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            indexItemParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            indexLayout.addView(indexImage, indexItemParams);

            dotsLayout.addView(indexLayout, layoutParams);
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

                    if (wantScrollToNext()) {
                        if (shouldScrollToNext()) {
                            currentItemIndex++;
                            refreshDotsLayout();
                            scrollToNext();
                        } else {
                            scrollToPrevious();
                        }

                    } else if (wantScrollToPrevious()) {
                        if (shouldScrollToPrevious()) {
                            currentItemIndex--;
                            refreshDotsLayout();
                            scrollToPrevious();
                        } else {
                            scrollToNext();
                        }
                    }

                }
                recycleVelocityTracker();
                break;

            default:

                break;
        }
        return false;

    }

    // [+] Methods

    public void scrollToNext() {
        new ScrollTask().execute(-20);
    }

    public void scrollToPrevious() {
        new ScrollTask().execute(20);
    }

    public void scrollToFirstItem() {
        new ScrollToFirstItemTask().execute(20 * itemsCount);
    }

    public void startAutoPlay() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentItemIndex == itemsCount - 1) {
                    currentItemIndex = 0;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollToFirstItem();
                            refreshDotsLayout();
                        }
                    });

                }else{
                    currentItemIndex++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollToNext();
                            refreshDotsLayout();
                        }
                    });
                }
            }
        }, 2000, 2000);
    }

    private boolean beAbleToScroll() {
        //手指划动的时候会传入leftMargin值，根据leftMargin是否符合范围来决定容器内的元素能否滚动
        return firstItemParams.leftMargin < rightEdge && firstItemParams.leftMargin > leftEdge;
    }

    private boolean wantScrollToNext() {
        return xUp - xDown < 0;
    }

    private boolean wantScrollToPrevious() {
        return xUp - xDown > 0;
    }

    private boolean shouldScrollToNext() {
        return xDown - xUp > switcherViewWidth / 2 || getSnapVelocity() > SNAP_VELOCITY;
    }

    private boolean shouldScrollToPrevious() {
        return xUp - xDown > switcherViewWidth / 2 || getSnapVelocity() > SNAP_VELOCITY;
    }

    private boolean isCrossBorder(int leftMargin, int speed) {
        for (int border : borders) {

            //手指左划，容器向下一个滚动
            if (speed > 0) {
                if (leftMargin >= border && leftMargin - speed < border) {
                    return true;
                }
            } else {
                if (leftMargin <= border && leftMargin - speed > border) {
                    return true;
                }
            }
        }
        return false;
    }

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
    // [-] Methods
    // [+] 手指速率计算

    private void createVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = velocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * @return
     */
    private int getSnapVelocity() {
        //设置为读取1000毫秒内移动的速率
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    private void recycleVelocityTracker() {
        velocityTracker.recycle();
        velocityTracker = null;
    }

    // [-] 手指速率计算
    // [+] 异步线程Task 操作滑动

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = firstItemParams.leftMargin;
            while (true) {
                //根据传入的速度值，递增或者递减 leftMargin。然后更新firstItemParams
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

    class ScrollToFirstItemTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = firstItemParams.leftMargin;
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin > 0) {
                    leftMargin = 0;
                    break;
                }

                publishProgress(leftMargin);
                sleep(20);
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
        } finally {

        }
    }

    // [-] 异步线程Task 操作滑动

}
