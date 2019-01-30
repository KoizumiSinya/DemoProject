package com.example.len.testapplication.sample.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.len.testapplication.sample.entry.PermissionRequestCode;
import com.example.len.testapplication.sample.interfaces.OnRequestSDPermissionListener;

/**
 * @author dragon
 * @date 2016/7/29 10:00
 */
public class BaseActivity extends AppCompatActivity {

    private OnRequestSDPermissionListener requestSDPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void checkSDPermission(boolean showRequestPermissionRationale) {
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (requestSDPermissionListener != null) {
                requestSDPermissionListener.onHaveSDPermission();
            }
        } else {
            if (showRequestPermissionRationale) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (requestSDPermissionListener != null) {
                        requestSDPermissionListener.onShowSDPermissionRationale();
                    }
                } else {
                    requestSDPermission();
                }
            } else {
                requestSDPermission();
            }
        }
    }

    public void setOnRequestSDPermissionListener(OnRequestSDPermissionListener requestSDPermissionListener) {
        this.requestSDPermissionListener = requestSDPermissionListener;
    }

    /**
     * 检查是否具有权限
     *
     * @param permissionName 权限的名字  比如：写SD卡的权限  Manifest.permission.WRITE_EXTERNAL_STORAGE
     * @return true 有权限  false没有权限
     */
    public boolean checkPermission(String permissionName) {
        boolean havePermission = false;
        if (ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED) {
            havePermission = true;
        }
        return havePermission;
    }


    public void requestSDPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionRequestCode.SD);
    }

    /*public void requestPermission(String[] permissonArray, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, permissonArray, permissionRequestCode);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null) {
            switch (requestCode) {
                case PermissionRequestCode.SD:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        if (requestSDPermissionListener != null) {
                            requestSDPermissionListener.onHaveSDPermission();
                        }
                    } else {
                        if (requestSDPermissionListener != null) {
                            requestSDPermissionListener.onRefuseSDPermission();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
