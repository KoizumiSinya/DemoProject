package jp.sinya.refreshlibrary.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class PullableImageView extends ImageView implements Pullable {

    private boolean isCanLoad;
    private boolean isCanRefresh = true;

    public void setIsCanLoad(boolean isCanLoad) {
        this.isCanLoad = isCanLoad;
    }

    public void setIsCanRefresh(boolean isCanRefresh) {
        this.isCanRefresh = isCanRefresh;
    }

    public PullableImageView(Context context) {
        super(context);
    }

    public PullableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableImageView(Context context, AttributeSet attrs, int defStyle) {
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
