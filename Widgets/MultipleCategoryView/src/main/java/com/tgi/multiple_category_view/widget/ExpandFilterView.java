package com.tgi.multiple_category_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tgi.multiple_category_view.Coord;
import com.tgi.multiple_category_view.OnFilterCollapseListener;

import java.util.LinkedHashMap;

/**
 * @author: Sinya
 * @date: 2019/02/27. 20:05
 * @editor:
 * @edit date:
 */
public class ExpandFilterView extends ViewGroup {

    private View preItem;
    private int preX;
    private int preY;
    private int preHeight;
    private float startX;
    private float startY;

    private int margin;
    private LinkedHashMap<FilterItemView, Coord> filters = new LinkedHashMap<>();

    private OnFilterCollapseListener onFilterCollapseListener;

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public LinkedHashMap<FilterItemView, Coord> getFilters() {
        return filters;
    }

    public void setOnFilterCollapseListener(OnFilterCollapseListener onFilterCollapseListener) {
        this.onFilterCollapseListener = onFilterCollapseListener;
    }

    public ExpandFilterView(Context context) {
        this(context, null);
    }

    public ExpandFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        margin = dp2px(getContext(), 6);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!filters.isEmpty()) {
            for (int i = 0; i < getChildCount() - 1; i++) {
                View child = getChildAt(i);
                Coord coord = filters.get(child);

                if (coord != null) {
                    child.layout(coord.getX(), coord.getY(), coord.getX() + child.getMeasuredWidth(), coord.getY() + child.getMeasuredHeight());
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(calculateSize(widthMeasureSpec, LayoutParams.MATCH_PARENT), calculateSize(heightMeasureSpec, calculateDesiredHeight()));
    }

    private boolean canPlaceOnTheSameLine(View item) {
        if (preItem != null) {
            int occupiedWidth = preX + preItem.getMeasuredWidth() + margin + item.getMeasuredWidth();
            return occupiedWidth <= getMeasuredWidth();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() - startY < -20) {
                    if (onFilterCollapseListener != null) {
                        onFilterCollapseListener.collapse();
                    }
                    startX = 0f;
                    startY = 0f;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private int calculateDesiredHeight() {
        int height = preHeight;
        if (filters.isEmpty()) {
            for (int i = 0; i < getChildCount() - 1; i++) {

                FilterItemView child = (FilterItemView) getChildAt(i);

                child.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                if (preItem == null) {
                    preX = margin;
                    preY = margin;
                    height = child.getMeasuredHeight() + margin;

                } else if (canPlaceOnTheSameLine(child)) {
                    preX = preX + preItem.getMeasuredWidth() + margin / 2;
                } else {
                    preX = margin;
                    preY = preY + preItem.getMeasuredHeight() + margin / 2;
                    height += child.getMeasuredHeight() + margin / 2;
                }

                preItem = child;

                if (filters.size() < getChildCount()) {
                    filters.put(child, new Coord(preX, preY));
                }
            }
            height = height > 0 ? height + margin : 0;
            preHeight = height;
        }

        return preHeight;
    }

    private int calculateSize(int measureSpec, int desiredSize) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        int actualSize;

        if (mode == View.MeasureSpec.EXACTLY) {
            actualSize = size;
        } else if (mode == View.MeasureSpec.AT_MOST) {
            actualSize = Math.min(desiredSize, size);
        } else {
            actualSize = desiredSize;
        }

        return actualSize;
    }

    private int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
