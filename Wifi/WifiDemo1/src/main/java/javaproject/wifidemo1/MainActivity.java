package javaproject.wifidemo1;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import javaproject.wifidemo1.mvp.presenter.WiFiPresenter;
import javaproject.wifidemo1.mvp.view.WifiListView;

public class MainActivity extends AppCompatActivity implements WifiListView {

    private Button btnCheckStatus;
    private Button btnScanWiFi;
    private Button btnOpenWiFi;
    private Button btnCloseWiFi;
    private RecyclerView recyclerView;

    private WiFiPresenter presenter;
    private WiFiListAdapter adapter;
    private WiFiBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadcastReceiver();
        initViews();
        initPresenter();
        initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initViews() {
        btnCheckStatus = findViewById(R.id.btn_check_status);
        btnScanWiFi = findViewById(R.id.btn_scan_wifi);
        btnOpenWiFi = findViewById(R.id.btn_open_wifi);
        btnCloseWiFi = findViewById(R.id.btn_close_wifi);

        btnCheckStatus.setOnClickListener(clickListener);
        btnScanWiFi.setOnClickListener(clickListener);
        btnOpenWiFi.setOnClickListener(clickListener);
        btnCloseWiFi.setOnClickListener(clickListener);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WiFiListAdapter(this, null);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new WiFiListAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                clickItem(position);
            }
        });
    }

    private void initPresenter() {
        presenter = new WiFiPresenter(this);
    }

    private void initBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        receiver = new WiFiBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void clickItem(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("WIFi：" + adapter.getItem(position).SSID);
        dialog.setMessage("请输入密码");
        final EditText editPassword = new EditText(this);
        dialog.setView(editPassword);
        dialog.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = editPassword.getText().toString();
                presenter.addNetWork(adapter.getItem(position).SSID, password);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.create().show();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_check_status:
                    presenter.checkWiFiStatus();
                    break;

                case R.id.btn_scan_wifi:
                    presenter.scanWiFi();
                    break;

                case R.id.btn_open_wifi:
                    presenter.openWiFi();
                    break;

                case R.id.btn_close_wifi:
                    presenter.closeWiFi();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void showScanList(List<ScanResult> list) {
        adapter.setList(list);
    }
}
