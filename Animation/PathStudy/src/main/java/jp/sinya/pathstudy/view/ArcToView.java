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
 * @date 2017/10/22. 16:26
 * @edithor
 * @date
 */
public class ArcToView extends View {
    public ArcToView(Context context) {

        super(context);
    }

    public ArcToView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcToView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        //先定义一个矩形，作为这个弧的范围域。如果是正方形，则为圆上的弧。如果是长方形，则为椭圆上的弧。
        RectF rectF = new RectF(50f, 50f, 400f, 400f);

        Path path = new Path();
        //第一个参数是矩形范围域；第二个参数是绘制的起点，0表示正常钟表的3点钟位置； 第三个参数是绘制的度数。
        path.arcTo(rectF, 0, 120);

        canvas.drawPath(path, paint);
    }
}
