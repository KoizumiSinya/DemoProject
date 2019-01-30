package jp.sinya.eveenttouch2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author Koizumi Sinya
 * @date 2017/11/09. 20:25
 * @edithor
 * @date
 */
public class MyTextView extends TextView {
    public static String TAG = "Sinya";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        boolean flag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "MyTextView dispatchHoverEvent - ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MyTextView dispatchHoverEvent - ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "MyTextView dispatchHoverEvent - ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "MyTextView dispatchHoverEvent - ACTION_CANCEL");
                break;

            default:
                break;
        }

        flag = super.dispatchTouchEvent(event);
        Log.i(TAG, "MyTextView dispatchHoverEvent - return " + flag);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "MyTextView onTouchEvent - ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MyTextView onTouchEvent - ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "MyTextView onTouchEvent - ACTION_UP");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "MyTextView onTouchEvent - ACTION_CANCEL");
                break;

            default:
                break;
        }

        flag = super.onTouchEvent(event);
        Log.i(TAG, "MyTextView onTouchEvent - return " + flag);
        return flag;
    }
}
