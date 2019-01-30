package com.android.volley.https;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * Created by Koizumi Sinya on 2015/12/15.
 */
public abstract class VolleyCallBack {

    public Context mContext;
    public static Listener<String> mSuccessListener;
    public static ErrorListener mErrorListener;

    public VolleyCallBack(Context context, Listener<String> succeesListener, ErrorListener errorListener) {
        this.mContext = context;
        this.mSuccessListener = succeesListener;
        this.mErrorListener = errorListener;
    }

    public Listener<String> getSuccessListener() {
        mSuccessListener = new Listener<String>() {
            @Override
            public void onResponse(String response) {
                setSuccessResult(response);
            }
        };
        return mSuccessListener;
    }

    public ErrorListener getErrorListener() {
        mErrorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorResult(error);
            }
        };
        return mErrorListener;
    }

    // [+] Abstract

    public abstract void setSuccessResult(String result);

    public abstract void setErrorResult(VolleyError error);

    // [-] Abstract
}
