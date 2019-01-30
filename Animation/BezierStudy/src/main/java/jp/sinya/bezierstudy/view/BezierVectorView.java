package jp.sinya.bezierstudy.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 贝塞尔曲线 与 属性动画结合 简单的实现动态变化
 *
 * @author Koizumi Sinya
 * @date 2017/10/19. 10:34
 * @edithor
 * @date
 */
public class BezierVectorView extends View implements View.OnClickListener {

    //起始点
    private float startX;
    private float startY;

    //结束点
    private float endX;
    private float endY;

    //控制点1
    private float flag1X;
    private float flag1Y;

    //控制点2
    private float flag2X;
    private float flag2Y;

    private Path bezierPath;
    private Paint bezierPaint;

    private Paint textPaint;
    private Paint pointPaint;
    private Paint linePaint;

    private ValueAnimator valueAnimatorFlagX;
    private ValueAnimator valueAnimatorFlagY;

    private ValueAnimator valueAnimatorFlag2X;
    private ValueAnimator valueAnimatorFlag2Y;

    private ValueAnimator valueAnimatorFlag3X;
    private ValueAnimator valueAnimatorFlag3Y;

    public BezierVectorView(Context context) {
        super(context);
    }

    public BezierVectorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStrokeWidth(6);
        bezierPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStrokeWidth(8);
        pointPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.DKGRAY);
    }

    public BezierVectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //起始点横坐标位于屏幕的左边 1/4处
        startX = w / 5;
        //起始点纵坐标位于屏幕的中间往上200px处
        startY = h / 2 - 200;

        endX = w * 4 / 5;
        endY = h / 2 - 200;

        flag1X = startX;
        flag1Y = startY;

        flag2X = endX;
        flag2Y = endY;

        bezierPath = new Path();

        valueAnimatorFlagY = ValueAnimator.ofFloat(flag1Y, flag1Y + 700);
        valueAnimatorFlag2Y = ValueAnimator.ofFloat(flag2Y, flag2Y - 380);

        valueAnimatorFlagX = ValueAnimator.ofFloat(flag1X, flag1X + 300);
        valueAnimatorFlag2X = ValueAnimator.ofFloat(flag2X, flag2X - 180);

        //valueAnimator.setInterpolator(new BounceInterpolator());

        valueAnimatorFlagY.setDuration(4000);
        valueAnimatorFlag2Y.setDuration(5000);
        valueAnimatorFlagX.setDuration(4000);
        valueAnimatorFlag2X.setDuration(4000);

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
        valueAnimatorFlag2Y.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimatorFlag3X = ValueAnimator.ofFloat(flag2X, flag2X - 180);
                valueAnimatorFlag3X.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        flag2X = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimatorFlag3X.setDuration(2000);
                valueAnimatorFlag3X.start();


                valueAnimatorFlag3Y = ValueAnimator.ofFloat(flag1Y, flag1Y - 280);
                valueAnimatorFlag3Y.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        flag1Y = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimatorFlag3Y.setDuration(2000);
                valueAnimatorFlag3Y.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimatorFlagX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flag1X = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorFlag2X.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flag2X = (float) animation.getAnimatedValue();
                invalidate();
            }
        });


        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bezierPath.reset();

        //先将path移动到起始点坐标
        bezierPath.moveTo(startX, startY);

        //贝塞尔曲线绘制3阶
        bezierPath.cubicTo(flag1X, flag1Y, flag2X, flag2Y, endX, endY);//绝对坐标
        //bezierPath.rCubicTo();//相对坐标

        //根据路径 在canvas绘制贝塞尔曲线
        canvas.drawPath(bezierPath, bezierPaint);

        //绘制连接线
        canvas.drawLine(startX, startY, flag1X, flag1Y, linePaint);
        canvas.drawLine(flag1X, flag1Y, flag2X, flag2Y, linePaint);
        canvas.drawLine(flag2X, flag2Y, endX, endY, linePaint);

        //绘制点
        pointPaint.setColor(Color.BLUE);
        canvas.drawCircle(startX, startY, 3, pointPaint);
        canvas.drawCircle(endX, endY, 3, pointPaint);
        pointPaint.setColor(Color.RED);
        canvas.drawCircle(flag1X, flag1Y, 3, pointPaint);
        canvas.drawCircle(flag2X, flag2Y, 3, pointPaint);
    }

    @Override
    public void onClick(View v) {
        valueAnimatorFlagY.start();
        valueAnimatorFlag2Y.start();
        valueAnimatorFlagX.start();
        valueAnimatorFlag2X.start();
    }
}
