package studio.sinya.jp.demo_map_baidu.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

/**
 * 普通地图
 * author : Sinya Koizumi
 * createDate ：2015/9/30 9:32
 * editor：
 * updateDate：2015/9/30 9:32
 */
public class BaseMapActivity extends Activity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //用来获取传入的坐标，如果有，则直接绘制当前坐标为地图中心点
        if (intent.hasExtra("x") && intent.hasExtra("y")) {

            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();

            //将两个double参数转换成LatLng对象 latitude 纬度; longitude 经度
            LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));

            //target 传入一个Latlng对象，设置地图显示的中心坐标
            mMapView = new MapView(this, new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(p).build()));
        } else {
            mMapView = new MapView(this, new BaiduMapOptions());
        }
        setContentView(mMapView);

        mBaiduMap = mMapView.getMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }

}
