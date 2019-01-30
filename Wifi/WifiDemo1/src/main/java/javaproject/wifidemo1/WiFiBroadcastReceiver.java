package javaproject.wifidemo1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * @author Koizumi Sinya
 * @date 2018/03/19. 17:57
 * @edithor
 * @date
 */
public class WiFiBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo.isConnected()) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String wifiSSID = wifiManager.getConnectionInfo().getSSID();
            Toast.makeText(context, wifiSSID + "连接成功", Toast.LENGTH_SHORT).show();
        }
    }
}
