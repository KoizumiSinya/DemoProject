package jp.sinya.pathstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * PosTan 是通过获取一个切线轨迹的方式，来实现运动的点
 *
 * @author Koizumi Sinya
 * @date 2017/10/24. 20:47
 * @edithor
 * @date
 */
public class PathPosTanView extends View implements View.OnClickListener {
    private Path path;
    private float[] posArr;
    private float[] tanArr;
    private Paint paint;
    private PathMeasure measure;
    private ValueAnimator animator;
    private float value;

    public PathPosTanView(Context context) {
        super(context);
    }

    public PathPosTanView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);


        measure = new PathMeasure();
        measure.setPath(path, false);

        posArr = new float[2];
        tanArr = new float[2];

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        setOnClickListener(this);

    }

    public PathPosTanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //把当前value值对应的长度下的path路径中的点坐标，保存到posArr;对应的切线 保存到tanArr
        measure.getPosTan(value * measure.getLength(), posArr, tanArr);
        float degree = (float) (Math.atan2(tanArr[1], tanArr[0]) * 180 / Math.PI);

        canvas.save();
        canvas.translate(400, 400);//把画布移动到 400 400位置

        //在这个点上 绘制一个小圆（这个小圆将会在 大圆的边线上）
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(posArr[0], posArr[1], 10, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        paint.setColor(Color.RED);

        //通过旋转画布，来实现切线的不停运动
        canvas.rotate(degree);
        canvas.drawLine(0, -200, 100, -200, paint);

        //释放
        canvas.restore();

    }

    @Override
    public void onClick(View v) {
        animator.start();
    }
}
