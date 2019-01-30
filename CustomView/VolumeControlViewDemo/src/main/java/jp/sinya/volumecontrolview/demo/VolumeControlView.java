package jp.sinya.volumecontrolview.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import jp.sinya.utils.ScreenUtils;

public class VolumeControlView extends View {

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
    private int scaleHeight = ScreenUtils.dp2px(getContext(), 5);

    private Paint dialPaint;
    private Paint arcPaint;
    private Paint titlePaint;
    private Paint flagPaint;
    private Paint buttonPaint;
    private Paint valuePaint;

    private String title = "Content";

    private int value = 0;
    private int minValue = 0;
    private int maxValue = 20;

    /**
     * 步长（多少个格子等于1个value）
     */
    private int angleRate = 18;

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

    //按钮的图片
    private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(), R.mipmap.temperature);
    //按钮的阴影背景
    private Bitmap buttonImageBg = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_rotate_shadow);

    //抗锯齿
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    private boolean isDown;
    private boolean isMove;

    //数值变化监听器
    private OnValueChangeListener valueChangeListener;

    public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public VolumeControlView(Context context) {
        super(context);
        initPaint();
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("Sinya", "size - " + temperatureValueArr.length);
        width = Math.min(w, h);
        height = Math.min(w, h);
        dialRadius = width / 2 - ScreenUtils.dp2px(getContext(), 43);
        arcRadius = dialRadius - ScreenUtils.dp2px(getContext(), 20);

        center = Math.min(width, height) / 2;
    }

    private void initPaint() {
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true);
        dialPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(), 2));
        dialPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.parseColor("#3CB7EA"));
        arcPaint.setStrokeWidth(ScreenUtils.dp2px(getContext(), 2));
        arcPaint.setStyle(Paint.Style.STROKE);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(ScreenUtils.sp2px(getContext(), 15));
        titlePaint.setColor(Color.parseColor("#3B434E"));
        titlePaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setTextSize(ScreenUtils.sp2px(getContext(), 25));
        valuePaint.setColor(Color.parseColor("#FFFFFF"));
        valuePaint.setStyle(Paint.Style.STROKE);

        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        flagPaint = new Paint();
        flagPaint.setAntiAlias(true);
        flagPaint.setTextSize(ScreenUtils.sp2px(getContext(), 60));
        flagPaint.setColor(Color.parseColor("#E27A3F"));
        flagPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        //drawArc(canvas);
        //drawText(canvas);
        drawButton(canvas);
        drawValueText(canvas);
    }

    /**
     * 绘制刻度盘
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.rotate(-180);

        //绘制全部的刻度线
        dialPaint.setColor(Color.parseColor("#999999"));
        for (int i = 0; i < angleRate * (maxValue - minValue); i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(angleDegree);
        }

        canvas.rotate(60);

        //绘制显示值的刻度（被渲染成另外的颜色）
        dialPaint.setColor(Color.parseColor("#333333"));
        for (int i = 0; i < angleRate * (value - minValue); i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(angleDegree);
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(270);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 300, false, arcPaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();

        float titleWidth = titlePaint.measureText(title);
        canvas.drawText(title, (width - titleWidth) / 2, dialRadius * 2 + ScreenUtils.dp2px(getContext(), 15), titlePaint);

        //设置最小标识文字
        String minFlag = minValue < 10 ? minValue + "" : minValue + "";
        float valueFlagWidth = titlePaint.measureText(maxValue + "");
        canvas.rotate(55, width / 2, height / 2);
        canvas.drawText(minFlag, (width - valueFlagWidth) / 2, height + ScreenUtils.dp2px(getContext(), 5), valuePaint);

        //设置最大标识文字
        canvas.rotate(-105, width / 2, height / 2);
        canvas.drawText(maxValue + "", (width - valueFlagWidth) / 2, height + ScreenUtils.dp2px(getContext(), 5), valuePaint);
        canvas.restore();
    }

    private void drawButton(Canvas canvas) {
        int btnWidth = buttonImage.getWidth();
        int btnHeight = buttonImage.getHeight();

        int btnBgWidth = buttonImageBg.getWidth();
        int btnBgHeight = buttonImageBg.getHeight();

        //绘制按钮的背景
        //canvas.drawBitmap(buttonImageBg, (width - btnBgWidth) / 2, (height - btnBgHeight) / 2, buttonPaint);

        Matrix matrix = new Matrix();
        matrix.setTranslate((width - btnWidth) / 2, (height - btnHeight) / 2);
        matrix.postRotate(rotateAngle, width / 2, height / 2);

        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(buttonImage, matrix, buttonPaint);
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

                        if (valueChangeListener != null) {
                            valueChangeListener.changeValue(temperatureValueArr[value]);
                        }
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

    public void setValue(int targetValue) {
        setValue(minValue, maxValue, targetValue);
    }

    public void setValue(int minValue, int maxValue, int targetValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        if (targetValue < minValue) {
            this.value = minValue;
        } else {
            this.value = targetValue;
        }

        //算旋转角度
        rotateAngle = (float) ((targetValue - minValue) * angleRate * angleDegree);
        //计算每格的角度
        angleDegree = (float) 300 / (maxValue - minValue) / angleRate;
        invalidate();
    }


    interface OnValueChangeListener {
        void changeValue(int value);
    }

    interface OnClickListener {
        void onClick(int value);
    }


}
