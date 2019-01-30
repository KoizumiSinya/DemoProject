package studio.sinya.jp.demo_httpframwork.http.callback;

import org.apache.http.HttpResponse;

/**
 * Created by SinyaKoizumi on 2015/9/13.
 */
public interface ICallback {
    void onFilure(Exception result);

    void onSuccess(Object result);

    Object handle(HttpResponse response);
}
