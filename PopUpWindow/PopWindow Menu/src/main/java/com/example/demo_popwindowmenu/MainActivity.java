package com.example.demo_popwindowmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.demo_popwindowmenu.widget.MyPopWindow1;

public class MainActivity extends Activity {

    private Button btn1;
    private Context context;

    MyPopWindow1 pop1;
    private String[] item = new String[]{"苹果", "香蕉", "西瓜", "橙子", "哈密瓜", "葡萄" ,"桃子"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        btn1 = (Button) findViewById(R.id.btn1);
        initPop1();
    }

    private void initPop1() {
        pop1 = new MyPopWindow1(context, item, 100);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop1.dismiss();
                Toast.makeText(context, item[position], Toast.LENGTH_SHORT).show();
            }
        };

        PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        };

        pop1.setOnItemClickListener(itemClickListener);
        pop1.setOnDismissListener(dismissListener);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                pop1.showAsRightDown(btn1,0);
                break;

            case R.id.btn2:
                break;

            default:
                break;
        }
    }


}
