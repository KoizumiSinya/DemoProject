package jp.sinya.db.demo1;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * @author Sinya
 * @date 2018/06/10. 18:29
 * @edithor
 * @date
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initStetho();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
