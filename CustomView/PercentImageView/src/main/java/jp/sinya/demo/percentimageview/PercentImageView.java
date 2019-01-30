package jp.sinya.demo.percentimageview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Sinya
 * @date 2018/08/27. 10:39
 * @edithor
 * @date
 */
public class PercentImageView extends View {
    private Context context;

    private Bitmap bitmapBg;
    private Bitmap bitmapImg;
    private Bitmap bitmapImgSelect;
    private Bitmap bitmapDivider;

    private int bgRes;
    private int imgRes;
    private int imgSelectRes;
    private int dividerRes;

    private int percent;

    private int width;
    private int height;
    private int padding;

    private Paint paint;
    private PaintFlagsDrawFilter paintFilter;

    public PercentImageView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public PercentImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public PercentImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public void setPercentAnim(final int percent) {
        int lastPercent = this.percent;
        ValueAnimator animator = ValueAnimator.ofInt(lastPercent, percent);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PercentImageView.this.percent = (int) valueAnimator.getAnimatedValue();
                if (PercentImageView.this.percent > 0) {
                    bitmapImgSelect = getBitmapImageSelect(imgSelectRes);
                }
                invalidate();
            }
        });
        animator.start();
    }

    public void setPercent(int percent) {
        this.percent = percent;
        if (PercentImageView.this.percent > 0) {
            bitmapImgSelect = getBitmapImageSelect(imgSelectRes);
        }
        invalidate();
    }

    public int getPercent() {
        return percent;
    }

    private void init(AttributeSet attr) {
        if (attr != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.PercentImageView);
            bgRes = typedArray.getResourceId(R.styleable.PercentImageView_hb_percent_image_bg, 0);
            imgRes = typedArray.getResourceId(R.styleable.PercentImageView_hb_percent_image, 0);
            imgSelectRes = typedArray.getResourceId(R.styleable.PercentImageView_hb_percent_image_select, 0);
            dividerRes = typedArray.getResourceId(R.styleable.PercentImageView_hb_percent_divider, 0);
            percent = typedArray.getInt(R.styleable.PercentImageView_hb_percent, 0);
            padding = (int) typedArray.getDimension(R.styleable.PercentImageView_hb_percent_padding, 0);
            typedArray.recycle();
        }
    }

    private void initParam() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        bitmapBg = getBitmapBg(bgRes);
        bitmapImg = getBitmapImage(imgRes);
        bitmapImgSelect = getBitmapImageSelect(imgSelectRes);
        bitmapDivider = getBitmapDivider(dividerRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initParam();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        if (percent > 0) {
            drawPercentImageSelect(canvas);
            if (percent != 100) {
                drawDivider(canvas);
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        int bgWidth = bitmapBg.getWidth();
        int bgHeight = bitmapBg.getHeight();
        Matrix matrix = new Matrix();
        matrix.setTranslate((width - bgWidth) / 2, (height - bgHeight) / 2);
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bitmapBg, matrix, paint);

        bgWidth = bitmapImg.getWidth();
        bgHeight = bitmapImg.getHeight();
        matrix = new Matrix();
        matrix.setTranslate((width - bgWidth) / 2, (height - bgHeight) / 2);
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bitmapImg, matrix, paint);
    }

    private void drawPercentImageSelect(Canvas canvas) {
        int imgWidth = bitmapImgSelect.getWidth();
        int imgHeight = bitmapImgSelect.getHeight();

        Matrix matrix = new Matrix();
        matrix.setTranslate((width - imgWidth) / 2, (height - imgHeight - padding * 2) / 2 + (bitmapBg.getHeight() - imgHeight) / 2);
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bitmapImgSelect, matrix, paint);
    }

    private void drawDivider(Canvas canvas) {
        int dividerWidth = bitmapDivider.getWidth();
        int dividerHeight = bitmapDivider.getHeight();

        Matrix matrix = new Matrix();
        matrix.setTranslate((width - dividerWidth) / 2, (height - bitmapImgSelect.getHeight() - padding * 2) / 2 + (bitmapBg.getHeight() - bitmapImgSelect.getHeight()) / 2 - dividerHeight);
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bitmapDivider, matrix, paint);
    }

    @SuppressLint("RestrictedApi")
    private Bitmap getBitmapBg(int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        int bitWidth = width;
        int bitHeight = height;
        Bitmap bitmapImg = Bitmap.createBitmap(bitWidth, bitHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapImg);
        drawable.setBounds(0, 0, bitWidth, bitHeight);
        drawable.draw(canvas);

        return bitmapImg;
    }

    @SuppressLint("RestrictedApi")
    private Bitmap getBitmapImage(int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        int bitWidth = width - padding * 2;
        int bitHeight = height - padding * 2;
        Bitmap bitmap = Bitmap.createBitmap(bitWidth, bitHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitWidth, bitHeight);
        drawable.draw(canvas);

        return bitmap;
    }

    @SuppressLint("RestrictedApi")
    private Bitmap getBitmapImageSelect(int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        int bitWidth = width - padding * 2;
        int bitHeight = height - padding * 2;

        Bitmap bitmap = Bitmap.createBitmap(bitWidth, bitHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitWidth, bitHeight);
        drawable.draw(canvas);

        int startY = (int) (bitHeight * (1 - percent / 100f));
        int endY = (int) (bitHeight * (percent / 100f));
        Bitmap showBitmap = Bitmap.createBitmap(bitmap, 0, startY, bitWidth, endY);
        return showBitmap;
    }

    @SuppressLint("RestrictedApi")
    private Bitmap getBitmapDivider(int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, dp2px(1), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, dp2px(1));
        drawable.draw(canvas);

        return bitmap;
    }

    private int dp2px(double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
