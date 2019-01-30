package jp.sinya.bezierstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 二阶贝塞尔曲线与位移的属性动画 构成波浪效果
 *
 * @author Koizumi Sinya
 * @date 2017/10/19. 10:34
 * @edithor
 * @date
 */
public class WaveBezierView extends View implements View.OnClickListener {
    private int waveWidth;
    private int waveCount;

    private Path bezierPath;
    private Paint bezierPaint;

    private int offset;
    private int screenWidth;
    private int screenHeight;
    private int centerY;

    public WaveBezierView(Context context) {
        super(context);
    }

    public WaveBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStrokeWidth(6);
        bezierPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bezierPaint.setColor(Color.LTGRAY);

        waveWidth = 500;
    }

    public WaveBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;
        centerY = screenHeight / 2;

        waveCount = (int) Math.round(screenWidth / waveWidth + 1.5);


        bezierPath = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bezierPath.reset();

        bezierPath.moveTo(-waveWidth + offset, centerY);

        for (int i = 0; i < waveCount; i++) {
            bezierPath.quadTo(-waveWidth * 3 / 4 + i * waveWidth + offset, centerY + 60, -waveWidth / 2 + i * waveWidth + offset, centerY);//绝对坐标
            bezierPath.quadTo(-waveWidth / 4 + i * waveWidth + offset, centerY - 60, i * waveWidth + offset, centerY);//绝对坐标
        }

        //形成一个封闭的路径
        bezierPath.lineTo(screenWidth, screenHeight);
        bezierPath.lineTo(0, screenHeight);
        bezierPath.close();

        //根据路径 在canvas绘制贝塞尔曲线
        canvas.drawPath(bezierPath, bezierPaint);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, waveWidth);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
