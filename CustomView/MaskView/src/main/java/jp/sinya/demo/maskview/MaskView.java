package jp.sinya.demo.maskview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sinya
 * @date 2018/07/25. 14:10
 * @edithor
 * @date
 */
public class MaskView extends View {

    private static final PorterDuffXfermode X_FER_MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private int roundRadius = dp2px(10);
    private int margin = dp2px(5);

    private MaskParam maskParam;

    private Paint mPaint;

    /**
     * 需要高亮设置的目标控件
     */
    private View mTargetView;

    private Bitmap mBitmap;
    /**
     * 点击回调
     */
    private OnClickListener mClickListener;
    /**
     * 可点击的区域
     */
    private Rect mClickRect = new Rect();
    private int[] rootViewLocation = new int[2];
    private int[] targetViewLocation = new int[2];
    /**
     * 是否在点击区域按下
     */
    private boolean mDownInClickRect;

    public MaskView(Context context, MaskParam maskParam) {
        super(context);
        this.maskParam = maskParam;
        setParams();
    }

    private void setParams() {
        //防锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //防抖动
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        setFilterTouchesWhenObscured(false);
    }

    public void showMask(final View targetView, OnClickListener listener) {
        mTargetView = targetView;
        mClickListener = listener;
        targetView.post(new Runnable() {
            @Override
            public void run() {

                //获取目标控件的RootView，实质上就是DecorView，它属于FrameLayout
                final View targetViewRoot = targetView.getRootView();

                rootViewLocation = new int[2];
                targetViewLocation = new int[2];
                targetViewRoot.getLocationInWindow(rootViewLocation);
                targetView.getLocationInWindow(targetViewLocation);

                if (maskParam.getTypeMask() == 0) {
                    drawCircle(targetViewRoot, targetView);
                } else {
                    drawRound(targetViewRoot, targetView);
                }
                if (targetViewRoot instanceof ViewGroup) {
                    ((ViewGroup) targetViewRoot).addView(MaskView.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
            }
        });
    }

    public void showMaskCircle(final View targetView, final String tip, OnClickListener listener) {
        mTargetView = targetView;
        mClickListener = listener;

        targetView.post(new Runnable() {
            @Override
            public void run() {

                //获取目标控件的RootView，实质上就是DecorView，它属于FrameLayout
                final View targetViewRoot = targetView.getRootView();

                drawCircle(targetViewRoot, targetView);

                if (targetViewRoot instanceof ViewGroup) {
                    ((ViewGroup) targetViewRoot).addView(MaskView.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
            }
        });
    }

    public void showMaskRound(final View targetView, final String tip, OnClickListener listener) {
        mTargetView = targetView;
        mClickListener = listener;

        targetView.post(new Runnable() {
            @Override
            public void run() {

                final View targetViewRoot = targetView.getRootView();

                drawRound(targetViewRoot, targetView);

                if (targetViewRoot instanceof ViewGroup) {
                    ((ViewGroup) targetViewRoot).addView(MaskView.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
            }
        });
    }

    private void drawCircle(View targetViewRoot, View targetView) {
        final int viewHeight = targetView.getHeight();
        final int viewWidth = targetView.getWidth();
        final int halfHeight = viewHeight / 2;

        int circleRadius = Math.max(viewHeight, viewWidth) / 2 + margin;
        int targetViewCenterX = targetViewLocation[0] - rootViewLocation[0] + viewWidth / 2;// 获取圆心x坐标
        int targetViewCenterY = targetViewLocation[1] - rootViewLocation[1] + halfHeight; // 获取圆心y坐标

        // 可点击区域为圆心按钮相交的近似矩形
        mClickRect.set(targetViewCenterX - circleRadius, targetViewCenterY - halfHeight, targetViewCenterX + circleRadius, targetViewCenterY + halfHeight);

        //mTipX = (targetViewRoot.getWidth() - mPaint.measureText(mTip)) / 2; //提示文字的横坐标，居中即可
        //mTipY = targetViewLocation[1] + circleRadius * 2; // 提示文字的纵坐标，只需要在圆的下方，这里设为view的纵坐标加上直径

        mBitmap = Bitmap.createBitmap(targetViewRoot.getWidth(), targetViewRoot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(maskParam.getBgColor());
        mPaint.setXfermode(X_FER_MODE);
        canvas.drawCircle(targetViewCenterX, targetViewCenterY, circleRadius, mPaint);

        drawTips(canvas);
    }

    private void drawRound(View targetViewRoot, View targetView) {

        int left = targetView.getLeft() - margin;
        int top = targetView.getTop() - margin;
        int right = targetView.getRight() + margin;
        int bottom = targetView.getBottom() + margin;

        // 可点击区域为圆心按钮相交的近似矩形
        mClickRect.set(left, top, right, bottom);

        //mTipX = (targetViewRoot.getWidth() - mPaint.measureText(mTip)) / 2; //提示文字的横坐标，居中即可
        //mTipY = targetViewLocation[1] + mRadius * 2; // 提示文字的纵坐标，只需要在圆的下方，这里设为view的纵坐标加上直径

        mBitmap = Bitmap.createBitmap(targetViewRoot.getWidth(), targetViewRoot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(maskParam.getBgColor());
        mPaint.setXfermode(X_FER_MODE);
        canvas.drawRoundRect(left, top, right, bottom, roundRadius, roundRadius, mPaint);

        drawTips(canvas);
    }

    private void drawTips(Canvas canvas) {

        if (maskParam.getTips() != null) {

            mPaint.setXfermode(null);

            for (int i = 0; i < maskParam.getTips().size(); i++) {
                MaskParam.Tip tip = maskParam.getTips().get(i);
                MaskParam.TipImage image = tip.getImage();
                MaskParam.TipText text = tip.getText();


                //绘制指示图片
                Bitmap imageBitmap = Bitmap.createScaledBitmap(//
                        getBitmapFromVectorDrawable(getContext(), image.getResId()), //
                        image.getWidth(), image.getHeight(), true);

                float imgLeft = 0;
                float imgTop = 0;

                if (image.getToLocationOfMask() == ConstraintWidget.ContentAlignment.LEFT) {
                    imgLeft = mClickRect.left - image.getMarginRight() - image.getWidth();
                    imgTop = mClickRect.centerY();
                    canvas.drawBitmap(imageBitmap, imgLeft, imgTop, null);

                } else if (image.getToLocationOfMask() == ConstraintWidget.ContentAlignment.TOP) {

                } else if (image.getToLocationOfMask() == ConstraintWidget.ContentAlignment.RIGHT) {
                    imgLeft = mClickRect.right + image.getMarginLeft();
                    imgTop = mClickRect.centerY() - image.getHeight() / 2 - image.getMarginBottom();
                    canvas.drawBitmap(imageBitmap, imgLeft, imgTop, null);

                } else if (image.getToLocationOfMask() == ConstraintWidget.ContentAlignment.BOTTOM) {
                }


                //绘制指示文字
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(text.getColor());
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(text.getSize());

                int measureTextWidth = (int) textPaint.measureText(text.getTip());
                StaticLayout staticLayout = new StaticLayout(//
                        text.getTip(), //
                        textPaint, //
                        measureTextWidth, //
                        Layout.Alignment.ALIGN_NORMAL, //
                        1.0f, //
                        0f, //
                        false);

                if (text.getToLocationOfImage() == ConstraintWidget.ContentAlignment.LEFT) {

                } else if (text.getToLocationOfImage() == ConstraintWidget.ContentAlignment.TOP) {

                    //移动画布绘制文字的中心点到指定位置
                    canvas.translate(imgLeft + image.getWidth() / 2, imgTop - text.getHeight());
                    staticLayout.draw(canvas);


                } else if (text.getToLocationOfImage() == ConstraintWidget.ContentAlignment.RIGHT) {
                } else if (text.getToLocationOfImage() == ConstraintWidget.ContentAlignment.BOTTOM) {

                    //staticLayout绘制文字的指定位置(x ,y）但是文字实际上会往左边偏移一半的宽度
                    canvas.translate(imgLeft, imgTop + text.getMarginLeft());
                    staticLayout.draw(canvas);

//                    textTop = imgTop + image.getHeight();
//                    textLeft = (imgLeft + image.getWidth()) / 2 - textWidth / 2;
//                    canvas.drawTips(text.getTip(), textLeft, textTop, mPaint);
                }
            }

        }
    }

    @SuppressLint("RestrictedApi")
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final PointF down = new PointF(event.getX(), event.getY());
                if (isInClickRect(down)) {
                    mDownInClickRect = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!mDownInClickRect) {
                    break;
                }
                final PointF up = new PointF(event.getX(), event.getY());
                if (isInClickRect(up) && mClickListener != null) {
                    mClickListener.onClick(mTargetView);
                    ((ViewGroup) getParent()).removeView(MaskView.this);
                }
                mDownInClickRect = false;
                return true;
        }
        return true;
    }

    private boolean isInClickRect(PointF point) {
        return point.x > mClickRect.left && point.x < mClickRect.right && point.y > mClickRect.top && point.y < mClickRect.bottom;
    }

    private int dp2px(double dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}