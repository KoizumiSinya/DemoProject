package jp.sinya.test.touchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * @author KoizumiSinya
 * @date 2017/1/12. 13:33
 * @editor
 * @date
 * @describe
 */
public class TouchMoveView extends View {
    int lastX, lastY;

    public TouchMoveView(Context context) {
        super(context);
    }

    public TouchMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;

                int transX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int transY = (int) (ViewHelper.getTranslationY(this) + deltaY);

                ViewHelper.setTranslationX(this, transX);
                ViewHelper.setTranslationY(this, transY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        lastX = x;
        lastY = y;

        return true;
    }
}
