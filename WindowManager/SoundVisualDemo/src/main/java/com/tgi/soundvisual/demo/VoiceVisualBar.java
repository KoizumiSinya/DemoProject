package com.tgi.soundvisual.demo;

import android.animation.Animator;
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
    public static final int TYPE_LISTENING_START = 0;
    public static final int TYPE_LISTENING = 1;
    public static final int TYPE_THINKING = 2;
    public static final int TYPE_SPEAKING = 3;
    public static final int TYPE_OFF = 4;
    private int type = TYPE_LISTENING_START;

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

    private ValueAnimator animator;

    private int mViewWidth = 0;

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

            final float[] position = new float[]{0f, 0.5f, 1.0f};
            final int[] colors = new int[]{backgroundColor, visualColor, backgroundColor};

            if (mViewWidth > 0) {
                backgroundPaint = getPaint();
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);
                backgroundMatrix = new Matrix();
            }

            //setType(TYPE_LISTENING_START);
        }
    }

    private void init() {

    }

    public void onDestory() {
        if (animator != null) {
            animator.end();
            animator = null;
        }
    }

    public void setType(int type) {
        this.type = type;

        if (type == TYPE_LISTENING_START) {
            onStartListeningStartAnimation();
        } else if (type == TYPE_LISTENING) {
            onStartListeningAnimation();
        } else if (type == TYPE_THINKING) {
            onStartThinkingAnimation();
        } else if (type == TYPE_SPEAKING) {
            onStartSpeakingAnimation();
        } else if (type == TYPE_OFF) {
            onStartOffAnimation();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {

        backgroundMatrix.setTranslate(0, 0);
        backgroundShader.setLocalMatrix(backgroundMatrix);

        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), backgroundPaint);
    }

    private void onStartListeningStartAnimation() {

        final float[] position = new float[]{0f, 0.01f, 0.11f, 0.89f, 0.99f, 1.0f};
        final int[] colors = new int[]{backgroundColor, visualColor, backgroundColor, backgroundColor, visualColor, backgroundColor};

        animator = new ValueAnimator();
        animator.setFloatValues(0f, 0.39f);
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onStartListeningStartAnimation2();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                position[1] = 0.01f + value;
                position[2] = 0.11f + value;

                position[3] = 0.89f - value;
                position[4] = 0.99f - value;
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);

                invalidate();
            }
        });
        animator.start();
    }

    private void onStartListeningStartAnimation2() {

        final float[] position = new float[]{0f, 0.39f, 0.45f, 0.55f, 0.61f, 1.0f};
        final int[] colors = new int[]{backgroundColor, backgroundColor, visualColor, visualColor, backgroundColor, backgroundColor};

        animator = new ValueAnimator();
        animator.setFloatValues(0f, 0.19f);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                position[1] = 0.39f - value;
                position[4] = 0.61f + value;
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);
                invalidate();
            }
        });
        animator.start();
    }

    private void onStartListeningAnimation() {

        final float[] position = new float[]{0f, 0.49f, 0.51f, 1.0f};
        final int[] colors = new int[]{backgroundColor, visualColor, visualColor, backgroundColor};

        animator = new ValueAnimator();
        animator.setFloatValues(0f, 0.19f);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(10);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onStartListeningAnimation2();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                position[1] = 0.49f - value;
                position[2] = 0.51f + value;
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);
                invalidate();
            }
        });
        animator.start();
    }

    private void onStartListeningAnimation2() {

        final float[] position = new float[]{0f, 0.3f, 0.7f, 1.0f};
        final int[] colors = new int[]{backgroundColor, visualColor, visualColor, backgroundColor};

        animator = new ValueAnimator();
        animator.setFloatValues(0f, 0.3f);
        animator.setDuration(400);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                position[1] = 0.3f - value;
                position[2] = 0.7f + value;
                backgroundShader = new LinearGradient(0, 0, getMeasuredWidth(), getHeight(), colors, position, Shader.TileMode.CLAMP);
                backgroundPaint.setShader(backgroundShader);
                invalidate();
            }
        });
        animator.start();
    }

    private void onStartThinkingAnimation() {

    }

    private void onStartSpeakingAnimation() {

    }

    private void onStartOffAnimation() {

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

    public void onStop() {
        if (animator != null) {
            animator.end();
        }
    }
}
