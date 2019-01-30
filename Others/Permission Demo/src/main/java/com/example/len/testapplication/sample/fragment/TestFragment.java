package com.example.len.testapplication.sample.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.len.testapplication.R;
import com.example.len.testapplication.sample.activity.MainActivity;
import com.example.len.testapplication.sample.interfaces.OnRequestSDPermissionListener;


public class TestFragment extends Fragment {

    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.id_btn_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setOnRequestSDPermissionListener(new OnRequestSDPermissionListener() {
                    @Override
                    public void onHaveSDPermission() {
                        Toast.makeText(activity, "HaveSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRefuseSDPermission() {
                        Toast.makeText(activity, "RefuseSDPermission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShowSDPermissionRationale() {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                                        activity.requestSDPermission();
                                    }
                                }).show();
                    }
                });
                activity.checkSDPermission(true);
            }
        });

    }

}
