package sinya.jp.demo_titleview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/10 16:34
 * editor：
 * updateDate：2015/9/10 16:34
 */
public class TitleView extends FrameLayout implements View.OnClickListener {

    private Button left;
    private Button right;
    private TextView title;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.comm_title, this);
        title = (TextView)findViewById(R.id.txt_title);

        left = (Button) findViewById(R.id.btn_left);
        left.setOnClickListener(this);

        right = (Button) findViewById(R.id.btn_right);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_left:
                onClickLeft();
                break;

            case R.id.btn_right:
                onClickRight();
                break;
        }
    }

    public void onClickLeft() {
        ((Activity)getContext()).finish();
    }

    public void onClickRight() {

    }

    public void setLeftText(String str) {
        left.setText(str);
    }

    public void setRightText(String str) {
        right.setText(str);
    }

    public void setTitleText(String str) {
        title.setText(str);
    }


}
