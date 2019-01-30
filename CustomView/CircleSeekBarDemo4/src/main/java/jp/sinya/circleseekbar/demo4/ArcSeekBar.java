package jp.sinya.circleseekbar.demo4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;


/**
 * Created by zmj
 * <p>
 * 这是带刻度线的可滑动进度半圆效果
 */

public class ArcSeekBar extends View {

    //圆环的宽度
    private float ringWidth;
    //底部的圆弧颜色
    private int ringBgColor;
    //滑动的圆弧颜色
    private int slideRingColor;
    //同心圆的外圆半径
    private int radius;
    //中间字的颜色
    private int wordColor;
    //中间字的大小
    private int wordSize;

    //最大进度范围
    private int maxProgress;
    //最小进度范围
    private int minProgress;
    //当前进度，通过进度的百分比求出实际显示数值
    private int progress;
    //实际显示的数值
    private double realShowProgress;
    //每次要增加减少的数值
    private double addOrReduce = 1;
    //开始滑动的起始位置度数，顶部270 右侧0 底部90 左侧180
    private int beginLocation = 210;
    //当前可滑动区域的范围
    private int slideAbleLocation;

    //圆环上的圆圈
    private Bitmap mDragBitmap;
    //圆环的宽
    private int bitmapWidth;
    //圆环的高度
    private int bitmapHeight;

    //外侧刻度线的数量
    private int scaleLineCount;
    //外侧正常刻度线的长度
    private int scaleLineLength;
    //线条的宽度
    private int scaleLineWidth;
    //需要特殊处理的刻度线长度，例如正方位上的刻度或者当前刻度
    private int specialScaleLineLength;
    //刻度结束的角度
    private float sweepAngle = 120;
    //未选择的刻度线颜色
    private int scaleLineNormalColor;
    //滑动后的刻度线颜色
    private int specialScaleColor;
    //刻度线距离里面的环的距离
    private int scaleToRingSpace;

    //画底部背景环的画笔
    private Paint ringBgPaint;
    //画上面圆弧的画笔
    private Paint slideRingPaint;
    //圆环上的小圆圈
    private Paint mBitmapPaint;
    //写当前progress的画笔
    private Paint wordPaint;
    //画普通背景刻度线的画笔
    private Paint scalePaint;
    //这是画滑动后的刻度颜色画笔
    private Paint specialScalePaint;

    //显示中间显示文字(当前为progress)所占的区域
    private Rect rect;

    //这是保留小数的使用类
    private DecimalFormat df;

    private int centerX;
    private int centerY;
    private Shader linearGradient;
    private int[] colors = new int[]{Color.TRANSPARENT, Color.parseColor("#99FFFFFF")};
    private boolean isTouchUp;


    public ArcSeekBar(Context context) {
        this(context, null);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint(context);
    }

    /**
     * 定位到指定刻度
     */
    public void setProgress(double p) {
        if (p <= maxProgress && p >= minProgress) {
            realShowProgress = p;
            progress = (int) ((realShowProgress - minProgress) * 100.0 / (maxProgress - minProgress));
        }
        invalidate();
    }

    /**
     * 点击增加progress
     */
    public void addProgress() {
        if (realShowProgress < maxProgress) {
            synchronized (ArcSeekBar.class) {
                realShowProgress = Double.parseDouble(df.format(realShowProgress + addOrReduce));
                progress = (int) ((realShowProgress - minProgress) * 100.0 / (maxProgress - minProgress));
            }
            invalidate();
        }
    }

    /**
     * 点击减少progress
     */
    public void reduceProgress() {
        if (realShowProgress > minProgress) {
            synchronized (ArcSeekBar.class) {
                realShowProgress = Double.parseDouble(df.format(realShowProgress - addOrReduce));
                progress = (int) ((realShowProgress - minProgress) * 100.0 / (maxProgress - minProgress));
            }
            invalidate();
        }
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        slideAbleLocation = CommentUtil.dip2px(context, 30);

        bitmapWidth = CommentUtil.dip2px(context, 30);
        bitmapHeight = CommentUtil.dip2px(context, 30);

        mDragBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ring_dot);
        mDragBitmap = CommentUtil.conversionBitmap(mDragBitmap, bitmapWidth, bitmapHeight);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyHalfCircularSildeView, defStyleAttr, 0);
        ringWidth = array.getInt(R.styleable.MyHalfCircularSildeView_ringWidth, CommentUtil.dip2px(context, 10));
        slideRingColor = array.getInt(R.styleable.MyHalfCircularSildeView_slideRingCorlor, 0xFF6a6aff);
        ringBgColor = array.getInt(R.styleable.MyHalfCircularSildeView_ringBgCorlor, 0xFFbebebe);
        radius = array.getInt(R.styleable.MyHalfCircularSildeView_radius, CommentUtil.dip2px(context, 100));
        wordColor = array.getInt(R.styleable.MyHalfCircularSildeView_wordCorlor, Color.WHITE);
        wordSize = array.getInt(R.styleable.MyHalfCircularSildeView_wordSize, 40);
        maxProgress = array.getInt(R.styleable.MyHalfCircularSildeView_maxProgress, 10);
        minProgress = array.getInt(R.styleable.MyHalfCircularSildeView_minProgress, 0);
        progress = array.getInt(R.styleable.MyHalfCircularSildeView_progress, 0);

        //下面是刻度线的属性
        scaleLineCount = array.getInt(R.styleable.MyHalfCircularSildeView_scaleLineCount, 100);
        scaleLineLength = array.getInt(R.styleable.MyHalfCircularSildeView_scaleLineLength, CommentUtil.dip2px(context, 10));
        specialScaleLineLength = array.getInt(R.styleable.MyHalfCircularSildeView_specialScaleLineLength, CommentUtil.dip2px(context, 15));
        scaleToRingSpace = array.getInt(R.styleable.MyHalfCircularSildeView_scaleToRingSpace, CommentUtil.dip2px(context, 10));
        scaleLineNormalColor = array.getInt(R.styleable.MyHalfCircularSildeView_scaleLineNormalCorlor, 0xFFbebebe);
        specialScaleColor = array.getInt(R.styleable.MyHalfCircularSildeView_specialScaleCorlor, 0xFF6a6aff);
        scaleLineWidth = array.getInt(R.styleable.MyHalfCircularSildeView_scaleLineWidth, CommentUtil.dip2px(context, 2));

        //记得使用完销毁
        array.recycle();

        //保留1位小数
        df = new DecimalFormat("#.0");
        realShowProgress = getShowProgress(progress);
    }

    private void initPaint(Context context) {
        //画背景圆弧的画笔初始化
        ringBgPaint = new Paint();
        //ringBgPaint.setColor(ringBgColor);
        ringBgPaint.setAntiAlias(true);// 抗锯齿效果
        ringBgPaint.setStyle(Paint.Style.STROKE);//设置空心
        ringBgPaint.setStrokeWidth(ringWidth);//线宽度，即环宽
        ringBgPaint.setStrokeCap(Paint.Cap.ROUND);//圆形笔头

        //画滑动圆弧的画笔初始化
        slideRingPaint = new Paint();
        slideRingPaint.setAntiAlias(true);
        slideRingPaint.setStyle(Paint.Style.STROKE);
        slideRingPaint.setColor(slideRingColor);
        slideRingPaint.setStrokeWidth(ringWidth);
        slideRingPaint.setStrokeCap(Paint.Cap.ROUND);//圆形笔头

        //写中间文字的画笔初始化
        wordPaint = new Paint();
        wordPaint.setColor(wordColor);
        wordPaint.setTextSize(CommentUtil.sp2px(context, wordSize));
        rect = new Rect();
        String str = progress + " C";
        wordPaint.getTextBounds(str, 0, str.length(), rect);

        //设置圆环上圆圈的画笔初始化
        mBitmapPaint = new Paint();
        mBitmapPaint.setDither(true);//设置防抖动
        mBitmapPaint.setFilterBitmap(true);//对Bitmap进行滤波处理
        mBitmapPaint.setAntiAlias(true);//设置抗锯齿

        //这是画外面刻度线的画笔
        scalePaint = new Paint();
        scalePaint.setColor(scaleLineNormalColor);
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(scaleLineWidth);

        //这是画滑动后的刻度线的画笔
        specialScalePaint = new Paint();
        specialScalePaint.setColor(specialScaleColor);
        specialScalePaint.setAntiAlias(true);
        specialScalePaint.setStyle(Paint.Style.STROKE);
        specialScalePaint.setStrokeWidth(scaleLineWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                widthSize = 2 * radius + getPaddingLeft() + getPaddingRight() + 2 * (specialScaleLineLength + scaleToRingSpace);
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        switch (heightModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                //当控件自适应时候，尺寸=半径+左右边距
                heightSize = radius + getPaddingTop() + getPaddingBottom() + bitmapHeight / 4 + specialScaleLineLength + scaleToRingSpace;
                break;
            //当宽度全屏或者固定尺寸时候
            case MeasureSpec.EXACTLY:
                break;
        }

        setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        linearGradient = new LinearGradient(centerX - radius - ringWidth / 2, 0, centerX - radius + sweepAngle + ringWidth, 0, colors, null, Shader.TileMode.CLAMP);
        ringBgPaint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景圆环
        paintDefaultArc(canvas);

        //画滑动圆弧
        //paintSelectedArc(canvas);

        //画上滑动图标
        paintThumb(canvas);

        //展示进度
        paintCenterText(canvas);
        //画背景刻度
        //paintScale(canvas);

        paintMinText(canvas);
        paintMaxText(canvas);
    }

    private void paintDefaultArc(Canvas canvas) {
        canvas.drawArc(new RectF(ringWidth / 2 + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, ringWidth / 2 + getPaddingTop() + specialScaleLineLength + scaleToRingSpace, 2 *
                radius - ringWidth / 2 + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, 2 * radius - ringWidth / 2 + getPaddingTop() + specialScaleLineLength + scaleToRingSpace),
                beginLocation, sweepAngle, false, ringBgPaint);
    }

    private void paintSelectedArc(Canvas canvas) {
        canvas.drawArc(new RectF(ringWidth / 2 + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, ringWidth / 2 + getPaddingTop() + specialScaleLineLength + scaleToRingSpace, 2 *
                radius - ringWidth / 2 + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, 2 * radius - ringWidth / 2 + getPaddingTop() + specialScaleLineLength + scaleToRingSpace),
                beginLocation, progress * sweepAngle / maxProgress, false, slideRingPaint);
    }

    private void paintThumb(Canvas canvas) {
        PointF progressPoint = CommentUtil.calcArcEndPointXY(radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, radius + getPaddingTop() + specialScaleLineLength +
                scaleToRingSpace, radius - ringWidth / 2, progress * sweepAngle / maxProgress, beginLocation);
        int left = (int) progressPoint.x - mDragBitmap.getWidth() / 2;
        int top = (int) progressPoint.y - mDragBitmap.getHeight() / 2;
        canvas.drawBitmap(mDragBitmap, left, top, mBitmapPaint);
    }

    private void paintCenterText(Canvas canvas) {
        if (realShowProgress == 0) {
            realShowProgress = 1;
        }
        String value = (int) Math.floor(4) + "";//整数
        wordSize = 48;
        wordPaint.setTextSize(CommentUtil.sp2px(getContext(), wordSize));
        wordPaint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, centerX - rect.width() / 2, radius + getPaddingTop() + specialScaleLineLength + scaleToRingSpace, wordPaint);
    }

    private void paintMinText(Canvas canvas) {
        String value = "1";
        wordSize = 18;
        wordPaint.setTextSize(CommentUtil.sp2px(getContext(), wordSize));
        wordPaint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, centerX - radius + rect.width() / 2, radius + getPaddingTop() + scaleToRingSpace, wordPaint);
    }

    private void paintMaxText(Canvas canvas) {
        String value = "4";
        wordSize = 18;
        wordPaint.setTextSize(CommentUtil.sp2px(getContext(), wordSize));
        wordPaint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, centerX + radius - rect.width(), radius + getPaddingTop() + scaleToRingSpace, wordPaint);
    }

    /**
     * 画背景刻度
     */
    private void paintScale(Canvas canvas) {
        canvas.save();
        //将坐标系移到圆中心
        canvas.translate(radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace, radius + getPaddingTop() + specialScaleLineLength + scaleToRingSpace);
        //旋转坐标系
        canvas.rotate(90);
        for (int i = 0; i < scaleLineCount + 1; i++) {
            //刻度线的实际展示长度
            int scaleLine = scaleLineLength;
            //正方位的线比较长
            if (i == 0 || i == scaleLineCount / 2 || i == scaleLineCount) {
                scaleLine = specialScaleLineLength;
            }
            //画已经滑动过的刻度线,因为实际刻度数量都是按着100个来转换的
            if (i * (100 / scaleLineCount) <= progress) {
                canvas.drawLine(0, radius + scaleToRingSpace, 0, radius + scaleToRingSpace + scaleLine, specialScalePaint);
            }
            //画未滑动到的刻度线
            else {
                canvas.drawLine(0, radius + scaleToRingSpace, 0, radius + scaleToRingSpace + scaleLine, scalePaint);
            }
            canvas.rotate(sweepAngle / (scaleLineCount * 1f));
        }
        //操作完成后恢复状态
        canvas.restore();
    }

    @Override
    public synchronized boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isOnRing(x, y) && y <= radius + getPaddingTop() + specialScaleLineLength + scaleToRingSpace) {
                    updateProgress(x, y);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (y <= radius + getPaddingTop() + specialScaleLineLength + scaleToRingSpace) {
                    updateProgress(x, y);
                }
                return true;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据当前点的位置求角度，再转换成当前进度
     */
    private void updateProgress(int eventX, int eventY) {
        double degree = 180 * Math.atan2(eventY - (radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace), //
                eventX - (radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace)) / Math.PI;
        if (degree < -150 && degree > -160) {
            progress = 0;
        } else if (degree >= -150 && degree <= -30) {
            progress = (int) ((degree + 150.0) / 1.3333);
        } else if (degree > -30 && degree < -20) {
            progress = 90;
        }
        realShowProgress = progress / 10 + 1;
        invalidate();
    }


    private void touchUp() {
        if (realShowProgress == 1) {
            progress = 0;
        } else if (realShowProgress == 2) {
            progress = 15;
        } else if (realShowProgress == 3) {
            progress = 25;
        } else if (realShowProgress == 4) {
            progress = 35;
        } else if (realShowProgress == 5) {
            progress = 45;
        } else if (realShowProgress == 6) {
            progress = 55;
        } else if (realShowProgress == 7) {
            progress = 65;
        } else if (realShowProgress == 8) {
            progress = 75;
        } else if (realShowProgress == 9) {
            progress = 85;
        } else if (realShowProgress == 10) {
            progress = 90;
        }
        invalidate();
    }

    /**
     * 判断当前触摸屏幕的位置是否位于咱们定的可滑动区域内
     */
    private boolean isOnRing(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - (radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace), 2) //
                + Math.pow(eventY - (radius + getPaddingLeft() + specialScaleLineLength + scaleToRingSpace), 2));
        if (distance < (2 * radius + getPaddingLeft() + getPaddingRight() + 2 * (specialScaleLineLength + scaleToRingSpace)) && distance > radius - slideAbleLocation) {
            result = true;
        }
        return result;
    }

    /**
     * 根据progress，再求出如果首位不是0-100的时候的数字
     */
    private double getShowProgress(int progress) {
        return Double.parseDouble(df.format((maxProgress - minProgress) / 90.0 * progress + minProgress));
    }

}
