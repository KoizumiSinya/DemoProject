package jp.sinya.serviceaidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Koizumi Sinya
 * @date 2018/01/18. 17:31
 * @edithor
 * @date
 */
public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface service;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            //IMyAidlInterface service = IMyAidlInterface.Stub.asInterface(service);
            //IMyAidlInterface.Stub binder = (IMyAidlInterface.Stub) service;
            //IMyAidlInterface.Stub service = (IMyAidlInterface.Stub) IMyAidlInterface.Stub.asInterface(service);

            service = IMyAidlInterface.Stub.asInterface(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private void initService() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void send(View view) {
        try {
            service.aidlTestMethod("Hi Service...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
