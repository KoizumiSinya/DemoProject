package jp.sinya.pathstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Koizumi Sinya
 * @date 2017/10/23. 13:31
 * @edithor
 * @date
 */
public class PathMeasureLoadView extends View implements View.OnClickListener {
    private Paint paint;
    private Path path;
    private Path path2;

    private PathMeasure pathMeasure;
    private float measureLength;
    private float animValue;
    private ValueAnimator animator;

    public PathMeasureLoadView(Context context) {
        super(context);
    }

    public PathMeasureLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        path = new Path();
        path2 = new Path();

        path.addCircle(400, 400, 100, Path.Direction.CW);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, true);

        measureLength = pathMeasure.getLength();

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        setOnClickListener(this);

    }

    public PathMeasureLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path2.reset();
        path2.lineTo(0, 0);

        //float start = 0;
        float stop = measureLength * animValue;
        float start = (float) (stop - ((0.5 - Math.abs(animValue - 0.5)) * measureLength));

        pathMeasure.getSegment(start, stop, path2, true);
        canvas.drawPath(path2, paint);
    }

    @Override
    public void onClick(View v) {
        animator.start();
    }
}
