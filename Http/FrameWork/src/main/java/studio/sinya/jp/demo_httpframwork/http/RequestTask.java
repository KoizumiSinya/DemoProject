package studio.sinya.jp.demo_httpframwork.http;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import studio.sinya.jp.demo_httpframwork.http.utils.HttpClientUtil;

/**
 * Created by SinyaKoizumi on 2015/9/13.
 */
public class RequestTask extends AsyncTask<Object, Integer, Object> {
    private Request request;

    public RequestTask(Request request) {
        super();
        this.request = request;
    }

    @Override
    protected Object doInBackground(Object... params) {
        try {
            HttpResponse response = HttpClientUtil.excute(request);
            return request.callBack.handle(response);
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (result instanceof Exception) {
            request.callBack.onFilure((Exception) result);
        } else {
            request.callBack.onSuccess(result);
        }
    }
}
