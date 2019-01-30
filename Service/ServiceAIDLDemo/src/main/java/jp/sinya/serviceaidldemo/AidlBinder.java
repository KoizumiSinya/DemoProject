package jp.sinya.serviceaidldemo;

import android.os.RemoteException;

/**
 * @author Koizumi Sinya
 * @date 2018/01/18. 17:20
 * @edithor
 * @date
 */
public class AidlBinder extends IMyAidlInterface.Stub {

    private MyService service;

    public AidlBinder(MyService service) {
        this.service = service;
    }

    @Override
    public void aidlTestMethod(String str) throws RemoteException {
        service.serviceTestMethod(str);
    }
}
