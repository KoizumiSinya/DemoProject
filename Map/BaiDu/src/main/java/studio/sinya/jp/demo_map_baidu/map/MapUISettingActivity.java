package studio.sinya.jp.demo_map_baidu.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;

import studio.sinya.jp.demo_map_baidu.R;

/**
 *
 * 操控地图上的UI控件
 * author : Sinya Koizumi
 * createDate ：2015/9/30 16:27
 * editor：
 * updateDate：2015/9/30 16:27
 */
public class MapUISettingActivity extends Activity{

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uisetting_map);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();

        MapStatus status = new MapStatus.Builder().overlook(-30).build();
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
        mBaiduMap.animateMapStatus(update, 1000);

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

    /**
     * 是否启用缩放手势
     * @param view
     */
    public void setZoomEnable(View view) {
        mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 是否启用平移手势
     * @param view
     */
    public void setScrollEnable(View view) {
        mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 师傅哦启用旋转手势
     * @param view
     */
    public void setRotateEnable(View view) {
        mUiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 是否启用俯视手势
     * @param view
     */
    public void setOverlookEnable(View view) {
        mUiSettings.setOverlookingGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * 是否启用指南针
     * @param view
     */
    public void setCompassEnable(View view) {
        mUiSettings.setCompassEnabled(((CheckBox)view).isChecked());
    }
}
