package jp.sinya.bezierstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 三阶贝塞尔曲线
 *
 * @author Koizumi Sinya
 * @date 2017/10/19. 10:34
 * @edithor
 * @date
 */
public class ThirdBezierView extends View {

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

    private boolean isTwoTouch;

    public ThirdBezierView(Context context) {
        super(context);
    }

    public ThirdBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStrokeWidth(6);
        bezierPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStrokeWidth(8);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setColor(Color.BLUE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.DKGRAY);
    }

    public ThirdBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        flag1X = w / 2;
        flag1Y = h / 2 - 500;

        flag2X = w * 3 / 5;
        flag2Y = h / 2 - 100;

        bezierPath = new Path();
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
        canvas.drawCircle(startX, startY, 3, pointPaint);
        canvas.drawCircle(endX, endY, 3, pointPaint);
        canvas.drawCircle(flag1X, flag1Y, 3, pointPaint);
        canvas.drawCircle(flag2X, flag2Y, 3, pointPaint);

        //绘制文字标识
        canvas.drawText("起始点", startX - 70, startY + 35, textPaint);
        canvas.drawText("结束点", endX + 10, endY + 30, textPaint);
        canvas.drawText("控制点1", flag1X - 50, flag1Y - 20, textPaint);
        canvas.drawText("控制点2", flag2X - 50, flag2Y - 20, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isTwoTouch = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                isTwoTouch = false;
                break;

            case MotionEvent.ACTION_MOVE:
                flag1X = event.getX(0);
                flag1Y = event.getY(0);

                if (isTwoTouch) {
                    flag2X = event.getX(1);
                    flag2Y = event.getY(1);
                }
                invalidate();
                break;
        }
        return true;
    }
}
