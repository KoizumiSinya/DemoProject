package com.android.volley.https;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.sinya.demo_http_volley.MyApplication;

import java.util.Map;

/**
 * Created by Koizumi Sinya on 2015/12/15.
 */
public class VolleyRequest {

    public static StringRequest stringRequest;
    public static Context context;

    public static void RequestGet(Context context, String url, String tag, VolleyCallBack volleyCallBack) {

        //防止重复
        MyApplication.getRequestQueue().cancelAll(tag);
        stringRequest = new StringRequest(Method.GET, url, volleyCallBack.getSuccessListener(), volleyCallBack.getErrorListener());
        Entry cacheEntry = stringRequest.getCacheEntry();
        startRequest(tag);
    }


    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyCallBack volleyCallBack) {

        MyApplication.getRequestQueue().cancelAll(tag);
        stringRequest = new StringRequest(Method.POST, url, volleyCallBack.getSuccessListener(), volleyCallBack.getErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        startRequest(tag);
    }

    public static void startRequest(String tag) {
        if (stringRequest != null) {
            stringRequest.setTag(tag);
            MyApplication.getRequestQueue().add(stringRequest);
            MyApplication.getRequestQueue().start();
        }
    }
}

