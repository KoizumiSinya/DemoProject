package jp.sinya.test.arcbar.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * @author KoizumiSinya
 * @date 2016/06/06.
 */
public class AutoChangeTextView extends TextView {

    //动画时长 ms
    private int duration = 1500;
    private float number;

    public AutoChangeTextView(Context context) {
        super(context);
    }

    public AutoChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    public void showNumberWithAnimation(float number, int moli) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, number);
        duration = moli;
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        setText(decimalFormat.format(number) + "%");
    }
}
