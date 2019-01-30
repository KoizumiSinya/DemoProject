package com.example.len.toucheventexample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.example.len.toucheventexample.utils.UtilTool;

/**
 * Created by Jalen on 2016/7/26.
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("Sinya", "MyScrollView dispatchTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.dispatchTouchEvent(ev);
        //flag = true;
        Log.i("Sinya", "MyScrollView dispatchTouchEvent return = " + flag);
        return flag;
       // return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w("Sinya", "MyScrollView onInterceptTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.onInterceptTouchEvent(ev);
        //flag = true;
        Log.w("Sinya", "MyScrollView onInterceptTouchEvent return = " + flag);
        return flag;
        //return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("Sinya", "MyScrollView onTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.onTouchEvent(ev);
        // flag = true;
        Log.e("Sinya", "MyScrollView onTouchEvent return = " + flag);
        return flag;
       // return super.onTouchEvent(ev);
    }
}
