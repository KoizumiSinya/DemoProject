package jp.sinya.bluetoothdemo2;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/03/20. 21:44
 * @edithor
 * @date
 */
public interface BluetoothListView {
    void showBluetoothList(List<BluetoothDevice> list);
}
