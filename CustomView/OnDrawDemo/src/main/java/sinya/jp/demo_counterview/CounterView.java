package sinya.jp.demo_counterview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义控件 使用onDraw绘制
 * author : Sinya Koizumi
 * createDate ：2015/9/10 11:05
 * editor：
 * updateDate：2015/9/10 11:05
 */
public class CounterView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Rect mBounds;
    private int mCount;

    public CounterView(Context context) {
        super(context);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制一个矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        //更改画笔属性，用来绘制文字
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);

        String text = String.valueOf(mCount);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);

        float textWidth = mBounds.width();
        float textHeight = mBounds.height();

        Log.i("Sinya", "textWidth " + textWidth + "; textHeight " + textHeight + "; getWidth() " + getWidth() + "; getHeight() " + getHeight());

        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 2, mPaint);


    }

    @Override
    public void onClick(View v) {
        mCount++;
        invalidate();
    }
}
