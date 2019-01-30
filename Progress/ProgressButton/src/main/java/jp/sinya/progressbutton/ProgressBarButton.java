package jp.sinya.progressbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @author Koizumi Sinya
 * @date 2018/06/24. 14:28
 * @edithor
 * @date
 */
public class ProgressBarButton extends ProgressBar {

    private Context mContext;
    private Paint mPaint;
    private PorterDuffXfermode mDuffX;
    private int mProgress;
    private int mState;

    // IconTextProgressBar的状态
    private static final int STATE_DEFAULT = 101;
    private static final int STATE_DOWNLOADING = 102;
    private static final int STATE_PAUSE = 103;
    private static final int STATE_DOWNLOAD_FINISH = 104;

    // IconTextProgressBar的文字大小(sp)
    private static final float TEXT_SIZE_SP = 17f;

    // IconTextProgressBar的图标与文字间距(dp)
    private static final float ICON_TEXT_SPACING_DP = 5f;

    public ProgressBarButton(Context context) {
        super(context, null, android.R.attr.progressBarStyleHorizontal);
        mContext = context;
        init();
    }

    public ProgressBarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 设置下载状态
     */
    public synchronized void setState(int state) {
        mState = state;
        invalidate();
    }

    /**
     * 设置下载进度
     */
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        mProgress = progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mState) {
            case STATE_DEFAULT:
                drawIconAndText(canvas, STATE_DEFAULT, false);
                break;

            case STATE_DOWNLOADING:
                drawIconAndText(canvas, STATE_DOWNLOADING, false);
                break;

            case STATE_PAUSE:
                drawIconAndText(canvas, STATE_PAUSE, false);
                break;

            case STATE_DOWNLOAD_FINISH:
                drawIconAndText(canvas, STATE_DOWNLOAD_FINISH, false);
                break;

            default:
                drawIconAndText(canvas, STATE_DEFAULT, true);
                break;
        }
    }

    private void init() {
        setIndeterminate(false);
        setIndeterminateDrawable(ContextCompat.getDrawable(mContext, android.R.drawable.progress_indeterminate_horizontal));
        setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_rectangle_gradient_red));
        setMax(100);

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(MeasureUtil.sp2px(mContext, TEXT_SIZE_SP));
        mPaint.setTypeface(Typeface.MONOSPACE);

        mDuffX = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    private void initForState(int state) {
        switch (state) {
            case STATE_DEFAULT:
                setProgress(100);
                mPaint.setColor(Color.WHITE);
                break;

            case STATE_DOWNLOADING:
                mPaint.setColor(ContextCompat.getColor(mContext, R.color.mc_red_center));
                break;

            case STATE_PAUSE:
                mPaint.setColor(ContextCompat.getColor(mContext, R.color.mc_red_center));
                break;

            case STATE_DOWNLOAD_FINISH:
                setProgress(100);
                mPaint.setColor(Color.WHITE);
                break;

            default:
                setProgress(100);
                mPaint.setColor(Color.WHITE);
                break;
        }
    }

    private void drawIconAndText(Canvas canvas, int state, boolean onlyText) {
        initForState(state);

        String text = getText(state);
        Rect textRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), textRect);

        if (onlyText) {
            // 仅绘制文字
            float textX = (getWidth() / 2) - textRect.centerX();
            float textY = (getHeight() / 2) - textRect.centerY();
            canvas.drawText(text, textX, textY, mPaint);

        } else {

            // 绘制图标和文字
            Bitmap icon = getIcon(state);

            float textX = (getWidth() / 2) - getOffsetX(icon != null ? icon.getWidth() : 0, textRect.centerX(), ICON_TEXT_SPACING_DP, true);
            float textY = (getHeight() / 2) - textRect.centerY();
            canvas.drawText(text, textX, textY, mPaint);

            Bitmap bufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bufferCanvas = new Canvas(bufferBitmap);

            if (icon != null) {
                float iconX = (getWidth() / 2) - icon.getWidth() - getOffsetX(icon.getWidth(), textRect.centerX(), ICON_TEXT_SPACING_DP, false);
                float iconY = (getHeight() / 2) - icon.getHeight() / 2;
                canvas.drawBitmap(icon, iconX, iconY, mPaint);
                bufferCanvas.drawBitmap(icon, iconX, iconY, mPaint);
            }

            bufferCanvas.drawText(text, textX, textY, mPaint);

            // 设置混合模式
            mPaint.setXfermode(mDuffX);
            mPaint.setColor(Color.WHITE);
            RectF rectF = new RectF(0, 0, getWidth() * mProgress / 100, getHeight());

            // 绘制源图形
            bufferCanvas.drawRect(rectF, mPaint);

            // 绘制目标图
            canvas.drawBitmap(bufferBitmap, 0, 0, null);

            // 清除混合模式
            mPaint.setXfermode(null);


            if (icon != null && !icon.isRecycled()) {
                icon.isRecycled();
            }

            if (!bufferBitmap.isRecycled()) {
                bufferBitmap.recycle();
            }
        }
    }

    private Bitmap getIcon(int state) {
        Bitmap icon = null;
        switch (state) {
            case STATE_DEFAULT:
                icon = getBitmapFromVectorDrawable(getContext(), R.drawable.download);
                break;

            case STATE_DOWNLOADING:
                break;

            case STATE_DOWNLOAD_FINISH:
                icon = getBitmapFromVectorDrawable(getContext(), R.drawable.download_over);
                break;

            case STATE_PAUSE:
                break;

            default:
                break;
        }
        return icon;
    }

    private String getText(int state) {
        String text;
        switch (state) {
            case STATE_DEFAULT:
                text = "DOWNLOAD COOKING";
                break;

            case STATE_DOWNLOADING:
                text = "DOWNLOADING...(" + mProgress + "%)";
                break;

            case STATE_PAUSE:
                text = "CONTINUE DOWNLOAD";
                break;

            case STATE_DOWNLOAD_FINISH:
                text = "START COOKING";
                break;

            default:
                text = "DOWNLOAD COOKING";
                break;
        }
        return text;
    }

    private float getOffsetX(float iconWidth, float textHalfWidth, float spacing, boolean isText) {
        float totalWidth = iconWidth + MeasureUtil.dip2px(mContext, spacing) + textHalfWidth * 2;
        // 文字偏移量
        if (isText) {
            return totalWidth / 2 - iconWidth - spacing;
        }
        // 图标偏移量
        return totalWidth / 2 - iconWidth;
    }

    @SuppressLint("RestrictedApi")
    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
