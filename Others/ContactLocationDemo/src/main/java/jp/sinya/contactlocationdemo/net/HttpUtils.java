package jp.sinya.contactlocationdemo.net;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Koizumi Sinya
 * @date 2018/01/01. 21:35
 * @edithor
 * @date
 */
public class HttpUtils {
    private String url;
    private Map<String, String> param;
    private HttpResponse response;
    private final OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler(Looper.getMainLooper());

    public HttpUtils(HttpResponse response) {
        this.response = response;
    }

    public void sendPostHttp(String url, Map<String, String> param) {
        sendHttp(url, param, true);
    }

    public void sendGetHttp(String url, Map<String, String> param) {
        sendHttp(url, param, false);
    }

    private void sendHttp(String url, Map<String, String> param, boolean isPost) {
        this.url = url;
        this.param = param;

        run(isPost);
    }

    private void run(boolean isPost) {
        final Request request = createRequest(isPost);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (response != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            response.onError(e.toString());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response res) throws IOException {
                if (response != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (res.isSuccessful()) {
                                try {
                                    response.onSuccess(res.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    response.onError("数据结果转换失败");
                                }

                            } else {
                                response.onError("请求失败");
                            }
                        }
                    });
                }
            }
        });
    }

    private Request createRequest(boolean isPost) {
        Request request;
        if (isPost) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }

            request = new Request.Builder() //
                    .url(url) //
                    .post(builder.build()) //
                    .build();
        } else {
            String completeURL = url + "?" + paramIntoUrl(param);
            request = new Request.Builder().url(completeURL).build();
        }
        return request;
    }

    private String paramIntoUrl(Map<String, String> param) {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String urlParam = builder.toString().substring(0, builder.length() - 1);
        return urlParam;
    }

    public interface HttpResponse {
        void onSuccess(Object obj);

        void onError(String err);
    }
}
