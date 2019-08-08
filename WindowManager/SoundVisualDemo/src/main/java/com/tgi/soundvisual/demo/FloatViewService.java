package com.tgi.soundvisual.demo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class FloatViewService extends Service {

    //定义浮动窗口布局
    private RelativeLayout layout;
    private VoiceVisualBar voiceVisualBar;
    private WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    private WindowManager mWindowManager;

    private final IBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    @SuppressWarnings("static-access")
    @SuppressLint("InflateParams")
    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();

        //通过getApplication获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        int LAYOUT_FLAG;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        //设置window type
        wmParams.type = LAYOUT_FLAG;
        //wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;

        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;

        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.BOTTOM;

        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        //wmParams.x = 0;
        //wmParams.y = 152;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        layout = (RelativeLayout) inflater.inflate(R.layout.layout_float, null);
        voiceVisualBar = layout.findViewById(R.id.layout_float_voice_bar);
        //添加mFloatLayout
        mWindowManager.addView(layout, wmParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (layout != null) {
            voiceVisualBar.onStop();
            mWindowManager.removeView(layout);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        FloatViewService getService() {
            return FloatViewService.this;
        }
    }

    public void onStartListeningStart() {
        voiceVisualBar.setType(VoiceVisualBar.TYPE_LISTENING_START);
    }

    public void onStartListening() {
        voiceVisualBar.setType(VoiceVisualBar.TYPE_LISTENING);
    }


}