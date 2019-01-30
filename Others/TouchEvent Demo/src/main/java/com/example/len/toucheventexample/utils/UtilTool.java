package com.example.len.toucheventexample.utils;

import android.view.MotionEvent;

/**
 * @author dragon
 * @date 2016/7/26 16:14
 */
public class UtilTool {

    public static String getEventName(MotionEvent event) {
        String name = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                name = "__DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                name = "__MOVE";
                break;
            case MotionEvent.ACTION_UP:
                name = "__UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                name = "__CANCEL";
                break;
        }
        return name;
    }
}
