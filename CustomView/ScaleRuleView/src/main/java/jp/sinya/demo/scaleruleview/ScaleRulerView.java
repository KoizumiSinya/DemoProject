package jp.sinya.demo.scaleruleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

/**
 * @author Sinya
 * @date 2018/05/04. 11:54
 * @edithor
 * @date
 */
public class ScaleRulerView extends View {

    private Context context;

    private int minVelocity;
    private int width;
    private int height;

    /**
     * 未选择时，表示默认值
     * 滑动中，表示当前指针对着的值
     */
    private float selectorValue;

    private float maxValue;
    private float minValue;
    /**
     * 步长对应的值
     */
    private float preValue = 1;

    /**
     * 两条刻度线之间的距离
     */
    private float lineSpaceWidth = 5;
    /**
     * 刻度线的宽度
     */
    private float lineWidth = 4;
    private float lineMidWidth = 4.5f;
    private float lineMaxWidth = 5.0f;

    /**
     * 最大的刻度线高度
     */
    private float lineMaxHeight = 400;
    /**
     * 位于中间的刻度线高度
     */
    private float lineMidHeight = 30;
    /**
     * 最小的刻度线高度
     */
    private float lineMinHeight = 10;

    private int lineColor = Color.GRAY;

    //刻度线上的数字
    private float textMarginTop = 10;
    private float textSize = 30;
    private float textHeight;
    private int textColor = Color.BLACK;

    private Paint paintText;
    private Paint paintLine;

    private int totalLines;
    private int maxOffset;
    /**
     * 默认状态下 selectorValue所在的位置
     */
    private float offset;
    private int lastX;
    private int move;

    private boolean isAlphaEnable;
    private ValueAnimator animator;
    private ValueAnimator animator2;
    private float flag = 0;

    public float getSelectorValue() {
        return selectorValue;
    }

    public void setSelectorValue(float newValue) {

        if (newValue >= maxValue && selectorValue == maxValue) {
            invalidate();
            return;
        }

        if (newValue <= minValue && selectorValue == minValue) {
            invalidate();
            return;
        }

        if (newValue >= maxValue) {
            newValue = maxValue;
        }
        if (newValue <= minValue) {
            newValue = minValue;
        }

        float lastValue = selectorValue;

        if (newValue - lastValue > 20) {
            flag = 20;
        } else if (lastValue - newValue > 20) {
            flag = -20;
        }

        animator = ValueAnimator.ofFloat(lastValue, newValue - flag).setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                selectorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        if (flag != 0) {
            animator2 = ValueAnimator.ofFloat(newValue - flag, newValue).setDuration(800);
            animator2.setInterpolator(new LinearInterpolator());
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    selectorValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animator2.start();
                }
            });
        }
        animator.start();
    }

    public ScaleRulerView(Context context) {
        this(context, null);
    }

    public ScaleRulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init(attrs);
        initPaint();
        initDefaultParameters();
    }

    private void initDefaultParameters() {
        preValue = (int) (preValue * 10.0f);
        totalLines = ((int) ((maxValue * 10 - minValue * 10) / preValue)) + 1;
        maxOffset = (int) (-(totalLines - 1) * lineSpaceWidth);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            width = w;
            height = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        offset = (minValue - selectorValue) / preValue * lineSpaceWidth * 10;


        String value;
        float left;
        float height;

        int alpha = 0;
        float scale;

        int srcPointX = width / 2;

        for (int i = 0; i < totalLines; i++) {

            left = srcPointX + offset + i * lineSpaceWidth;

            if (left < 0 || left > width) {
                continue;
            }

            if (i % 10 == 0) {
                value = String.valueOf((int) (minValue + i * preValue / 10));
                if (isAlphaEnable) {
                    paintText.setAlpha(alpha);
                }
                canvas.drawText(value, left - paintText.measureText(value) / 2, textHeight + textMarginTop, paintText);
            }

            float lineStartY = textHeight + textMarginTop * 3;

            if (i % 10 == 0) {
                height = lineMaxHeight;
                paintLine.setStrokeWidth(lineMaxWidth);
            } else if (i % 5 == 0) {
                height = lineMidHeight;
                paintLine.setStrokeWidth(lineMidWidth);
            } else {
                height = lineMinHeight;
                paintLine.setStrokeWidth(lineWidth);
            }

            if (isAlphaEnable) {
                scale = 1 - Math.abs(left - srcPointX) / srcPointX;
                alpha = (int) (255 * scale * scale);
                paintLine.setAlpha(alpha);

            }
            canvas.drawLine(left, lineStartY, left, height + lineStartY, paintLine);


        }
    }

    private void init(AttributeSet attrs) {

        this.lineSpaceWidth = float2Int(25.0f);
        this.lineWidth = float2Int(2.0f);
        this.lineMaxHeight = float2Int(100.0f);
        this.lineMidHeight = float2Int(60.0f);
        this.lineMinHeight = float2Int(40.0f);
        this.textHeight = float2Int(40.0f);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScaleRulerView);

        lineSpaceWidth = typedArray.getDimension(R.styleable.ScaleRulerView_line_space_width, lineSpaceWidth);
        lineWidth = typedArray.getDimension(R.styleable.ScaleRulerView_line_width, lineWidth);
        lineMaxHeight = typedArray.getDimension(R.styleable.ScaleRulerView_line_max_height, lineMaxHeight);
        lineMidHeight = typedArray.getDimension(R.styleable.ScaleRulerView_line_mid_height, lineMidHeight);
        lineMinHeight = typedArray.getDimension(R.styleable.ScaleRulerView_line_min_height, lineMinHeight);

        textSize = typedArray.getDimension(R.styleable.ScaleRulerView_text_size, textSize);
        textColor = typedArray.getColor(R.styleable.ScaleRulerView_text_color, textColor);
        textMarginTop = typedArray.getDimension(R.styleable.ScaleRulerView_text_margin_top, textMarginTop);

        selectorValue = typedArray.getFloat(R.styleable.ScaleRulerView_value, selectorValue);
        maxValue = typedArray.getFloat(R.styleable.ScaleRulerView_max_value, maxValue);
        minValue = typedArray.getFloat(R.styleable.ScaleRulerView_min_value, minValue);
        preValue = typedArray.getFloat(R.styleable.ScaleRulerView_pre_value, preValue);

        isAlphaEnable = typedArray.getBoolean(R.styleable.ScaleRulerView_alpha_enable, false);

        minVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();

        typedArray.recycle();
    }

    private void initPaint() {
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize(textSize);
        paintText.setColor(textColor);

        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStrokeWidth(lineWidth);
        paintLine.setColor(lineColor);
    }

    private int float2Int(float paramFloat) {
        return (int) (0.5F + paramFloat * 1.0f);
    }

}
