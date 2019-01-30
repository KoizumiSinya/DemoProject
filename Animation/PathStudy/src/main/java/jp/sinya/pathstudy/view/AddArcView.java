package jp.sinya.pathstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Koizumi Sinya
 * @date 2017/10/22. 16:38
 * @edithor
 * @date
 */
public class AddArcView extends View {
    public AddArcView(Context context) {
        super(context);
    }

    public AddArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        //绘制第一图形， 一个1/4圆弧
        Path path = new Path();
        RectF rectF = new RectF(0, 10, 300, 300);
        path.addArc(rectF, 0, 90);

        //绘制一个带有圆弧角的矩形
        rectF = new RectF(350, 10, 650, 300);
        path.addRoundRect(rectF, 25, 25, Path.Direction.CCW);//CW顺时针 CCW逆时针

        //绘制一个椭圆
        rectF = new RectF(10, 350, 600, 700);
        path.addOval(rectF, Path.Direction.CCW);

        //绘制一个正方形/矩形
        rectF = new RectF(10, 750, 410, 1150);
        path.addRect(rectF, Path.Direction.CW);

        //绘制一个完整的圆形
        path.addCircle(rectF.centerX(), rectF.centerY(), 100, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }
}
