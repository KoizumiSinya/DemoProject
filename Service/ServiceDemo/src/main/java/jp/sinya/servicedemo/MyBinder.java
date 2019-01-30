package jp.sinya.servicedemo;

import android.os.Binder;

/**
 * @author Koizumi Sinya
 * @date 2018/01/18. 16:47
 * @edithor
 * @date
 */
public class MyBinder extends Binder {

    private MyService service;
    private OnMyListener listener;

    public MyBinder(MyService service) {
        this.service = service;
    }

    public void binderTestMethod(String msg) {
        service.serviceTestMethod(msg);
        if (listener != null) {
            listener.onListTestMethod("Hi Activity...");
        }
    }

    public interface OnMyListener {
        void onListTestMethod(String msg);
    }

    public void setListener(OnMyListener listener) {
        this.listener = listener;
    }
}
