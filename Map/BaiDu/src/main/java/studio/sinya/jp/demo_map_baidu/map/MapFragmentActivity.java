package studio.sinya.jp.demo_map_baidu.map;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;

import studio.sinya.jp.demo_map_baidu.R;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/30 11:59
 * editor：
 * updateDate：2015/9/30 11:59
 */
public class MapFragmentActivity extends FragmentActivity{

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_map);


        //overlook：地图俯仰角度; zoom：地图缩放级别(范围3-20);
        MapStatus mapStatus = new MapStatus.Builder().overlook(-20).zoom(15).build();

        //compassEnabled：设置指南针; zoomControlsEnabled：设置缩放比例尺
        BaiduMapOptions options = new BaiduMapOptions().mapStatus(mapStatus).compassEnabled(false).zoomControlsEnabled(false);

        mapFragment = SupportMapFragment.newInstance(options);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map_frame, mapFragment, "map_fragment").commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
