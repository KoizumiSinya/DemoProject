package jp.sinya.eveenttouch2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Type1Activity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    public static String TAG = "Sinya";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type1);

        MyTextView myTextView = (MyTextView) findViewById(R.id.activity_type1_tv);
        myTextView.setOnClickListener(this);
        myTextView.setOnTouchListener(this);
    }

    /**
     * MyTextView 的点击监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_type1_tv:
                Log.e(TAG, "Type1Activity MyTextView OnClickListener");
                break;
        }
    }

    /**
     * MyTextView 的触摸响应事件
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean flag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "Type1Activity MyTextView - OnTouchListener - ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "Type1Activity MyTextView - OnTouchListener - ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, "Type1Activity MyTextView - OnTouchListener - ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "Type1Activity MyTextView - OnTouchListener - ACTION_CANCEL");
                break;

            default:
                break;
        }

        flag = false;//默认就是false
        Log.e(TAG, "Type1Activity MyTextView - OnTouchListener - return " + flag);
        return flag;
    }

    /**
     * Type1Activity 自身作为界面所拥有的 dispatchTouchEvent 事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean flag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "Type1Activity dispatchTouchEvent - ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "Type1Activity dispatchTouchEvent - ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, "Type1Activity dispatchTouchEvent - ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "Type1Activity dispatchTouchEvent - ACTION_CANCEL");
                break;

            default:
                break;
        }

        flag = super.dispatchTouchEvent(event);
        Log.e(TAG, "Type1Activity dispatchTouchEvent - return " + flag);
        return flag;
    }

    /**
     * ype1Activity 自身作为界面所拥有的 onTouchEvent 事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "Type1Activity onTouchEvent - ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "Type1Activity onTouchEvent - ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, "Type1Activity onTouchEvent - ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "Type1Activity onTouchEvent - ACTION_CANCEL");
                break;

            default:
                break;
        }

        flag = super.onTouchEvent(event);
        Log.e(TAG, "Type1Activity onTouchEvent - return " + flag);
        return flag;
    }
}
