package com.example.len.toucheventexample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.len.toucheventexample.utils.UtilTool;

/**
 * @author Sinya
 * @date 2016/7/26 13:47
 */

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("Sinya", "MyLinearLayout dispatchTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.dispatchTouchEvent(ev);
//        flag = true;
//        flag = false;
        Log.i("Sinya", "MyLinearLayout dispatchTouchEvent return = " + flag);
        return flag;
        // return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w("Sinya", "MyLinearLayout onInterceptTouchEvent　" + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.onInterceptTouchEvent(ev);
//        flag = true;
        Log.w("Sinya", "MyLinearLayout onInterceptTouchEvent return = " + flag);
        return flag;
        //return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Sinya", "MyLinearLayout onTouchEvent " + UtilTool.getEventName(event));
        boolean flag;
        flag = super.onTouchEvent(event);
//        flag = true;
        Log.e("Sinya", "MyLinearLayout onTouchEvent return = " + flag);
        return flag;
        //return super.onTouchEvent(event);
    }
}
