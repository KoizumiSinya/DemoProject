package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import sinya.jp.demo_togglebutton.R;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/17 10:28
 * editor：
 * updateDate：2015/9/17 10:28
 */
public class ToggleButton extends View implements View.OnTouchListener {

    private static final String TAG = "ToggleButton";

    private boolean nowChoose = false;
    private boolean isChangListenerOn = false;
    private boolean onSlip = false;
    private boolean isChecked;

    private float donwX, nowX;

    private onChangedListener listener;

    private Bitmap bg_on, bg_off, bg_image;

    private Rect btn_On, btn_Off;

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initRes(context, attrs);
    }


    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initRes(context, attrs);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (event.getX() > bg_on.getWidth() || event.getY() > bg_on.getHeight()) {
                    return false;
                }

                onSlip = true;
                donwX = event.getX();
                nowX = donwX;
                break;

            case MotionEvent.ACTION_MOVE:
                nowX = event.getX();
                break;

            case MotionEvent.ACTION_CANCEL:
                onSlip = false;
                boolean choose = nowChoose;

                if (nowX >= (bg_on.getWidth() / 2)) {
                    nowX = bg_on.getWidth() - bg_image.getWidth() / 2;
                    nowChoose = true;
                } else {
                    nowX = nowX - bg_image.getWidth() / 2;
                    nowChoose = false;
                }

                //如果设置了监听器，就调用监听器的方法
                if (isChangListenerOn && (choose != nowChoose)) {
                    listener.onChanged(nowChoose);
                }
                break;

            case MotionEvent.ACTION_UP:
                onSlip = false;
                boolean lastChoose = nowChoose;

                if (event.getX() >= (bg_on.getWidth() / 2)) {
                    nowX = bg_on.getWidth() - bg_image.getWidth() / 2;
                    nowChoose = true;
                } else {
                    nowX = nowX - bg_image.getWidth() / 2;
                    nowChoose = false;
                }

                if (isChangListenerOn && (lastChoose != nowChoose)) {
                    listener.onChanged(nowChoose);
                }
                break;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;

        if (nowX < (bg_on.getWidth() / 2)) {
            x = nowX - bg_image.getWidth() / 2;
            canvas.drawBitmap(bg_off, matrix, paint);//绘制关闭时的背景
        } else {
            x = bg_on.getWidth() - bg_image.getWidth() / 2;
            canvas.drawBitmap(bg_on, matrix, paint);//绘制打开时的背景
        }

        //是否是在滑动状态
        if (onSlip) {

            if (nowX >= bg_on.getWidth()) {
                x = bg_on.getWidth() - bg_image.getWidth() / 2;
            } else if (nowX < 0) {
                x = 0;
            } else {
                x = nowX - bg_image.getWidth() / 2;
            }

            //非滑动状态下
        } else {

            if (nowChoose) {
                x = btn_Off.left;
                canvas.drawBitmap(bg_on, matrix, paint);
            } else {
                x = btn_On.left;
                canvas.drawBitmap(bg_off, matrix, paint);
            }
        }

        if (x < 0) {
            x = 0;
        } else if (x > bg_on.getWidth() - bg_image.getWidth()) {
            x = bg_on.getWidth() - bg_image.getWidth();
        }

        canvas.drawBitmap(bg_image, x, 0, paint);
    }

    private void initRes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton);
        int on_resource = typedArray.getResourceId(R.styleable.ToggleButton_on_resource, R.mipmap.bg_switch_on);
        int off_resource = typedArray.getResourceId(R.styleable.ToggleButton_off_resource, R.mipmap.bg_switch_off);
        int btn_resource = typedArray.getResourceId(R.styleable.ToggleButton_btn_resource, R.mipmap.btn_switch);

        initView(on_resource, off_resource, btn_resource);
    }

    private void initView(int on_resource, int off_resource, int btn_resource) {
        bg_on = BitmapFactory.decodeResource(getResources(), on_resource);
        bg_off = BitmapFactory.decodeResource(getResources(), off_resource);
        bg_image = BitmapFactory.decodeResource(getResources(), btn_resource);

        Log.d(TAG, "bg_on=" + bg_on.getWidth());
        Log.d(TAG, "bg_off=" + bg_off.getWidth());
        Log.d(TAG, "slip_btn=" + bg_image.getWidth());

        //关闭的时候绘制 拖动圆点的位置
        btn_On = new Rect(0, 0, bg_image.getWidth(), bg_image.getHeight());

        //打开的时候绘制 拖动圆点的位置
        btn_Off = new Rect(bg_off.getWidth() - bg_image.getWidth(), 0, bg_off.getWidth(), bg_image.getHeight());

        setOnTouchListener(this);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setCheck(boolean isChecked) {
        this.isChecked = isChecked;
        nowChoose = isChecked;
        invalidate();
    }

    public void setOnchangedListener(onChangedListener listener) {
        isChangListenerOn = true;
        this.listener = listener;
    }

    public interface onChangedListener {
        abstract void onChanged(boolean checkState);
    }
}
