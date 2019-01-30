package jp.sinya.demo.maskview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);

        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void show(View view) {
        showGuideCircleBig(view);
    }

    public void show2(View view) {
        showGuideRound(view);
    }

    public void show3(View view) {
        showGuideRoundBig(view);
    }

    public void show4(View view) {
        showGuideCircle(view);
    }

    private void showGuideCircle(View view) {
//        MaskView lightView = new MaskView(this);
//        lightView.showMaskCircle(view, "点击这里，领取大奖", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void showGuideCircleBig(View view) {

        MaskParam param = new MaskParam();
        MaskParam.Tip tip = new MaskParam.Tip();

        MaskParam.TipImage tipImage = new MaskParam.TipImage();
        tipImage.setWidth(dp2px(60));
        tipImage.setHeight(dp2px(60));
        tipImage.setResId(R.drawable.lib_res_drawable_ic_guide_arrow_top2left);
        tipImage.setMarginLeft(dp2px(60));
        tipImage.setMarginBottom(dp2px(40));
        tipImage.setToLocationOfMask(ConstraintWidget.ContentAlignment.RIGHT);
        tip.setImage(tipImage);

        MaskParam.TipText tipText = new MaskParam.TipText();
        tipText.setColor(Color.WHITE);
        //tipText.setTip("Dig some recipes here\nand we’ll guideyou!");
        tipText.setTip("Cook directly with different\nfunctions in manual cooking\nmode!");
        //tipText.setTip("Hello world!");
        tipText.setSize(sp2px(22));
        tipText.setMarginBottom(dp2px(20));
        tipText.setWidth(dp2px(210));
        tipText.setHeight(dp2px(90));
        tipText.setToLocationOfImage(ConstraintWidget.ContentAlignment.TOP);
        tip.setText(tipText);


        param.addTip(tip);

        MaskView maskView = new MaskView(this, param);
        maskView.showMask(view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void showGuideCircleBig2(View view) {

        MaskParam param = new MaskParam();
        MaskParam.Tip tip = new MaskParam.Tip();

        MaskParam.TipImage tipImage = new MaskParam.TipImage();
        tipImage.setWidth(dp2px(60));
        tipImage.setHeight(dp2px(60));
        tipImage.setResId(R.drawable.lib_res_drawable_ic_guide_arrow_down2right);
        tipImage.setMarginLeft(dp2px(85));
        tipImage.setMarginBottom(dp2px(17));
        tipImage.setToLocationOfMask(ConstraintWidget.ContentAlignment.RIGHT);
        tip.setImage(tipImage);

        MaskParam.TipText tipText = new MaskParam.TipText();
        tipText.setColor(Color.WHITE);
        //tipText.setTip("Dig some recipes here\nand we’ll guideyou!");
        tipText.setTip("Cook directly with different\nfunctions in manual cooking\nmode!");
        //tipText.setTip("Hello world!");
        tipText.setSize(sp2px(22));
        tipText.setMarginLeft(dp2px(10));
        tipText.setWidth(dp2px(210));
        tipText.setToLocationOfImage(ConstraintWidget.ContentAlignment.TOP);
        tip.setText(tipText);


        param.addTip(tip);

        MaskView maskView = new MaskView(this, param);
        maskView.showMask(view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void showGuideRound(View view) {
//        MaskView lightView = new MaskView(this);
//        lightView.showMaskRound(view, "点击这里，领取大奖", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void showGuideRoundBig(View view) {
//        MaskView lightView = new MaskView(this, 20, 50);
//        lightView.showMaskRound(view, "点击这里，领取大奖", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
