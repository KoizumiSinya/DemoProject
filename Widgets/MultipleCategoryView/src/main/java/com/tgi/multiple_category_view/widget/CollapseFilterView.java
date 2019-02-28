package com.tgi.multiple_category_view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tgi.multiple_category_view.OnFilterCollapseListener;

/**
 * @author: Sinya
 * @date: 2019/02/27. 20:05
 * @editor:
 * @edit date:
 */
public class CollapseFilterView extends ViewGroup {

    private int margin;
    private int realMargin;

    private boolean isBusy;

    private float startX;
    private float startY;

    private OnFilterCollapseListener collapseListener;

    public boolean isBusy() {
        return isBusy;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setCollapseListener(OnFilterCollapseListener collapseListener) {
        this.collapseListener = collapseListener;
    }

    public CollapseFilterView(Context context) {
        this(context, null);
    }

    public CollapseFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapseFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        margin = dp2px(getContext(), 6);
        realMargin = margin;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            if (getChildAt(i) instanceof FilterItemView) {
                FilterItemView child = (FilterItemView) getChildAt(i);
                child.layout(0, 0, child.getCollapsedSize() / 2 + child.getMeasuredWidth() / 2 + 1, child.getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 0) {

            if (getChildAt(0) instanceof FilterItemView) {
                FilterItemView child = (FilterItemView) getChildAt(0);
                child.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                realMargin = calculateMargin(((ViewGroup) getParent()).getMeasuredWidth(), child.getCollapsedSize(), margin);

                int width = getChildCount() * child.getCollapsedSize() + getChildCount() * realMargin + realMargin;
                setMeasuredDimension(width, calculateSize(margin * 2 + child.getCollapsedSize(), LayoutParams.MATCH_PARENT));

            }

        } else {
            setMeasuredDimension(0, 0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return getChildCount() > 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(startX - event.getX()) < 20 && event.getY() - startY > 20) {
                    if (!isBusy) {
                        if (collapseListener != null) {
                            collapseListener.expand();
                        }
                    }
                    startX = 0f;
                    startY = 0f;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isBusy && isClick(startX, startY, event.getX(), event.getY())) {
                    FilterItemView item = findViewByCoord(event.getX());
                    if (item != null) {
                        item.dismiss();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    private FilterItemView findViewByCoord(float x) {
        for (int i = 0; i < getChildCount() - 1; i++) {

            FilterItemView itemView = (FilterItemView) getChildAt(i);
            if (containsCoord(itemView, x)) {
                return itemView;
            }
        }

        return null;
    }

    private boolean containsCoord(FilterItemView item, float x) {
        return item.getX() + item.getFullSize() / 2 - item.getCollapsedSize() / 2 <= x && x <= item.getX() + item.getFullSize() / 2f + item.getCollapsedSize() / 2f;
    }

    public boolean removeItem(final FilterItemView itemView) {
        if (isBusy) {
            return false;
        }
        final int index = indexOfChild(itemView);

        isBusy = true;


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 200).setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 200;

                for (int i = index + 1; i < getChildCount() - 1; i++) {
                    FilterItemView item = (FilterItemView) getChildAt(i);
                    if (ratio == 0f) {
                        item.setStartX(item.getX());
                    }
                    item.setTranslationX(item.getStartX() + (-itemView.getCollapsedSize() - realMargin) * ratio);
                    item.setAlpha(1 - ratio);
                }

                if (ratio == 1f) {
                    itemView.setTranslationX(itemView.getStartX() + (-itemView.getCollapsedSize() - realMargin) * ratio);
                    isBusy = false;
                }
            }
        });
        valueAnimator.start();
        return true;
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

    private int calculateMargin(int width, int itemWidth, int margin) {
        int count = calculateCount(width, itemWidth, margin);
        if (count > 0) {
            return (width - count * itemWidth) / count;
        } else {
            return 0;
        }
    }

    private int calculateCount(int width, int itemWidth, int margin) {
        return width / (itemWidth + margin);
    }

    private int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private boolean isClick(float startX, float startY, float x, float y) {
        return Math.abs(x - startX) < 20 && Math.abs(y - startY) < 20;
    }
}
