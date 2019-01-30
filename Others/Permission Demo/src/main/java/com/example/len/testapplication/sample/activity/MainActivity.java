package com.example.len.testapplication.sample.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.len.testapplication.R;
import com.example.len.testapplication.sample.interfaces.OnRequestSDPermissionListener;

public class MainActivity extends BaseActivity {

    private Button btn_sd;
    private Button btn_camere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_sd = (Button) findViewById(R.id.btn_sd);
        btn_camere = (Button) findViewById(R.id.btn_camere);
        setOnClickListener();
    }

    private void setOnClickListener() {

        btn_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnRequestSDPermissionListener(new OnRequestSDPermissionListener() {
                    @Override
                    public void onHaveSDPermission() {
                        Toast.makeText(MainActivity.this, "HaveSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRefuseSDPermission() {
                        Toast.makeText(MainActivity.this, "RefuseSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShowSDPermissionRationale() {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("权限说明")
                                .setMessage("请允许授予SD的读写权限")
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestSDPermission();
                                    }
                                }).show();
                    }
                });
                checkSDPermission(true);
            }
        });

        btn_camere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnRequestSDPermissionListener(new OnRequestSDPermissionListener() {
                    @Override
                    public void onHaveSDPermission() {
                        Toast.makeText(MainActivity.this, "HaveSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRefuseSDPermission() {
                        Toast.makeText(MainActivity.this, "RefuseSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShowSDPermissionRationale() {
                        Toast.makeText(MainActivity.this, "ShowSDPermissionRationale", Toast.LENGTH_SHORT).show();
                    }
                });
                checkSDPermission(false);
            }
        });
    }
}
