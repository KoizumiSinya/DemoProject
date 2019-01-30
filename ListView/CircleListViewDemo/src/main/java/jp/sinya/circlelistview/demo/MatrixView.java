package jp.sinya.circlelistview.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MatrixView extends RelativeLayout {
    // 屏幕高度
    private int screenH = 0;
    // 屏幕宽度
    private int screenW = 0;
    int itemCounts;

    public static final int MODE_VERTICAL = 0;
    public static final int MODE_HORIZONTAL = 1;
    public static final int MODE_GALLERY = 2;
    public int mMode = MODE_VERTICAL;

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setParentHeight(int height) {
        screenH = height;
    }

    public void setParentWidth(int w) {
        screenW = w;
    }

    public void setMode(int mode) {
        this.mMode = mode;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        // code for vertical mode
        if (mMode == MODE_VERTICAL) {
            int top = getTop();
            // item的高度
            int itemH = canvas.getHeight();
            // 满屏可以防止item的个数
            itemCounts = screenH / itemH;
            float scale = calcuylateScaleVertical(top, screenH);
            float scaleConv = 1 - scale;
            Matrix m = canvas.getMatrix();
            float centerYItem = screenH / (itemCounts * 2f);
            m.postScale(scale, scale, screenW / 2, centerYItem);
            // 按照抛物线的轨迹设置移动距离
            m.postTranslate((1 - scaleConv * scaleConv * scaleConv - scaleConv) * 0.4f * getWidth(), 2 / getHeight());
            canvas.concat(m);

        } else {
            // code for horizontal and gallery mode
            int left = getLeft();
            int itemW = canvas.getWidth();
            if (itemCounts == 0) {
                itemCounts = screenW / itemW;
            }
            float scale;
            scale = calcuylateScaleGallery(left, screenW);
            float scaleConv = 1 - scale;
            Matrix m = canvas.getMatrix();
            float centerXItem = screenW / (itemCounts * 2f);
            m.postScale(scale, scale, centerXItem, screenH / 2);
            m.postTranslate(0, (1 - scaleConv * scaleConv * scaleConv - scaleConv) * 0.5f * getHeight());
            // m.preTranslate(-2 / getWidth(), -2 / getHeight());
            // m.postRotate(rotate);
            canvas.concat(m);
        }

        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // private float calculateAngelVertical(int top, int h) {
    // float result = 0f;
    // if (top < h / 2f) {
    // result = (top - (h / 2f)) / (h / 2f) * fullAngelFactor;
    // } else if (top > h / 2f) {
    // result = (top - (h / 2f)) / (h / 2f) * fullAngelFactor;
    // }
    // return result;
    // }

    private float calcuylateScaleHorizontal(int left, int w) {
        int centerX = w / 2;
        int counts = 2;
        int bias = Math.abs(left + screenW / counts - centerX);
        float percent = 1 - (bias / (w / 2f)) * 0.75f;

        // float result = 0f;
        // int top2 = top + 1280/4/2;
        //
        // result = (1f - 1f/2f*Math.abs((top2 - h / 2f)) / (h / 2f)) *
        // fullScaleFactor;
        return percent;

    }

    private float calcuylateScaleGallery(int left, int w) {
        if (itemCounts == 0) {
            return 0.0f;
        }
        // 设置偏移使得最大项显示在中间
        left -= (itemCounts / 2) * (screenW / itemCounts);
        int centerX = w / 2;
        int counts = 2;
        int bias = Math.abs(left + screenW / counts - centerX);
        float percent = 1 - (bias / (w / 2f)) * 0.75f;

        // float result = 0f;
        // int top2 = top + 1280/4/2;
        //
        // result = (1f - 1f/2f*Math.abs((top2 - h / 2f)) / (h / 2f)) *
        // fullScaleFactor;
        return percent;

    }

    private float calcuylateScaleVertical(int top, int h) {
        int centerY = h / 2;
        int bias = Math.abs(top + screenH / itemCounts / 2 - centerY);
        float percent = 1 - (bias / (h / 2f)) * 0.75f;

        // float result = 0f;
        // int top2 = top + 1280/4/2;
        //
        // result = (1f - 1f/2f*Math.abs((top2 - h / 2f)) / (h / 2f)) *
        // fullScaleFactor;
        return percent;

    }
}
