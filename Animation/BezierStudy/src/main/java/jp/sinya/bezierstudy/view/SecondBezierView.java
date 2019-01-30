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
 * 二阶贝塞尔曲线
 *
 * @author Koizumi Sinya
 * @date 2017/10/19. 10:34
 * @edithor
 * @date
 */
public class SecondBezierView extends View {

    //起始点
    private float startX;
    private float startY;

    //结束点
    private float endX;
    private float endY;

    //控制点
    private float flagX;
    private float flagY;

    private Path bezierPath;
    private Paint bezierPaint;

    private Paint textPaint;
    private Paint pointPaint;
    private Paint linePaint;

    public SecondBezierView(Context context) {
        super(context);
    }

    public SecondBezierView(Context context, AttributeSet attrs) {
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

    public SecondBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //起始点横坐标位于屏幕的左边 1/4处
        startX = w / 4;
        //起始点纵坐标位于屏幕的中间往上200px处
        startY = h / 2 - 200;

        endX = w * 3 / 4;
        endY = h / 2 - 200;

        flagX = w / 2;
        flagY = h / 2 - 400;

        bezierPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bezierPath.reset();

        //先将path移动到起始点坐标
        bezierPath.moveTo(startX, startY);

        //贝塞尔曲线绘制2阶
        bezierPath.quadTo(flagX, flagY, endX, endY);//绝对坐标
        //bezierPath.rQuadTo();//相对坐标

        //根据路径 在canvas绘制贝塞尔曲线
        canvas.drawPath(bezierPath, bezierPaint);

        //绘制连接线
        canvas.drawLine(startX, startY, flagX, flagY, linePaint);
        canvas.drawLine(flagX, flagY, endX, endY, linePaint);

        //绘制其它的点
        canvas.drawCircle(startX, startY, 3, pointPaint);
        canvas.drawCircle(endX, endY, 3, pointPaint);
        canvas.drawCircle(flagX, flagY, 3, pointPaint);
        //canvas.drawPoint(startX, startY, pointPaint);
        //canvas.drawPoint(endX, endY, pointPaint);
        //canvas.drawPoint(flagX, flagY, pointPaint);

        //绘制文字标识
        canvas.drawText("起始点", startX - 70, startY + 35, textPaint);
        canvas.drawText("结束点", endX + 10, endY + 30, textPaint);
        canvas.drawText("控制点", flagX - 40, flagY - 20, textPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                flagX = event.getX();
                flagY = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
