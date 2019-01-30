package com.shop.cart.activity;

import android.app.Application;

import com.shop.cart.utils.ImageLoaderFactory;

/**
 * Created by xugang on 2016/4/7.
 * Describe
 */
public class MApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderFactory.init(getApplicationContext());
    }
}
