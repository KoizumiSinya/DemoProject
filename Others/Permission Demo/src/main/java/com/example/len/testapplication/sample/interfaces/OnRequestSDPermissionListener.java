package com.example.len.testapplication.sample.interfaces;

/**
 * @author dragon
 * @date 2016/7/29 11:32
 */
public interface OnRequestSDPermissionListener {
    void onHaveSDPermission();

    void onRefuseSDPermission();

    void onShowSDPermissionRationale();
}
