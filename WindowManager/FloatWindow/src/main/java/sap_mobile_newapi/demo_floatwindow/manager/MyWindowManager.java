package sap_mobile_newapi.demo_floatwindow.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sap_mobile_newapi.demo_floatwindow.R;
import sap_mobile_newapi.demo_floatwindow.widget.FloatWindowBigView;
import sap_mobile_newapi.demo_floatwindow.widget.FloatWindowSmallView;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/13 9:59
 * editor：
 * updateDate：2015/10/13 9:59
 */
public class MyWindowManager {

    /**
     * 小悬浮窗的对象
     */
    private static FloatWindowSmallView smallView;

    /**
     * 大悬浮窗的对象
     */
    private static FloatWindowBigView bigView;

    /**
     * 小悬浮窗的View 参数
     */
    private static WindowManager.LayoutParams smallWindowParams;

    /**
     * 大悬浮窗的View 参数
     */
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * Window控制器
     */
    private static WindowManager mWindowManager;

    /**
     * 活动管理器 可以获取手机使用内存
     */
    private static ActivityManager mActivityManager;

    /**
     * 创建一个小悬浮窗
     *
     * @param context
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if (smallView == null) {
            smallView = new FloatWindowSmallView(context);

            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGB_888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }

            smallView.setParams(smallWindowParams);
            windowManager.addView(smallView, smallWindowParams);

        }
    }

    public static void createBigWindow(Context context) {
        WindowManager w = getWindowManager(context);
        int screenWidth = w.getDefaultDisplay().getWidth();
        int screenHeight = w.getDefaultDisplay().getHeight();

        if (bigView == null) {
            bigView = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;

                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGB_888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;

            }

            w.addView(bigView, bigWindowParams);
        }
    }

    /**
     * 移除大悬浮窗
     *
     * @param context
     */
    public static void removeBigWindow(Context context) {
        if (bigView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigView);
            bigView = null;
        }
    }

    /**
     * 移除小悬浮窗
     * @param context
     */
    public static void removeSmallWindow(Context context) {
        if (smallView != null) {
            WindowManager w = getWindowManager(context);
            w.removeView(smallView);
            smallView = null;
        }
    }

    /**
     * 更新小悬浮窗上的text 百分比文本数据
     *
     * @param context
     */
    public static void updateUsedPercent(Context context) {
        if (smallView != null) {
            TextView percentView = (TextView) smallView.findViewById(R.id.percent);
            percentView.setText(getUsedPercentValue(context));
        }
    }

    /**
     * 判断当前是否有悬浮窗在显示
     * @return
     */
    public static boolean isWindowShowing() {
        return smallView != null || bigView != null;
    }

    /**
     * 计算手机内存使用百分比
     *
     * @param context
     * @return
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";

        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();

            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "悬浮窗";

    }

    /**
     * 获取当前手机中可用内容
     *
     * @param context
     * @return 返回数据以字节为单位
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
