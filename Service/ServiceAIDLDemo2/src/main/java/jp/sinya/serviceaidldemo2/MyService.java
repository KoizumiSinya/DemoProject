package jp.sinya.serviceaidldemo2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Koizumi Sinya
 * @date 2018/01/19. 16:34
 * @edithor
 * @date
 */
public class MyService extends Service {
    private AidlBinder binder = new AidlBinder(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void serviceTestMethod(String msg) {
        Log.i("Sinya", "Service收到来自Activity的消息:" + msg);
    }
}
