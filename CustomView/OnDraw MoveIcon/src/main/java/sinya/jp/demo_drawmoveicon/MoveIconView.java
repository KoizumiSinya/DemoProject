package sinya.jp.demo_drawmoveicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by 01 on 2015/7/29.
 */
public class MoveIconView extends View {

    private Context context;
    public float currentX = 0;
    public float currentY = 0;



    public MoveIconView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, p);
    }
}
