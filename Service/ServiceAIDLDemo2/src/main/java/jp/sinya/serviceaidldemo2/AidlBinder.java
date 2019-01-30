package jp.sinya.serviceaidldemo2;

import android.os.RemoteException;

/**
 * @author Koizumi Sinya
 * @date 2018/01/19. 16:33
 * @edithor
 * @date
 */
public class AidlBinder extends IMyAidlInterface.Stub {

    private MyService service;
    private IMyCallBackListener listener;

    public AidlBinder(MyService service) {
        this.service = service;
    }

    @Override
    public void aidlTestMethod(String msg) throws RemoteException {
        service.serviceTestMethod(msg);
        listener.onResponse("Hi Activity...");
    }

    @Override
    public void registerListener(IMyCallBackListener listener) throws RemoteException {
        this.listener = listener;
    }
}
