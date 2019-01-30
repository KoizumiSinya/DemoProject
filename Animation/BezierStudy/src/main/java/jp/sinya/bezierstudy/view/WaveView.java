package jp.sinya.bezierstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 贝塞尔曲线 与 属性动画结合 简单的实现波浪变化
 *
 * @author Koizumi Sinya
 * @date 2017/10/19. 10:34
 * @edithor
 * @date
 */
public class WaveView extends View implements View.OnClickListener {

    //起始点
    private float startX;
    private float startY;

    //结束点
    private float endX;
    private float endY;

    //结束点
    private float end2X;
    private float end2Y;

    //控制点1
    private float flag1X;
    private float flag1Y;

    //控制点2
    private float flag2X;
    private float flag2Y;

    private Path bezierPath;
    private Paint bezierPaint;

    private Paint pointPaint;
    private Paint linePaint;

    private ValueAnimator valueAnimatorFlagY;
    private ValueAnimator valueAnimatorFlag2Y;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStrokeWidth(6);
        bezierPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStrokeWidth(8);
        pointPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.DKGRAY);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //第一个贝塞尔的终点 也是第二个贝塞尔的起点 使用屏幕的正中心点
        endX = w / 2;
        //起始点纵坐标位于屏幕的中间往上200px处
        endY = h / 2;

        //起始点  结束点
        startX = endX - 300;
        end2X = endX + 300;

        startY = endY;
        end2Y = endY;

        flag1X = (startX + endX) / 2;
        flag1Y = endY - 200;

        flag2X = (endX + end2X) / 2;
        flag2Y = endY + 200;

        bezierPath = new Path();

        valueAnimatorFlagY = ValueAnimator.ofFloat(flag1Y, flag1Y + 400);
        valueAnimatorFlag2Y = ValueAnimator.ofFloat(flag2Y, flag2Y - 400);

        valueAnimatorFlagY.setDuration(2000);
        valueAnimatorFlag2Y.setDuration(2000);
        valueAnimatorFlagY.setRepeatCount(-1);
        valueAnimatorFlag2Y.setRepeatCount(-1);
        valueAnimatorFlagY.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimatorFlag2Y.setRepeatMode(ValueAnimator.REVERSE);

        valueAnimatorFlagY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flag1Y = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimatorFlag2Y.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flag2Y = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //绘制左边的波浪
        bezierPath.reset();
        //先将path移动到起始点坐标
        bezierPath.moveTo(startX, startY);
        bezierPath.quadTo(flag1X, flag1Y, endX, endY);//绝对坐标
        canvas.drawPath(bezierPath, bezierPaint);

        //绘制右边的波浪
        bezierPath.reset();
        //先将path移动到起始点坐标
        bezierPath.moveTo(endX, endY);
        bezierPath.quadTo(flag2X, flag2Y, end2X, end2Y);//绝对坐标
        canvas.drawPath(bezierPath, bezierPaint);

        //绘制连接线
        canvas.drawLine(startX, startY, flag1X, flag1Y, linePaint);
        canvas.drawLine(flag1X, flag1Y, endX, endY, linePaint);
        canvas.drawLine(endX, endY, flag2X, flag2Y, linePaint);
        canvas.drawLine(flag2X, flag2Y, end2X, end2Y, linePaint);

        //绘制点
        pointPaint.setColor(Color.BLUE);
        canvas.drawCircle(startX, startY, 3, pointPaint);
        canvas.drawCircle(endX, endY, 3, pointPaint);
        canvas.drawCircle(end2X, end2Y, 3, pointPaint);

        pointPaint.setColor(Color.RED);
        canvas.drawCircle(flag1X, flag1Y, 3, pointPaint);
        canvas.drawCircle(flag2X, flag2Y, 3, pointPaint);
    }

    @Override
    public void onClick(View v) {
        valueAnimatorFlagY.start();
        valueAnimatorFlag2Y.start();
    }
}
