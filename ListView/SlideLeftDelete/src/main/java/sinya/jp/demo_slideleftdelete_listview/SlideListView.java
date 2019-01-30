package sinya.jp.demo_slideleftdelete_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/10 17:25
 * editor：
 * updateDate：2015/9/10 17:25
 */
public class SlideListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private OnDeleteListener listener;
    private View deleteBtn;
    private ViewGroup itemLayout;
    private int selectedItem;
    private boolean isDeleteShown;

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    // [+] OnOnTouchListener

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown) {
            itemLayout.removeView(deleteBtn);
            deleteBtn = null;
            isDeleteShown = false;
            return false;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    // [-] OnOnTouchListener
    // [+] OnGestureListener

    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown) {
            selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {
            deleteBtn = LayoutInflater.from(getContext()).inflate(R.layout.delete_button, null);
            deleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(deleteBtn);
                    deleteBtn = null;
                    isDeleteShown = false;
                    listener.onDelete(selectedItem);
                }
            });

            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            itemLayout.addView(deleteBtn, params);
            isDeleteShown = true;

        }
        return false;
    }

    // [-] OnGestureListener

    public interface OnDeleteListener {
        void onDelete(int index);
    }
}
