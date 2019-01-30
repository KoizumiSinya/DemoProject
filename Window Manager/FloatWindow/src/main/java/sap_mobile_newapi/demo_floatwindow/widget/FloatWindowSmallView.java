package sap_mobile_newapi.demo_floatwindow.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import sap_mobile_newapi.demo_floatwindow.R;
import sap_mobile_newapi.demo_floatwindow.manager.MyWindowManager;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/13 9:59
 * editor：
 * updateDate：2015/10/13 9:59
 */
public class FloatWindowSmallView extends LinearLayout {

    /**
     * 小悬浮窗口的宽度
     */
    public static int viewWidth;

    /**
     * 小悬浮窗口的高度
     */
    public static int viewHeight;

    /**
     * 系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 管理小悬浮窗口的位置
     */
    private WindowManager windowManager;

    /**
     * 当前手指触摸在屏幕上的X坐标
     */
    private float xInScreen;

    /**
     * 当前手指触摸在屏幕上的Y坐标
     */
    private float yInScreen;

    /**
     * 手指按下时在屏幕上的X坐标
     */
    private float xDownInScreen;

    /**
     * 手指按下时在屏幕上的Y坐标
     */
    private float yDownInScreen;

    /**
     * 手指按下时在小悬浮窗的View上的X坐标
     */
    private float xInView;

    /**
     * 手指按下时在小悬浮窗的View上的Y坐标
     */
    private float yInView;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    public FloatWindowSmallView(Context context) {
        super(context);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);

        View view = findViewById(R.id.small_window_layout);
        //获取小悬浮窗的宽和高
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        TextView percentText = (TextView) findViewById(R.id.percent);

        //显示百分比
        percentText.setText(MyWindowManager.getUsedPercentValue(context));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();

                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();

                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();

                break;

            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();

                //伴随着手指移动 更新小悬浮窗的位置
                updateViewPosition();
                break;

            case MotionEvent.ACTION_UP:
                //if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                if (Math.abs(xDownInScreen - xInScreen) < 5 && Math.abs(yDownInScreen - yInScreen) < 5) {
                    openBigWindow();
                }
                break;
        }
        return true;
    }

    public void setParams(WindowManager.LayoutParams params) {
        this.mParams = params;
    }

    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());
    }

    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);

        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 使用反射的原理 获取状态栏的高度值
     *
     * @return
     */
    public int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object obj = clazz.newInstance();

                Field field = clazz.getField("status_bar_height");
                int x = (int) field.get(obj);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

}
