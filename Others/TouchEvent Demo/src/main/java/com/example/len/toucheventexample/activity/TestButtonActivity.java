package com.example.len.toucheventexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.len.toucheventexample.R;
import com.example.len.toucheventexample.utils.UtilTool;


public class TestButtonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("Sinya", "TestButtonActivity dispatchTouchEvent " + UtilTool.getEventName(ev));
        boolean flag;
        flag = super.dispatchTouchEvent(ev);
        //flag = true;
        Log.i("Sinya", "TestButtonActivity dispatchTouchEvent return = " + flag);
        return flag;
        //return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Sinya", "TestButtonActivity onTouchEvent " + UtilTool.getEventName(event));
        boolean flag;
        flag = super.onTouchEvent(event);
        //flag = true;
        Log.e("Sinya", "TestButtonActivity onTouchEvent return = " + flag);
        return flag;
       // return super.onTouchEvent(event);
    }

}
