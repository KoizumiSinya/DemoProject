package studio.sinya.jp.demo_httpframwork.http.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Map;

import studio.sinya.jp.demo_httpframwork.http.Request;

/**
 * Created by SinyaKoizumi on 2015/9/13.
 */
public class HttpClientUtil {

    public static HttpResponse excute(Request request) throws Exception {
        switch (request.requestMethod) {
            case GET:
                return get(request);

            case POST:
                return post(request);
            default:
                throw new IllegalStateException("you doesn't define this request method");
        }
    }

    public static HttpResponse get(Request request) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(request.url);
        addHeader(get, request.headers);
        HttpResponse response = client.execute(get);
        return response;
    }

    private static HttpResponse post(Request request) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(request.url);
        addHeader(post, request.headers);
        //post的请求参数在 Request 中定义，如果为空，则没有定义
        if (request.entity == null) {
            throw new IllegalStateException("you forget to set post content to the http post");
        } else {
            post.setEntity(request.entity);
        }
        HttpResponse response = client.execute(post);
        return response;
    }

    public static void addHeader(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
