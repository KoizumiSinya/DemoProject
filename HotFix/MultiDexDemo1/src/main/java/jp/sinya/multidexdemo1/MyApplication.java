package jp.sinya.multidexdemo1;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author Koizumi Sinya
 * @date 2018/01/06. 15:54
 * @edithor
 * @date
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }
}
