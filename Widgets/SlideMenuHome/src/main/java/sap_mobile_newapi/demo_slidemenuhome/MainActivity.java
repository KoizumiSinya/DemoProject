package sap_mobile_newapi.demo_slidemenuhome;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static android.content.Context.WINDOW_SERVICE;


public class MainActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    /**
     * 滚动显示或隐藏 menu 时，手指滑动需要达到的速度
     */
    public static final int SNAP_VELOCITY = 1000;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * menu最多可以滑动到多少左边距（由menu布局的宽度来决定， marginLeft到达这个值之后，不能再减少）
     */
    private int leftEdge;

    /**
     * menu最多可以滑动到多少右边距（恒定值为0，到达0之后不能再增加）
     */
    private int rightEdge = 0;

    /**
     * menu完全显示时，留给content多少的宽度值可见
     */
    private int menuPadding = 300;

    private View content;

    private View menu;

    /**
     * menu布局参数，用来修改marginLeft的值
     */
    private LinearLayout.LayoutParams menuParams;

    private float xDown;

    private float xMove;

    private float xUp;

    private boolean isMenuVisible;

    /**
     * 用来计算手机滑动的速度
     */
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValues();
        content.setOnTouchListener(this);
        content.setOnClickListener(this);
    }

    private void initValues() {

        WindowManager w = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = w.getDefaultDisplay().getWidth();

        content = findViewById(R.id.content);
        menu = findViewById(R.id.menu);

        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        //menu的宽度值设为 屏幕的宽度 - menuPadding
        menuParams.width = screenWidth - menuPadding;
        //左边缘的值 设置为menu宽度的负数
        leftEdge = -menuParams.width;
        //menu的marginLeft设置为左边缘的值，这样初始化的时候menu就会因为向左偏移而不可见
        menuParams.leftMargin = leftEdge;
        //将content的宽度设置为屏幕的宽度
        content.getLayoutParams().width = screenWidth;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        createVelocityTracker(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录手指按下时的X坐标
                xDown = event.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);

                if (isMenuVisible) {
                    menuParams.leftMargin = distanceX;
                } else {
                    menuParams.leftMargin = leftEdge + distanceX;
                }

                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }

                //不停的在改变menu的显示宽度
                menu.setLayoutParams(menuParams);
                break;

            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();

                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else {
                        //scrollToMenu();
                    }
                }
                recycleVelocityTracker();
                break;
        }
        return false;
    }

    /**
     * 判断当前的手势意图，是不是想要显示menu
     * 如果手指一动的距离是正数，且当前menu是不可见的，则认为当前手势意图是想要显示menu
     *
     * @return
     */
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }

    /**
     * 判断当前手势意图，是不是想要显示content
     * 如果手指移动的距离是负数，且当前menu是可见，则认为当前手势意图是想要显示content
     *
     * @return
     */
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }

    /**
     * 判断是否应该把menu显示出来
     * ①如果手指移动的距离大于屏幕1/2，
     * ②或者手指移动的速率大于SNAP_VELOCITY
     * ③或者移动展开menu超过了menu 的1/3
     * 就判定为展开menu
     *
     * @return
     */
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth / 2 || xUp - xDown > menu.getLayoutParams().width / 3 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否有应该展开content
     * 如果手指移动的距离 大于menu的1/4
     * 或者手指移动的速率大于SNAP_VELOCITY，就判定为展开content
     *
     * @return
     */
    private boolean shouldScrollToContent() {
        return xDown - xUp + menuPadding > screenWidth / 3 * 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 将屏幕移动到menu界面，速率为30
     */
    private void scrollToMenu() {
        new ScrollTask().execute(30);
    }

    /**
     * 将屏幕移动到content界面，速率为-30
     */
    private void scrollToContent() {
        new ScrollTask().execute(-30);
    }

    /**
     * 创建VelocityTracker对象
     * 将触摸事件加入VelocityTracker中
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
     * 手指在content界面上滑动的速率
     * 设置为每秒钟移动了多少像素单位值
     *
     * @return
     */
    public int getScrollVelocity() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content:
                if (isMenuVisible) {
                    scrollToContent();
                }
                break;
        }
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int marginLeft = menuParams.leftMargin;
            while (true) {
                marginLeft = marginLeft + speed[0];
                if (marginLeft > rightEdge) {
                    marginLeft = rightEdge;
                    break;
                }

                if (marginLeft < leftEdge) {
                    marginLeft = leftEdge;
                    break;
                }

                publishProgress(marginLeft);
                sleep(20);
            }

            if (speed[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return marginLeft;
        }

        @Override
        protected void onProgressUpdate(Integer... marginLeft) {
            menuParams.leftMargin = marginLeft[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer marginLeft) {
            menuParams.leftMargin = marginLeft;
            menu.setLayoutParams(menuParams);
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
