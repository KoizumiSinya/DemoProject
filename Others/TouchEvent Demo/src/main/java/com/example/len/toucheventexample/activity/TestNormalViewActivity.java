package com.example.len.toucheventexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.len.toucheventexample.R;
import com.example.len.toucheventexample.utils.UtilTool;

/**
 * @author Sinya
 * @date 2016/7/26 13:51
 */
public class TestNormalViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("Sinya", "TestNormalViewActivity dispatchTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.dispatchTouchEvent(ev);
//        flag = true;
//        flag = false;
        Log.i("Sinya", "TestNormalViewActivity dispatchTouchEvent return = " + flag);
        return flag;
        //return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Sinya", "TestNormalViewActivity onTouchEvent " + UtilTool.getEventName(event));
        boolean flag;
        flag = super.onTouchEvent(event);
//        flag = true;
        Log.e("Sinya", "TestNormalViewActivity onTouchEvent return = " + flag);
        return flag;
        // return super.onTouchEvent(event);
    }

}
