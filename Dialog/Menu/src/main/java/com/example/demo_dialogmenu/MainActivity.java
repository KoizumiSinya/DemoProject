package com.example.demo_dialogmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demo_dialogmenu.widget.MyDialog;

public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if (MyDialog.showDialog(context, "您正在使用快捷支付", "确定支付吗", 2, false) == MyDialog.OK) {
                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "取消支付", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn2:
                MyDialog.showMenu(context, "请选择支付方式", new String[]{"网银支付", "支付宝支付", "微信支付"}, true, new MyDialog.MenuItemClickListener() {
                    @Override
                    public void confirm(int item) {
                        if (item < 0) {
                            return;
                        }

                        if (item == 0) {
                            Toast.makeText(context, "网银支付", Toast.LENGTH_SHORT).show();
                        } else if (item == 1) {
                            Toast.makeText(context, "支付宝支付", Toast.LENGTH_SHORT).show();
                        } else if (item == 2) {
                            Toast.makeText(context, "微信支付", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}
