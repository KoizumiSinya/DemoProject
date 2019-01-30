package jp.sinya.test.jpushdemo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * @author KoizumiSinya
 * @date 2016/12/5. 0:19
 * @editor
 * @date
 * @describe
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
