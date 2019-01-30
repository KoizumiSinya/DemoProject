package jp.sinya.refreshlibrary.pullableview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class PullableTextView extends TextView implements Pullable {
    private boolean isCanLoad;
    private boolean isCanRefresh = true;

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public void setIsCanRefresh(boolean isCanRefresh) {
        this.isCanRefresh = isCanRefresh;
    }

    public PullableTextView(Context context) {
        super(context);
    }

    public PullableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        return isCanRefresh;
    }

    @Override
    public boolean canPullUp() {
        return isCanLoad;
    }

}
