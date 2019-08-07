package sap_mobile_newapi.demo_floatwindow.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import sap_mobile_newapi.demo_floatwindow.R;
import sap_mobile_newapi.demo_floatwindow.manager.MyWindowManager;
import sap_mobile_newapi.demo_floatwindow.service.FloatWindowService;

/**
 * author : Sinya Koizumi
 * createDate ：2015/10/13 9:59
 * editor：
 * updateDate：2015/10/13 9:59
 */
public class FloatWindowBigView extends LinearLayout{

    /**'
     * 大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 大悬浮窗的高度
     */
    public static int viewHeight;

    public FloatWindowBigView(final Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);

        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

        Button close = (Button) findViewById(R.id.close);
        Button back = (Button) findViewById(R.id.back);

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击关闭时，移除所有悬浮窗，并且停止service
                Log.i("Sinya", "关闭");
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);

                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击返回时，移除大悬浮窗，重新显示小悬浮窗
                Log.i("Sinya", "返回");
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.createSmallWindow(context);
            }
        });
    }
}
