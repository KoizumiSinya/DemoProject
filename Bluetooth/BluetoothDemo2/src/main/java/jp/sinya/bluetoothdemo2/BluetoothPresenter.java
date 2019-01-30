package jp.sinya.bluetoothdemo2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/03/20. 21:46
 * @edithor
 * @date
 */
public class BluetoothPresenter {
    private BluetoothListView view;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private List<BluetoothDevice> devices;

    public BluetoothPresenter(BluetoothListView view) {
        this.view = view;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devices = new ArrayList<>();
    }

    public void openBluetooth() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText((Context) view, "正在打开蓝牙...", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.enable();
            }

            devices.addAll(bluetoothAdapter.getBondedDevices());
            view.showBluetoothList(devices);
        }else{
            Toast.makeText((Context) view, "没有蓝牙驱动可用", Toast.LENGTH_SHORT).show();
        }
    }

    public void scanBluetooth() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (bluetoothAdapter.isDiscovering()) {
                //关闭蓝牙搜索
                bluetoothAdapter.cancelDiscovery();

            } else {
                //发现当前范围的蓝牙设备，异步方法
                bluetoothAdapter.startDiscovery();
            }
        }
    }

    public void addBluetooth(BluetoothDevice newDevice) {
        this.device = newDevice;
        //如果这个新的蓝牙设备是之前没有配对过的
        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            devices.add(device);
        }
        view.showBluetoothList(devices);
    }
}
