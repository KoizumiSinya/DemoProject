package javaproject.wifidemo1.bean;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/03/15. 12:48
 * @edithor
 * @date
 */
public class WiFiConfig {

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private List<ScanResult> wifiScanList;
    private List<WifiConfiguration> wifiConfigurationList;

    private WifiManager.WifiLock wifiLock;

    public WiFiConfig(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }

    public void openWiFi(Context context) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            Toast.makeText(context, "WiFi没有开启，正在启用...", Toast.LENGTH_SHORT).show();

        } else if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            Toast.makeText(context, "WiFi正在开启，请不要重复点击...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "WiFi已经开启...", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeWiFi(Context context) {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            Toast.makeText(context, "WiFi结束使用，正在关闭...", Toast.LENGTH_SHORT).show();
        } else if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            Toast.makeText(context, "WiFi正在关闭，请不要重复点击...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "WiFi关闭失败，稍后重试...", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkWiFiStatus(Context context) {
        switch (wifiManager.getWifiState()) {
            //正在关闭
            case WifiManager.WIFI_STATE_DISABLING:
                Toast.makeText(context, "WiFi状态：正在关闭", Toast.LENGTH_SHORT).show();
                break;

            //已经关闭
            case WifiManager.WIFI_STATE_DISABLED:
                Toast.makeText(context, "WiFi状态：已经关闭", Toast.LENGTH_SHORT).show();
                break;

            //正在开启
            case WifiManager.WIFI_STATE_ENABLING:
                Toast.makeText(context, "WiFi状态：正在开启", Toast.LENGTH_SHORT).show();
                break;

            //已经开启
            case WifiManager.WIFI_STATE_ENABLED:
                Toast.makeText(context, "WiFi状态：已经开启", Toast.LENGTH_SHORT).show();

                break;

            default:
                Toast.makeText(context, "没有获取到WiFi状态", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void createWiFiLock() {
        wifiLock = wifiManager.createWifiLock("Test");
    }

    public void setAcquireWiFiLock() {
        if (wifiLock == null) {
            createWiFiLock();
        }
        wifiLock.acquire();
    }

    public void setReleaseWiFiLock() {
        if (wifiLock == null) {
            createWiFiLock();
        }
        if (wifiLock.isHeld()) {
            wifiLock.acquire();
        }
    }

    public List<WifiConfiguration> getWifiConfigurationList() {
        return wifiConfigurationList;
    }

    public List<ScanResult> getWifiScanList() {
        return wifiScanList;
    }

    public void setConnectConfig(int index) {
        if (index > wifiConfigurationList.size()) {
            return;
        }

        wifiManager.enableNetwork(wifiConfigurationList.get(index).networkId, true);
    }

    /**
     * 开启扫描
     *
     * @param context
     */
    public void startScan(Context context) {
        wifiManager.startScan();

        //获取扫描结果
        List<ScanResult> tempScanList = wifiManager.getScanResults();
        Log.i("Sinya", "扫描到有: " + tempScanList.size() + " 个WiFi信号");
        Log.i("Sinya", "扫描结果:\n" + tempScanList.toString());

        //得到配置好的网络连接
        wifiConfigurationList = wifiManager.getConfiguredNetworks();

        if (tempScanList == null) {
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
                Toast.makeText(context, "当前区域内没有可用无线网络", Toast.LENGTH_SHORT).show();
            } else if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                Toast.makeText(context, "WiFi正在开启，请稍后重新扫描", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "请先打开WiFi", Toast.LENGTH_SHORT).show();
            }

        } else {
            wifiScanList = new ArrayList<>();
            for (ScanResult scanResult : tempScanList) {
                if (scanResult.SSID == null || scanResult.SSID.length() == 0 || scanResult.capabilities.contains("[IBSS]")) {
                    continue;
                }

                boolean found = false;
                for (ScanResult item : wifiScanList) {
                    if (item.SSID.equals(scanResult.SSID) && item.capabilities.equals(scanResult.capabilities)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    wifiScanList.add(scanResult);
                }
            }

        }
    }

    /**
     * 查看扫描结果
     *
     * @return
     */
    public StringBuilder lookUpScan() {
        StringBuilder builder = new StringBuilder();
        if (wifiScanList != null) {
            for (int i = 0; i < wifiScanList.size(); i++) {
                builder.append("Index: " + new Integer(i + 1).toString() + ", ");
                builder.append(wifiScanList.get(i).toString());
                builder.append("\n");
            }
        }
        return builder;
    }

    public String getMacAddress() {
        if (wifiInfo == null) {
            return "null";
        }
        return wifiInfo.getMacAddress();
    }

    /**
     * 获取接入点的BSSID
     *
     * @return
     */
    public String getBSSID() {
        if (wifiInfo == null) {
            return "null";
        }
        return wifiInfo.getBSSID();
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public int getIPAddress() {
        if (wifiInfo == null) {
            return 0;
        }
        return wifiInfo.getIpAddress();
    }

    public int getNetWorkId() {
        if (wifiInfo == null) {
            return 0;
        }
        return wifiInfo.getNetworkId();
    }

    public String getWiFiInfo() {
        if (wifiInfo == null) {
            return "null";
        }
        return wifiInfo.toString();
    }

    public void addNetWork(WifiConfiguration wifiConfiguration) {
        int id = wifiManager.addNetwork(wifiConfiguration);
        boolean enable = wifiManager.enableNetwork(id, true);
        Log.i("Sinya", "id: " + id + ", enable: " + enable);
    }

    /**
     * 断开指定id的WiFi网络
     *
     * @param id
     */
    public void disconnectWiFi(int id) {
        wifiManager.disableNetwork(id);
        wifiManager.disconnect();
    }

    public void removeWiFi(int id) {
        disconnectWiFi(id);
        wifiManager.removeNetwork(id);
    }

    private WifiConfiguration isExist(String ssid) {
        List<WifiConfiguration> existConfigList = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : existConfigList) {
            if (config.SSID.equals(ssid)) {
                return config;
            }
        }
        return null;
    }

    public WifiConfiguration createWiFiInfo(String ssid, String password, CipherType type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        config.SSID = "\"" + ssid + "\"";
        WifiConfiguration tempConfig = this.isExist(ssid);
        if (tempConfig != null) {
            wifiManager.removeNetwork(tempConfig.networkId);
        }


        switch (type) {
            case WIFI_CIPHER_NOPASS:
                config.wepKeys[0] = "";
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
                break;

            case WIFI_CIPHER_WEP:
                config.hiddenSSID = true;
                config.wepKeys[0] = "\"" + password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
                break;

            case WIFI_CIPHER_WPA:
                config.hiddenSSID = true;
                config.preSharedKey = "\"" + password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                config.status = WifiConfiguration.Status.ENABLED;
                break;

            case WIFI_CIPHER_WPA2:
                config.hiddenSSID = true;
                config.preSharedKey = "\"" + password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                config.status = WifiConfiguration.Status.ENABLED;
                break;

            default:
                break;
        }

        return config;
    }
}
