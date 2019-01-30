package javaproject.wifidemo1.mvp.presenter;

import android.content.Context;

import javaproject.wifidemo1.bean.CipherType;
import javaproject.wifidemo1.bean.WiFiConfig;
import javaproject.wifidemo1.mvp.view.WifiListView;

/**
 * @author Koizumi Sinya
 * @date 2018/03/18. 16:11
 * @edithor
 * @date
 */
public class WiFiPresenter extends MvpPresenter {
    private WifiListView view;
    private WiFiConfig wiFiConfig;

    public WiFiPresenter(WifiListView view) {
        this.view = view;
        wiFiConfig = new WiFiConfig((Context) view);
    }

    public void scanWiFi() {
        wiFiConfig.startScan((Context) view);
        view.showScanList(wiFiConfig.getWifiScanList());
    }

    public void addNetWork(String ssid, String password) {
        wiFiConfig.addNetWork(wiFiConfig.createWiFiInfo(ssid, password, CipherType.WIFI_CIPHER_WPA2));
    }

    public void checkWiFiStatus() {
        wiFiConfig.checkWiFiStatus((Context) view);
    }

    public void openWiFi() {
        wiFiConfig.openWiFi((Context) view);
    }

    public void closeWiFi() {
        wiFiConfig.closeWiFi((Context) view);
    }
}
