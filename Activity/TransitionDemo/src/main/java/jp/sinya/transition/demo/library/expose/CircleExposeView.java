package jp.sinya.transition.demo.library.expose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import jp.sinya.transition.demo.library.expose.base.ExposeView;


/**
 * Created by Mr_immortalZ on 2016/10/13.
 * email : mr_immortalz@qq.com
 */

public class CircleExposeView extends ExposeView {


    public CircleExposeView(Context context) {
        super(context);
    }


    @Override
    public void animDrawing(Canvas canvas, Paint paint) {
        canvas.drawCircle(startExposeX, startExposeY, exposeWidth, paint);
    }

}
