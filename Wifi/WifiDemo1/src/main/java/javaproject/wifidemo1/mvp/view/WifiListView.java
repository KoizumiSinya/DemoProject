package javaproject.wifidemo1.mvp.view;

import android.net.wifi.ScanResult;

import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/03/18. 15:32
 * @edithor
 * @date
 */
public interface WifiListView extends MvpView {
    void showScanList(List<ScanResult> list);

}
