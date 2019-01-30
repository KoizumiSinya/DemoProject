package jp.sinya.test.retrofitdemo;

import android.app.Application;
import android.content.Context;

/**
 * @author Koizumi Sinya
 * @date 2017/05/21. 1:39
 * @edithor
 * @date
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
