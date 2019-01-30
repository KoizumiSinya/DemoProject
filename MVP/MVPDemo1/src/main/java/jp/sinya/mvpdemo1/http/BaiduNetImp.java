package jp.sinya.mvpdemo1.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.sinya.mvpdemo1.bean.BaiduBean;
import jp.sinya.mvpdemo1.baiduhome.BaiduInfoBiz;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 12:34
 * @edithor
 * @date
 */
public class BaiduNetImp implements BaiduInfoBiz {

    @Override
    public void requestData(final BaiduHttpCallBack callBack) {

        HttpClientGet(callBack);
    }

    private void HttpClientGet(final BaiduHttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://www.baidu.com");

                    HttpResponse response = httpClient.execute(httpGet);
                    int responseCode = response.getStatusLine().getStatusCode();

                    if (responseCode == 200) {
                        HttpEntity entity = response.getEntity();
                        String result = EntityUtils.toString(entity, "UTF-8");
                        callBack.response(new BaiduBean(result));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void httpURLConnectionReqeust(final BaiduHttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();

                    bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    callBack.response(new BaiduBean(stringBuilder.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private void okhttpRequest(final BaiduHttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                //RequestBody requestBody = new FormBody.Builder().add("username", "小泉真也").add("password", "aide9shi1HB").build();
                Request request = new Request.Builder()//
                        //.addHeader("User-Agent", "android")
                        //.header("Content-Type", "text/html; charset=utf-8")
                        .url("http://www.baidu.com")
                        //.post(requestBody)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resultStream = response.body().toString();
                        callBack.response(new BaiduBean(resultStream));
                    }
                });
            }
        }).start();
    }

}
