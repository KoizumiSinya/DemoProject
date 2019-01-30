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
 * @date 2017/10/22. 17:13
 * @edithor
 * @date
 */
public class PathOpView extends View {
    public PathOpView(Context context) {
        super(context);
    }

    public PathOpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathOpView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        Path path = new Path();
        Path path2 = new Path();

        path.addCircle(200, 200, 100, Path.Direction.CW);
        path2.addRect(200, 200, 300, 300, Path.Direction.CW);

        //path.op(path2, Path.Op.DIFFERENCE);//保留在path范围内的path2，其余的path2部分统统丢弃
        //path.op(path2, Path.Op.INTERSECT);//仅有path和path2相交的部分保留，其余部分统统丢弃
        //path.op(path2, Path.Op.REVERSE_DIFFERENCE);//保留在path范围内的path2，其余的path部分统统丢弃
        //path.op(path2, Path.Op.UNION);//取最两者相接最大的范围
        path.op(path2, Path.Op.XOR);//取最两者相接最大的范围
        canvas.drawPath(path, paint);

    }
}
