package jp.sinya.circleseekbar.demo5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class JogdialView extends View {

    private static final String[] temperatureArr = new String[]{//
            "0°", "37°", "40°", "45°", "50°", "55°", "60°", //
            "65°", "70°", "75°", "80°", "85°", "90°", "95°", "100°",//
            "105°", "110°", "115°", "120°", "125°", "130°"};

    private static final int[] temperatureValueArr = new int[] //
            {0, 37, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130};

    /**
     * 宽
     */
    private int width;
    /**
     * 高
     */
    private int height;

    /**
     * 刻度盘的半径
     */
    private int dialRadius;

    /**
     * 圆弧半径
     */
    private int arcRadius;

    /**
     * 刻度的高度
     */
    private int scaleHeight = dp2px(getContext(), 8);

    private Paint dialPaint;
    private Paint arcPaint;
    private Paint circlePaint;
    private Paint circlePaint2;
    private Paint buttonPaint;
    private Paint valuePaint;

    private int arcWidth;

    private String title = "Content";
    private boolean isEnable = true;

    /**
     * 0~20
     */
    private int value = 0;
    private int minValue = 0;
    private int maxValue = 20;

    /**
     * 步长（多少个格子等于1个value）
     */
    private int angleRate = 25;

    /**
     * 每个格子的跨度
     */
    private float angleDegree = (float) 300 / (maxValue - minValue) / angleRate;

    /**
     * 当前旋转的角度
     */
    private float rotateAngle;
    /**
     * 当前的角度
     */
    private float currentAngle;

    private float center;
    private int centerX, centerY;

    //按钮的图片
//    private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(), R.drawable.oval_temperature);
    private Bitmap buttonImage = getBitmapFromVectorDrawable(getContext(), R.drawable.temperature_bg);

    //按钮的阴影背景
    //private Bitmap buttonImageBg = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_rotate_shadow);

    //抗锯齿
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    private boolean isDown;
    private boolean isMove;


    //数值变化监听器
    private OnValueChangeListener valueChangeListener;

    public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


    public JogdialView(Context context) {
        super(context);
        initPaint();
    }

    public JogdialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public JogdialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("Sinya", "size - " + temperatureValueArr.length);

        width = Math.min(w, h);
        height = Math.min(w, h);

        dialRadius = width / 2 - dp2px(getContext(), 40);
        arcRadius = dialRadius - dp2px(getContext(), 72);

        center = Math.min(width, height) / 2;
        centerX = w / 2;
        centerY = h / 2;
    }

    private void initPaint() {
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true);
        dialPaint.setStrokeWidth(dp2px(getContext(), 2));
        dialPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcWidth = dp2px(getContext(), 24);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setStyle(Paint.Style.STROKE);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(Color.parseColor("#F2534C"));
        circlePaint2 = new Paint();
        circlePaint2.setAntiAlias(true);
        circlePaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint2.setColor(Color.parseColor("#FFFFFF"));

        arcPaint.setStrokeCap(Paint.Cap.ROUND);//圆形笔头
        //arcPaint.setShader(new SweepGradient(centerX, centerX, new int[]{Color.TRANSPARENT, Color.parseColor("#40FFFFFF")}, null));
        arcPaint.setShader(new LinearGradient(centerX - arcRadius - arcWidth / 2, 2 * arcRadius, centerX - arcRadius + 300 + arcWidth, 0,//
                new int[]{Color.parseColor("#1AFFFFFF"), Color.parseColor("#D9FFFFFF")}, //
                null, LinearGradient.TileMode.CLAMP));

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setTextSize(sp2px(getContext(), 60));
        valuePaint.setColor(Color.parseColor("#F2534C"));
        valuePaint.setStyle(Paint.Style.STROKE);

        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawCircle(canvas);
        drawValueText(canvas);
    }


    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        RectF rectF = //
                new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 90, 300, false, arcPaint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        int btnWidth = buttonImage.getWidth();
        int btnHeight = buttonImage.getHeight();

        //int btnBgWidth = buttonImageBg.getWidth();
        //int btnBgHeight = buttonImageBg.getHeight();

        //绘制按钮的背景
        //canvas.drawBitmap(buttonImageBg, (width - btnBgWidth) / 2, (height - btnBgHeight) / 2, buttonPaint);

        Matrix matrix = new Matrix();
        matrix.setTranslate((width - btnWidth) / 2, (height - btnHeight) / 2);
        matrix.postRotate(rotateAngle, width / 2, height / 2);

        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(buttonImage, matrix, buttonPaint);

        //canvas.drawCircle(centerX, centerY + arcRadius, 15, circlePaint);
        //canvas.drawCircle(centerX, centerY + arcRadius, 7, circlePaint2);
    }

    private void drawValueText(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        String temperature = temperatureArr[value];
        float valueTextWidth = valuePaint.measureText(temperature);
        float valueTextHeight = (valuePaint.ascent() + valuePaint.descent()) / 2;

        canvas.drawText(temperature, -valueTextWidth / 2, -valueTextHeight, valuePaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnable) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                float downX = event.getX();
                float downY = event.getY();
                currentAngle = getAngle(downX, downY);
                break;

            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float targetX;
                float targetY;

                downX = targetX = event.getX();
                downY = targetY = event.getY();

                if (checkTouch(downX, downY)) {
                    float angle = getAngle(targetX, targetY);

                    //滑过的角度增量
                    float angleIncreased = angle - currentAngle;
                    //防止越界
                    if (angleIncreased < -300) {
                        angleIncreased = angleIncreased + 360;
                    } else if (angleIncreased > 300) {
                        angleIncreased = angleIncreased - 360;
                    }

                    setIncreaseAngle(angleIncreased);
                    currentAngle = angle;
                    invalidate();

                    if (valueChangeListener != null) {
                        valueChangeListener.changeValue(value, temperatureValueArr[value]);
                    }

                } else {
                    return false;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isDown) {
                    if (isMove) {
                        rotateAngle = (float) ((value - minValue) * angleRate * angleDegree);
                        invalidate();
                        isMove = false;
                    } else {
                        //click
                    }
                    isDown = false;
                }

                break;

            default:
                break;
        }
        return true;
    }

    private boolean checkTouch(float x, float y) {
        double distance = Math.sqrt((x - center) * (x - center) + (y - center) * (y - center));
        return distance <= dialRadius + 40;
    }

    private float getAngle(float targetX, float targetY) {
        float x = targetX - width / 2;
        float y = targetY - height / 2;
        double radian;

        if (x != 0) {
            float tan = Math.abs(y / x);
            if (x > 0) {
                if (y >= 0) {
                    radian = Math.atan(tan);
                } else {
                    radian = 2 * Math.PI - Math.atan(tan);
                }
            } else {
                if (y >= 0) {
                    radian = Math.PI - Math.atan(tan);
                } else {
                    radian = Math.PI + Math.atan(tan);
                }
            }
        } else {
            if (y > 0) {
                radian = Math.PI / 2;
            } else {
                radian = -Math.PI / 2;
            }
        }

        // 弧度 × 180 / π = 角度
        return (float) ((radian * 180) / Math.PI);
    }

    private void setIncreaseAngle(float angle) {
        rotateAngle += angle;
        if (rotateAngle < 0) {
            rotateAngle = 0;
        } else if (rotateAngle > 300) {
            rotateAngle = 300;
        }

        // 加上0.5是为了取整时四舍五入
        value = (int) ((rotateAngle / angleDegree) / angleRate + 0.5) + minValue;
    }

    public void setAngelRate(int angleRate) {
        this.angleRate = angleRate;
    }

    public void setValue(int value) {
        setValue(minValue, maxValue, value);
    }

    public void setValueByTemperature(int temperature) {
        int value = 0;
        for (int i = 0; i < temperatureValueArr.length; i++) {
            if (temperature == temperatureValueArr[i]) {
                value = i;
                break;
            }
        }
        setValue(minValue, maxValue, value);
    }

    public void setValue(int minValue, int maxValue, int value) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        if (value < minValue) {
            this.value = minValue;
        } else {
            this.value = value;
        }

        //算旋转角度
        rotateAngle = (float) ((value - minValue) * angleRate * angleDegree);
        //计算每格的角度
        angleDegree = (float) 300 / (maxValue - minValue) / angleRate;
        invalidate();

        if (valueChangeListener != null) {
            valueChangeListener.changeValue(value, temperatureValueArr[value]);
        }
    }

    public void setJogDialEnable(boolean enable) {
        isEnable = enable;
    }

    public interface OnValueChangeListener {
        void changeValue(int progress, int preTemperature);
    }

    private int dp2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
