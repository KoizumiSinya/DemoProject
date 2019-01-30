package jp.sinya.bezierstudy.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author Koizumi Sinya
 * @date 2017/10/20. 22:23
 * @edithor
 * @date
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF flagPoint;

    public BezierEvaluator(PointF flagPoint) {
        this.flagPoint = flagPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtils.CalculateBezierPointForQuadratic(fraction, startValue, flagPoint, endValue);
    }
}
