package test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by SinyaKoizumi on 2015/10/22.
 */
public class ZoomImageView2 extends View {

    /**
     * 图片初始化状态
     */
    public static final int STATUS_INIT = 1;

    /**
     * 图片放大状态
     */
    public static final int STATUS_ZOOM_OUT = 2;

    /**
     * 图片缩小状态
     */
    public static final int STATUS_ZOOM_IN = 3;

    /**
     * 图片拖动状态
     */
    public static final int STATUS_MOVE = 4;

    /**
     * 用于对图片进行移动或者缩放的载体矩阵
     */
    private Matrix matrix = new Matrix();

    /**
     * 需要显示的Bitmap对象
     */
    private Bitmap sourceBitmap;

    /**
     * 当前操作状态
     */
    private int currentStatus;

    /**
     * ZoomImageView控件宽度
     */
    private int width;

    /**
     * ZoomImageView控件高度
     */
    private int height;

    /**
     * 记录两个手指同时放在屏幕上时，中心点的横坐标X
     */
    private float centerPointX;

    /**
     * 记录两个手指同时放在屏幕上时，中心点的纵坐标Y
     */
    private float centerPointY;

    /**
     * 记录当前图片的宽度（图片被缩放时，这个值会一起变动）
     */
    private float currentBitmapWidth;

    /**
     * 记录当前图片的高度（图片被缩放时，这个值会一起变动）
     */
    private float currentBitmapHeight;

    /**
     * 记录上次手指移动的横坐标X
     */
    private float lastMoveX = -1;

    /**
     * 记录上次手指移动的纵坐标Y
     */
    private float lastMoveY = -1;

    /**
     * 记录手指在横坐标X上移动的距离
     */
    private float movedDistanceX;

    /**
     * 记录手指在纵坐标Y上移动的距离
     */
    private float movedDistanceY;

    /**
     * 记录图片在矩阵上的横向偏移值
     */
    private float totalTranslateX;

    /**
     * 记录图片在矩阵上的纵向偏移值
     */
    private float totalTranslateY;

    /**
     * 记录图片在矩阵上的总缩放比例
     */
    private float totalRatio;

    /**
     * 记录图片初始化时候的缩放比例（会根据图片与控件宽高计算出来）
     */
    private float initRatio;

    /**
     * 记录手指移动的距离 产生的缩放比例
     */
    private float scaledRatio;

    /**
     * 记录上次两个手指之间的距离（将使用平方根算出）
     */
    private double lastFingerDis;

    /**
     * 构造函数
     * 第一次生成，将当前的图片操作状态设置成STATUS_INIT
     *
     * @param context
     * @param attrs
     */
    public ZoomImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentStatus = STATUS_INIT;
    }

    public void setImageBitmap(Bitmap bitmap) {
        sourceBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            width = getWidth();
            height = getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    //当出现两个触摸点时，计算两个手指之间的距离
                    lastFingerDis = distanceBetweenFingers(event);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                //如果只有一个触摸点，视为拖动图片
                if (event.getPointerCount() == 1) {
                    float moveX = event.getX();
                    float moveY = event.getY();

                    if (lastMoveX == -1 && lastMoveY == -1) {
                        lastMoveX = moveX;
                        lastMoveY = moveY;
                    }

                    //更改状态，并计算移动距离
                    currentStatus = STATUS_MOVE;
                    movedDistanceX = moveX - lastMoveX;
                    movedDistanceY = moveY - lastMoveY;

                    //图片是否超出边界检查

                    //如果 矩阵移动距离+X轴移动距离 大于0，不可以再移动
                    if (totalTranslateX + movedDistanceX > 0) {
                        movedDistanceX = 0;

                        //如果 控件宽度 - （矩阵移动距离 + X轴移动距离） 大于 图片宽度
                    } else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
                        movedDistanceX = 0;
                    }

                    if (totalTranslateY + movedDistanceY > 0) {
                        movedDistanceY = 0;
                    } else if (height - (totalTranslateY + movedDistanceY) > currentBitmapHeight) {
                        movedDistanceY = 0;
                    }

                    invalidate();
                    lastMoveX = moveX;
                    lastMoveY = moveY;

                    //两个手指在屏幕上移动时 视为缩放状态
                } else if (event.getPointerCount() == 2) {
                    centerPointBetweenFingers(event);
                    double fingerDis = distanceBetweenFingers(event);

                    //如果当前两个手指间距离 大于上一次距离 视为放大
                    if (fingerDis > lastFingerDis) {
                        currentStatus = STATUS_ZOOM_OUT;
                    } else {
                        currentStatus = STATUS_ZOOM_IN;
                    }

                    //缩放倍数检查。最大只允许将图片放大到原来的4倍，最小可以缩小到初始化比例
                    if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < 4 * initRatio) || (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {

                        //当前的手指间距离除以上一次的两指间距离 求得手指距离产生的缩放比例
                        scaledRatio = (float) (fingerDis / lastFingerDis);
                        totalRatio = totalRatio * scaledRatio;

                        if (totalRatio > 4 * initRatio) {
                            totalRatio = 4 * initRatio;
                        } else if (totalRatio < initRatio) {
                            totalRatio = initRatio;
                        }

                        invalidate();
                        lastFingerDis = fingerDis;
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    lastMoveX = -1;
                    lastMoveY = -1;
                }
                break;

            case MotionEvent.ACTION_UP:
                lastMoveX = -1;
                lastMoveY = -1;
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //根据操作状态处理图片绘制刷新
        switch (currentStatus) {
            case STATUS_ZOOM_OUT:
            case STATUS_ZOOM_IN:
                zoom(canvas);
                break;

            case STATUS_MOVE:
                move(canvas);
                break;

            case STATUS_INIT:
                initBitmap(canvas);
                break;

            default:
                canvas.drawBitmap(sourceBitmap, matrix, null);
                break;
        }
    }

    // [+] Methods

    /**
     * 缩放
     *
     * @param canvas
     */
    private void zoom(Canvas canvas) {
        matrix.reset();

        //设置缩放比例
        matrix.postScale(totalRatio, totalRatio);

        //设置移动的变化的宽高
        float scaledWidth = sourceBitmap.getWidth() * totalRatio;
        float scaledHeight = sourceBitmap.getHeight() * totalRatio;
        float translateX = 0f;
        float translateY = 0f;

        //如果当前图片宽度小于屏幕宽度，则按屏幕中心的横坐标X进行水平缩放。
        if (currentBitmapWidth < width) {
            translateX = (width - scaledWidth) / 2f;

            //否则按两指的中心点的横坐标进行水平缩放
        } else {
            translateX = totalTranslateX * scaledRatio + centerPointX * (1 - scaledRatio);
            //边界检查。保证图片缩放后水平方向X轴 不会偏移出屏幕
            if (translateX > 0) {
                translateX = 0;
            } else if (width - translateX > scaledWidth) {
                translateX = width - scaledWidth;
            }
        }

        //如果当前图片高度小于屏幕高度，则按屏幕中心的纵坐标Y进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
        if (currentBitmapHeight < height) {
            translateY = (height - scaledHeight) / 2f;
        } else {
            translateY = totalTranslateY * scaledRatio + centerPointY * (1 - scaledRatio);
            if (translateY > 0) {
                translateY = 0;
            } else if (height - translateY > scaledHeight) {
                translateY = height - scaledHeight;
            }
        }

        //缩放后对图片进行偏移，以保证缩放后的中心点位置不变
        matrix.postTranslate(translateX, translateY);
        totalTranslateX = translateX;
        totalTranslateY = translateY;
        currentBitmapWidth = scaledWidth;
        currentBitmapHeight = scaledHeight;

        canvas.drawBitmap(sourceBitmap, matrix, null);
    }

    /**
     * 拖动
     *
     * @param canvas
     */
    private void move(Canvas canvas) {
        matrix.reset();
        //根据手指移动的距离计算出总的偏移值
        float translateX = totalTranslateX + movedDistanceX;
        float translateY = totalTranslateY + movedDistanceY;

        //按照已有的缩放比例对图片进行缩放
        matrix.postScale(totalRatio, totalRatio);
        //再根据移动距离设置偏移值
        matrix.postTranslate(translateX, translateY);
        totalTranslateX = translateX;
        totalTranslateY = translateY;

        canvas.drawBitmap(sourceBitmap, matrix, null);
    }

    /**
     * 初始化加载
     *
     * @param canvas
     */
    private void initBitmap(Canvas canvas) {
        if (sourceBitmap != null) {
            matrix.reset();

            int bitmapWidth = sourceBitmap.getWidth();
            int bitmapHeight = sourceBitmap.getHeight();

            if (bitmapWidth > width || bitmapHeight > height) {

                if (bitmapWidth - width > bitmapHeight - height) {
                    // 当图片宽度大于屏幕宽度时，将图片等比例压缩，使它可以完全显示出来
                    float ratio = width / (bitmapWidth * 1.0f);
                    matrix.postScale(ratio, ratio);
                    float translateY = (height - (bitmapHeight * ratio)) / 2f;
                    //在纵坐标Y方向上进行偏移，保证图片居中显示
                    matrix.postTranslate(0, translateY);
                    totalTranslateY = translateY;
                    totalRatio = initRatio = ratio;

                } else {
                    // 当图片高度大于屏幕高度时，将图片等比例压缩，使它可以完全显示出来
                    float ratio = height / (bitmapHeight * 1.0f);
                    matrix.postScale(ratio, ratio);
                    float translateX = (width - (bitmapWidth * ratio)) / 2f;
                    //在横坐标X方向上进行偏移，以保证图片居中显示
                    matrix.postTranslate(translateX, 0);
                    totalTranslateX = translateX;
                    totalRatio = initRatio = ratio;
                }

                currentBitmapWidth = bitmapWidth * initRatio;
                currentBitmapHeight = bitmapHeight * initRatio;

                // 当图片的宽高都小于屏幕宽高时，直接让图片居中显示
            } else {
                float translateX = (width - sourceBitmap.getWidth()) /2f;
                float translateY = (height - sourceBitmap.getHeight()) /2f;
                matrix.postTranslate(translateX, translateY);
                totalTranslateX = translateX;
                totalTranslateY = translateY;
                totalRatio = initRatio = 1f;

                currentBitmapWidth = bitmapWidth;
                currentBitmapHeight = bitmapHeight;
            }
            canvas.drawBitmap(sourceBitmap, matrix, null);
        }
    }

    /**
     * 计算两个手指之间的距离
     *
     * @param event
     * @return
     */
    private double distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getX(0) - event.getY(1));
        //平方根获取两个手指之间的距离
        return Math.sqrt(disX * disX + disY * disY);
    }

    /**
     * 计算两个手指之间中心点的坐标XY
     *
     * @param event
     */
    private void centerPointBetweenFingers(MotionEvent event) {
        float xPoint0 = event.getX(0);
        float yPoint0 = event.getY(0);
        float xPoint1 = event.getX(1);
        float yPoint1 = event.getY(1);
        centerPointX = (xPoint0 + xPoint1) / 2;
        centerPointY = (yPoint0 + yPoint1) / 2;
    }

    // [-] Methods
}
