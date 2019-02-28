package com.tgi.multiple_category_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.tgi.multiple_category_view.OnFilterCollapseListener;
import com.tgi.multiple_category_view.R;

/**
 * @author: Sinya
 * @date: 2019/02/27. 20:05
 * @editor:
 * @edit date:
 */
public class CollapseFilterContainer extends RelativeLayout {

    private OnFilterCollapseListener collapseListener;
    private float startX;
    private float startY;

    private CollapseFilterView collapseFilterView;
    private RelativeLayout rlBg;

    public void setContainerBackground(int bg) {
        rlBg.setBackgroundColor(bg);
    }

    public void setCollapseListener(OnFilterCollapseListener collapseListener) {
        this.collapseListener = collapseListener;
    }

    public CollapseFilterContainer(Context context) {
        this(context, null);
    }

    public CollapseFilterContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapseFilterContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.collapsed_container, this, true);

        init();
    }

    private void init() {
        rlBg = findViewById(R.id.relative_container);
        collapseFilterView = findViewById(R.id.collapsedFilter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isEmpty = collapseFilterView.getChildCount() == 0;
        boolean containsEvent = ev.getX() >= collapseFilterView.getX() && ev.getX() <= collapseFilterView.getX() + collapseFilterView.getMeasuredWidth();
        return isEmpty || !containsEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!collapseFilterView.isBusy() && Math.abs(startX - event.getX()) < 20 && event.getY() - startY > 20) {
                    if (collapseListener != null) {
                        collapseListener.expand();
                    }
                    startX = 0f;
                    startY = 0f;
                } else if (!collapseFilterView.isBusy() && Math.abs(startX - event.getX()) < 20 && event.getY() - startY < -20) {
                    if (collapseListener != null) {
                        collapseListener.collapse();
                    }
                    startX = 0f;
                    startY = 0f;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (collapseFilterView.isBusy() && isClick(startX, startY, event.getX(), event.getY())) {
                    if (collapseListener != null) {
                        collapseListener.toggle();
                    }
                    startX = 0;
                    startY = 0;
                }
                break;

            default:
                break;
        }
        return true;
    }

    private boolean isClick(float startX, float startY, float x, float y) {
        return Math.abs(x - startX) < 20 && Math.abs(y - startY) < 20;
    }
}
