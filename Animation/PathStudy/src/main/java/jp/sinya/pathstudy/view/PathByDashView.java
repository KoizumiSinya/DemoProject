package jp.sinya.pathstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 使用Dash 虚线与实线 交互的方式，来实现path路径绘制
 *
 * @author Koizumi Sinya
 * @date 2017/10/24. 18:53
 * @edithor
 * @date
 */
public class PathByDashView extends View implements View.OnClickListener {
    private Path path;
    private Paint paint;
    private DashPathEffect effect;
    private PathMeasure measure;
    private float length;
    private ValueAnimator animator;

    public PathByDashView(Context context) {
        super(context);
    }

    public PathByDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        measure = new PathMeasure();

        path = new Path();
        path.moveTo(300, 100);
        path.lineTo(50, 400);
        path.lineTo(550, 400);
        path.close();

        measure.setPath(path, true);
        length = measure.getLength();

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                effect = new DashPathEffect(new float[]{length, length}, value * length);
                paint.setPathEffect(effect);
                invalidate();
            }
        });
        setOnClickListener(this);
    }

    public PathByDashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawPath(path, paint);

    }

    @Override
    public void onClick(View v) {
        animator.start();
    }
}
