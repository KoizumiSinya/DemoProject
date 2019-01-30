package jp.sinya.bluetoothdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "该设备没有蓝牙支持", Toast.LENGTH_SHORT).show();
            return;
        }

        getBluetoothInfo();
    }

    private void getBluetoothInfo() {
        bluetoothAdapter.getName();
        bluetoothAdapter.getAddress();
        bluetoothAdapter.getState();
    }

    public void open(View v) {
        //判断蓝牙是否已经打开在使用中
        if (bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "蓝牙已经在使用中", Toast.LENGTH_SHORT).show();
        } else {
            openBluetooth2();
        }
    }

    public void close(View v) {
        boolean isClose = bluetoothAdapter.disable();
        if (isClose) {
            Toast.makeText(this, "蓝牙关闭", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "蓝牙关闭失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void openBluetooth1() {
        //强行打开蓝牙的方式
        boolean isOpen = bluetoothAdapter.enable();

        if (isOpen) {
            Toast.makeText(this, "蓝牙开启", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "蓝牙开启失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void openBluetooth2() {
        //使用Intent的方式提示用户去设置开启蓝牙
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "蓝牙开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "蓝牙开启失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
