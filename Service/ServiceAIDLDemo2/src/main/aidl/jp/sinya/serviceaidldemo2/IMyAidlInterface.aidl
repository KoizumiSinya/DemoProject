// IMyAidlInterface.aidl
package jp.sinya.serviceaidldemo2;
import jp.sinya.serviceaidldemo2.IMyCallBackListener;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    void aidlTestMethod(String msg);
    void registerListener(IMyCallBackListener listener);
}
