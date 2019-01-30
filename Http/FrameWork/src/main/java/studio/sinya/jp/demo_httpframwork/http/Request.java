package studio.sinya.jp.demo_httpframwork.http;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import studio.sinya.jp.demo_httpframwork.http.callback.ICallback;

/**
 * Created by SinyaKoizumi on 2015/9/13.
 */
public class Request {

    public enum RequestMethod {
        GET, POST, DELETE, PUT
    }

    public RequestMethod requestMethod;

    public String url;

    public HttpEntity entity;
    public Map<String, String> headers;
    public static final String ENCODING = "UTF-8";

    public ICallback callBack;

    private RequestTask task;

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.requestMethod = method;
    }

    public void setEntity(ArrayList<NameValuePair> forms) {
        try {
            entity = new UrlEncodedFormEntity(forms, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setEntity(String postContent) {
        try {
            entity = new StringEntity(postContent, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setEntity(byte[] bytes) {
        entity = new ByteArrayEntity(bytes);
    }

    public void setCallBack(ICallback callback) {
        this.callBack = callback;
    }

    public void execute() {
        task = new RequestTask(this);
        task.execute();
    }

}
