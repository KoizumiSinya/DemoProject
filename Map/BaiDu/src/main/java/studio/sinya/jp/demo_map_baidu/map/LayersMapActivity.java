package studio.sinya.jp.demo_map_baidu.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import studio.sinya.jp.demo_map_baidu.R;

/**
 * 演示地图图层显示控制方法
 * author : Sinya Koizumi
 * createDate ：2015/9/30 14:34
 * editor：
 * updateDate：2015/9/30 14:34
 */
public class LayersMapActivity extends Activity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers);

        mMapView = (MapView) findViewById(R.id.map_View);
        mBaiduMap = mMapView.getMap();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    public void Click(View v) {
        boolean checked = false;

        if(v instanceof RadioButton) {
            checked = ((RadioButton) v).isChecked();
        }

        if(v instanceof CheckBox){
            checked = ((CheckBox)v).isChecked();
        }

        switch (v.getId()) {
            case R.id.normal:

                if(checked){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }

                break;

            case R.id.statellite:

                if(checked){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }

                break;

            case R.id.traffice:
                mBaiduMap.setTrafficEnabled(checked);
                break;

            case R.id.baiduHeatMap:
                mBaiduMap.setBaiduHeatMapEnabled(checked);
                break;
        }
    }


}
