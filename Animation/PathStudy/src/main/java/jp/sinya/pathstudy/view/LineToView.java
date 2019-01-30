package jp.sinya.pathstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Koizumi Sinya
 * @date 2017/10/22. 16:16
 * @edithor
 * @date
 */
public class LineToView extends View {
    public LineToView(Context context) {
        super(context);
    }

    public LineToView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineToView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        path.moveTo(0, 0);
        path.lineTo(250, 1000);
        canvas.drawPath(path, paint);
    }
}
