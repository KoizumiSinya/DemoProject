package studio.sinya.jp.demo_slidingpane;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    private SlidingPaneLayout paneLayout;
    private LinearLayout menu, home;

    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private int maxMargin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setContentView(R.layout.activity_main);

        paneLayout = (SlidingPaneLayout) findViewById(R.id.slidingPane);
        menu = (LinearLayout) findViewById(R.id.menu);
        home = (LinearLayout) findViewById(R.id.home);

        //设置适当的比例作为边距
        maxMargin = displayMetrics.heightPixels / 10;

        paneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                // 如果API大于11
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {

                    //右侧布局发生变化
                    float scale = 1 - ((slideOffset) * maxMargin * 2) / (float) displayMetrics.heightPixels;
                    home.setScaleX(scale);
                    home.setScaleY(scale);
                    home.setPivotX(0);
                    home.setPivotY(displayMetrics.heightPixels / 2);

                    // + 0.1f是为了保证完全右侧划开之后，Home界面的透明度不为全透明
                    home.setAlpha(1 - slideOffset + 0.1f);

                    //左侧布局发生变化
                    float scale2 = 1 - ((1 - slideOffset) * maxMargin * 2) / (float) displayMetrics.heightPixels;
                    menu.setScaleX(scale2);
                    menu.setScaleY(scale2);
                    menu.setPivotX(0);
                    menu.setPivotY(displayMetrics.heightPixels / 2);

                    //Menu直接使用固定透明度
                    menu.getBackground().setAlpha(30);
                }
            }

            @Override
            public void onPanelOpened(View panel) {
                //这里可以对  paneLayout 界面右滑展开之后 进行什么操作
            }

            @Override
            public void onPanelClosed(View panel) {
                //这里可以对  paneLayout 界面左滑关闭之后 进行什么操作
            }
        });
    }
}
