package jp.sinya.bluetoothdemo2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BluetoothListView {
    private BluetoothPresenter presenter;
    private BluetoothListAdapter adapter;
    private RecyclerView recyclerView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // 当某一个蓝牙设备被找到，则会收到此消息
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                // 获取搜索到的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                presenter.addBluetooth(device);

            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                setTitle("蓝牙设备");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("蓝牙demo");
        setContentView(R.layout.activity_main);
        initReceiver();
        initPresenter();
        initRecyclerView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, intentFilter);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new BluetoothListAdapter(this, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BluetoothListAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {

            }
        });
    }

    private void initPresenter() {
        presenter = new BluetoothPresenter(this);
    }

    public void openBluetooth(View view) {
        presenter.openBluetooth();
    }

    public void scanBluetooth(View view) {
        presenter.scanBluetooth();
    }

    @Override
    public void showBluetoothList(List<BluetoothDevice> list) {
        adapter.setList(list);
    }


}
