package sap_mobile_newapi.demo_slidingmenudelete;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sap_mobile_newapi.demo_slidingmenudelete.widget.MySlidingMenu;


public class SecondActivity extends Activity {

    private MySlidingMenu slidingMenu;

    private RelativeLayout contentLayout;
    private TextView delete;
    private View txt_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_delete);

        slidingMenu = (MySlidingMenu) findViewById(R.id.sliding_layout);
        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        txt_content = findViewById(R.id.txt_content);
        delete = (TextView) findViewById(R.id.delete);

        slidingMenu.setScrollEvent(txt_content);

        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingMenu.isRightLayoutVisible()) {
                    Log.i("Sinya", "归位");
                    slidingMenu.scrollToRightLayout();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "删除", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
