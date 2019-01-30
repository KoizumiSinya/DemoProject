package jp.sinya.hoffixdemo2;

import android.app.Application;

/**
 * @author Koizumi Sinya
 * @date 2018/01/28. 23:10
 * @edithor
 * @date
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFix();
    }

    private void initFix() {
        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.loadFixDix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
