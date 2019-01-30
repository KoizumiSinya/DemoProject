package com.sinya.demo_http_volley;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.https.VolleyCallBack;
import com.android.volley.https.VolleyRequest;
import com.android.volley.toolbox.StringRequest;
import com.sinya.demo_http_volley.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTextView;
    private static String url = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mTextView = (TextView) findViewById(R.id.tv_content);

    }

    private void HttpGet(String url) {

        LogUtils.I("Sinya", "HttpGet URL:\n" + url);

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.I("Sinya", "请求成功:\n" + response);
                        mTextView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.I("Sinya", "请求失败:\n" + error.toString());
                    }
                });

        //设置标签
        stringRequest.setTag("Get1");
        //是否控制缓存
        //stringRequest.setShouldCache(true);

        MyApplication.getRequestQueue().add(stringRequest);
    }

    private void HttpPost(String url) {
        StringRequest stringRequest = new StringRequest(

                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int errorStatusCode = error.networkResponse.statusCode;
                    }
                }) {

            /**
             * 复写POST参数的方法
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //如果有参数则提交
                if (true) {
                    Map<String, String> map = new HashMap<>();
                    map.put("params1", "value1");
                    map.put("params1", "value1");
                    return map;

                    //如果没有参数则使用原来的
                } else {
                    return super.getParams();
                }
            }
        };
    }

    // [+] Activity Override

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("tag");
    }

    // [-] Activity Override
    // [+] OnClick

    public void getData(View view) {
        switch (view.getId()) {

            case R.id.btn1:

                VolleyRequest.RequestGet(this, url, "123", new VolleyCallBack(mContext, VolleyCallBack.mSuccessListener,VolleyCallBack.mErrorListener) {
                    @Override
                    public void setSuccessResult(String result) {

                    }

                    @Override
                    public void setErrorResult(VolleyError error) {

                    }
                });

                break;

            default:
                break;
        }
    }

    // [-] OnClick
}
