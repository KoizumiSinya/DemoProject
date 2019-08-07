package com.tgi.soundvisual.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


/**
 * @author: Sinya
 * @date: 2019-08-07. 16:44
 * @editor:
 * @date:
 */
public class VoiceVisualBar extends View {

    private int backgroundColor = Color.parseColor("#214CFB");
    private int visualColor = Color.parseColor("#05FEFE");

    private LinearGradient backgroundShader;
    private LinearGradient visualShader;

    private Paint backgroundPaint;
    private Paint visualPaint;
    private Paint visualPaint2;

    private Matrix backgroundMatrix;
    private Matrix visualMatrix;
    private Matrix visualMatrix2;

    private int mViewWidth = 0;
    private float[] position = new float[]{0f, 0.1f, 0.9f, 1.0f};
    private int[] colors = new int[]{backgroundColor, visualColor, visualColor, backgroundColor};

    public VoiceVisualBar(Context context) {
        super(context);
        init();
    }

    public VoiceVisualBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoiceVisualBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getWidth();

            if (mViewWidth > 0) {
                backgroundPaint = getPaint();
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);
                backgroundMatrix = new Matrix();
            }

            ValueAnimator animator = new ValueAnimator();
            animator.setFloatValues(0f, 0.3f);
            animator.setDuration(250);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(200);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    position[1] = 0.1f + value;
                    position[2] = 0.9f - value;
                    backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                    backgroundPaint.setShader(backgroundShader);
                    invalidate();
                }
            });
            animator.start();
        }
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRainbow(canvas);
    }

    private void drawRainbow(Canvas canvas) {

        backgroundMatrix.setTranslate(0, 0);
        backgroundShader.setLocalMatrix(backgroundMatrix);

        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), backgroundPaint);
    }

    public Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);


        return paint;
    }

}
