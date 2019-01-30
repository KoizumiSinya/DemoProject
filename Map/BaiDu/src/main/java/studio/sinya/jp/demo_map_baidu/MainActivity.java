package studio.sinya.jp.demo_map_baidu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

import studio.sinya.jp.demo_map_baidu.map.BaseMapActivity;
import studio.sinya.jp.demo_map_baidu.map.ControlMapActivity;
import studio.sinya.jp.demo_map_baidu.map.LayersMapActivity;
import studio.sinya.jp.demo_map_baidu.map.LocationActivity;
import studio.sinya.jp.demo_map_baidu.map.MapFragmentActivity;
import studio.sinya.jp.demo_map_baidu.map.MapUISettingActivity;
import studio.sinya.jp.demo_map_baidu.map.MultiMapFragmentActivity;


public class MainActivity extends Activity {

    private SDKReceiver mReceiver;

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            Log.d("Sinya", "action: " + s);
            TextView text = (TextView) findViewById(R.id.text_Info);
            text.setTextColor(Color.RED);
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (text.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                text.setText("网络出错");
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
    }

    public void goMap(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.btn1:
                intent = new Intent(this, BaseMapActivity.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                intent = new Intent(this, MapFragmentActivity.class);
                startActivity(intent);
                break;

            case R.id.btn3:
                intent = new Intent(this, LayersMapActivity.class);
                startActivity(intent);
                break;

            case R.id.btn4:
                intent = new Intent(this, MultiMapFragmentActivity.class);
                startActivity(intent);
                break;

            case R.id.btn5:
                intent = new Intent(this, ControlMapActivity.class);
                startActivity(intent);
                break;

            case R.id.btn6:
                intent = new Intent(this, MapUISettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn7:
                intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
        }
    }


}
