package jp.sinya.knobview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import jp.sinya.knobview.R;
import jp.sinya.utils.ScreenUtils;

/**
 * @author Koizumi Sinya
 * @date 2018/04/12. 22:17
 * @edithor
 * @date
 */
public class KnobView1 extends View {

    private int width;
    private int height;
    private float center;

    private int dialRadius;

    private Paint markPaint;
    private Paint markSelectedPaint;

    private int markColor;
    private float markWidth;
    private float markLength;

    private int markSelectedColor;
    private float markSelectedWidth;
    private float markSelectedLength;

    private int arcRadius;
    private int arcColor;
    private float arcWidth;
    private Paint arcPaint;

    private String title;

    private int titleTextSize;
    private int titleTextColor;
    private Paint titleTextPaint;
    private int centerTextSize;

    private int centerTextColor;
    private Paint centerTextPaint;
    private int value;

    private boolean isStress;
    private int stressStep;
    private int angleStep;

    private int minValue;
    private int maxValue;
    private float minDegree;

    private float maxDegree;

    private float angleDegree;

    private float angleRotate;
    private float currentAngle;

    private Bitmap knobImg;
    private Bitmap knobImgBg;
    private Paint knobPaint;

    private boolean isDown;
    private boolean isMove;

    private OnValueChangeListener valueChangeListener;
    private OnClickListener clickListener;


    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    public KnobView1(Context context) {
        super(context);
        initAttrs(null);
        initPaints();
    }

    public KnobView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaints();
    }

    public KnobView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaints();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = Math.min(w, h);
        height = Math.min(w, h);

        dialRadius = width / 2 - ScreenUtils.dp2px(getContext(), 20);
        arcRadius = width / 2 - ScreenUtils.dp2px(getContext(), 40);

        center = Math.min(width, height) / 2;
    }

    private void initAttrs(AttributeSet attributeSet) {

        if (attributeSet == null) {
            markWidth = ScreenUtils.dp2px(getContext(), 2);
            markLength = ScreenUtils.dp2px(getContext(), 10);
            markColor = Color.parseColor("#3CB7EA");

            markSelectedWidth = ScreenUtils.dp2px(getContext(), 4);
            markSelectedLength = ScreenUtils.dp2px(getContext(), 15);
            markSelectedColor = Color.parseColor("#E37364");

            arcWidth = ScreenUtils.dp2px(getContext(), 2);
            arcColor = Color.parseColor("#3CB7EA");

            title = "";
            titleTextSize = ScreenUtils.sp2px(getContext(), 16);
            titleTextColor = Color.parseColor("#3B434E");

            centerTextSize = ScreenUtils.sp2px(getContext(), 30);
            centerTextColor = Color.parseColor("#E27A3F");

            knobImg = BitmapFactory.decodeResource(getResources(), R.mipmap.knob3);
            knobImgBg = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_rotate_shadow);


        } else {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.KnobView1);

            markWidth = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_mark_width, ScreenUtils.dp2px(getContext(), 2));
            markLength = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_mark_length, ScreenUtils.dp2px(getContext(), 10));
            markColor = typedArray.getColor(R.styleable.KnobView1_knob_mark_color, Color.parseColor("#3CB7EA"));

            markSelectedWidth = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_mark_selected_width, ScreenUtils.dp2px(getContext(), 4));
            markSelectedLength = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_mark_selected_length, ScreenUtils.dp2px(getContext(), 15));
            markSelectedColor = typedArray.getColor(R.styleable.KnobView1_knob_mark_selected_color, Color.parseColor("#E37364"));

            arcWidth = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_arc_width, ScreenUtils.dp2px(getContext(), 2));
            arcColor = typedArray.getColor(R.styleable.KnobView1_knob_arc_color, Color.parseColor("#3CB7EA"));

            title = typedArray.getString(R.styleable.KnobView1_knob_title_text);
            titleTextSize = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_title_text_size, ScreenUtils.sp2px(getContext(), 16));
            titleTextColor = typedArray.getColor(R.styleable.KnobView1_knob_title_text_size, Color.parseColor("#3B434E"));

            centerTextSize = typedArray.getDimensionPixelSize(R.styleable.KnobView1_knob_center_text_size, ScreenUtils.sp2px(getContext(), 30));
            centerTextColor = typedArray.getColor(R.styleable.KnobView1_knob_title_text_color, Color.parseColor("#E4A07E"));

            isStress = typedArray.getBoolean(R.styleable.KnobView1_knob_stress, false);
            stressStep = typedArray.getInteger(R.styleable.KnobView1_knob_stress_step, 0);
            angleStep = typedArray.getInteger(R.styleable.KnobView1_knob_angle_step, 1);

            knobImg = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.KnobView1_knob_img, R.mipmap.knob3));
            knobImgBg = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.KnobView1_knob_img_bg, R.mipmap.btn_rotate_shadow));

            minDegree = typedArray.getFloat(R.styleable.KnobView1_knob_min_degree, -135f);
            maxDegree = typedArray.getFloat(R.styleable.KnobView1_knob_max_degree, 45f);

            minValue = typedArray.getInteger(R.styleable.KnobView1_knob_min_value, 0);
            maxValue = typedArray.getInteger(R.styleable.KnobView1_knob_max_value, 10);

            value = typedArray.getInteger(R.styleable.KnobView1_knob_value, 0);

            typedArray.recycle();
        }
        angleDegree = (float) 270 / (maxValue - minValue) / angleStep;
    }

    private void initPaints() {
        markPaint = new Paint();
        markPaint.setAntiAlias(true);
        markPaint.setStrokeWidth(markWidth);
        markPaint.setColor(markColor);
        markPaint.setStyle(Paint.Style.STROKE);

        markSelectedPaint = new Paint();
        markSelectedPaint.setAntiAlias(true);
        markSelectedPaint.setStrokeWidth(markSelectedWidth);
        markSelectedPaint.setColor(markSelectedColor);
        markSelectedPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(arcColor);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setStyle(Paint.Style.STROKE);

        titleTextPaint = new Paint();
        titleTextPaint.setAntiAlias(true);
        titleTextPaint.setTextSize(titleTextSize);
        titleTextPaint.setColor(titleTextColor);
        titleTextPaint.setStyle(Paint.Style.STROKE);

        centerTextPaint = new Paint();
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setTextSize(centerTextSize);
        centerTextPaint.setColor(centerTextColor);
        centerTextPaint.setStyle(Paint.Style.STROKE);

        knobPaint = new Paint();
        knobPaint.setAntiAlias(true);

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMark(canvas);
    }

    private void drawMark(Canvas canvas) {
        canvas.save();
        canvas.drawColor(Color.GRAY);
        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.rotate(minDegree);
        for (int i = 0; i < angleStep * (maxValue - minValue); i++) {
            canvas.drawLine(0, -dialRadius + markLength, 0, -dialRadius + markLength * 2, markPaint);
            canvas.rotate(angleDegree);
        }

//        canvas.rotate(90);
//        //绘制有value值部分的刻度
//        for (int i = 0; i < angleStep * (value - minValue); i++) {
//            canvas.drawLine(0, -dialRadius, 0, -dialRadius + markLength * 2, markSelectedPaint);
//            canvas.rotate(angleDegree);
//        }
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public interface OnValueChangeListener {
        void changed(int value);
    }

    public interface OnClickListener {
        void click();
    }

    public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
