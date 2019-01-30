package jp.sinya.stickyrecycer.demo1.sticky;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class StickyRecyclerView extends RecyclerView {

    private BaseStickyDecoration mDecoration;

    public StickyRecyclerView(Context context) {
        super(context);
    }

    public StickyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addItemDecoration(ItemDecoration decor) {
        if (decor != null && decor instanceof BaseStickyDecoration) {
            mDecoration = (BaseStickyDecoration) decor;
        }
        super.addItemDecoration(decor);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mDecoration != null) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDecoration.onEventDown(e);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mDecoration.onEventUp(e)) {
                        return true;
                    }
                    break;
                default:
            }
        }
        return super.onInterceptTouchEvent(e);
    }
}

