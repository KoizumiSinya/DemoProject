package jp.sinya.bezierstudy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import jp.sinya.bezierstudy.util.BezierEvaluator;

/**
 * @author Koizumi Sinya
 * @date 2017/10/20. 22:07
 * @edithor
 * @date
 */
public class ShopCardBezierView extends View implements View.OnClickListener {

    private int startX;
    private int startY;

    private int endX;
    private int endY;

    private int flagX;
    private int flagY;

    private int moveX;
    private int moveY;

    private Paint paint;
    private Path path;

    public ShopCardBezierView(Context context) {
        super(context);
    }

    public ShopCardBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        startX = 100;
        startY = 300;
        endX = 800;
        endY = 1200;

        flagX = 350;
        flagY = 0;

        moveX = startX;
        moveY = startY;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();

        setOnClickListener(this);
    }

    public ShopCardBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

        //path.moveTo(startX, startY);
        //path.quadTo(flagX, flagY, endX, endY);
        //canvas.drawPath(path, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startX, startY, 20, paint);
        canvas.drawCircle(endX, endY, 20, paint);

        if (!((moveX == startX && moveY == startY) || (moveX == endX && moveY == endY))) {
            paint.setColor(Color.BLACK);
            canvas.drawCircle(moveX, moveY, 25, paint);
        }
    }

    @Override
    public void onClick(View v) {
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(flagX, flagY));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(startX, startY), new PointF(endX, endY));
        animator.setDuration(1500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                moveX = (int) pointF.x;
                moveY = (int) pointF.y;
                invalidate();
            }
        });
        animator.start();
    }
}
