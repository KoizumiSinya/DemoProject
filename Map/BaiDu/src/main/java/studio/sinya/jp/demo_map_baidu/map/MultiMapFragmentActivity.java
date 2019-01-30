package studio.sinya.jp.demo_map_baidu.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

import studio.sinya.jp.demo_map_baidu.R;

/**
 * 一个界面上 划分4个区域显示地图
 * author : Sinya Koizumi
 * createDate ：2015/9/30 14:56
 * editor：
 * updateDate：2015/9/30 14:56
 */
public class MultiMapFragmentActivity extends FragmentActivity {

    //模拟四个坐标位置
    private static final LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
    private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    private static final LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
    private static final LatLng GEO_SHENGZHENG = new LatLng(22.560, 114.064);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimap);

        initMap();
    }

    private void initMap() {

        MapStatusUpdate mapUpdate1 = MapStatusUpdateFactory.newLatLng(GEO_BEIJING);
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment1.getBaiduMap().setMapStatus(mapUpdate1);

        MapStatusUpdate mapUpdate2 = MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
        SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment2.getBaiduMap().setMapStatus(mapUpdate2);

        MapStatusUpdate mapUpdate3 = MapStatusUpdateFactory.newLatLng(GEO_GUANGZHOU);
        SupportMapFragment mapFragment3 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3);
        mapFragment3.getBaiduMap().setMapStatus(mapUpdate3);

        MapStatusUpdate mapUpdate4 = MapStatusUpdateFactory.newLatLng(GEO_SHENGZHENG);
        SupportMapFragment mapFragment4 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map4);
        mapFragment4.getBaiduMap().setMapStatus(mapUpdate4);
    }
}
