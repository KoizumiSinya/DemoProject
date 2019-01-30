package jp.sinya.demo.wavelayout;

import android.app.Application;
import android.widget.Toast;


public class MyApplication extends Application {
    private static MyApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;


    }

    public static MyApplication getInstance() {

        if (sInstance == null) {
            sInstance = new MyApplication();
        }
        return sInstance;
    }


    public void show(String msg) {


        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}