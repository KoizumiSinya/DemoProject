package studio.sinya.jp.demo_map_baidu;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by SinyaKoizumi on 2015/9/30.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);
    }
}
