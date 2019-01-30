package sinya.jp.demo_map_gaode.map;

import android.os.Bundle;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;

import sinya.jp.demo_map_gaode.R;

/**
 * Created by SinyaKoizumi on 2015/9/22.
 */
public class GridViewActivity extends MapActivity {

    private MapView mMapView;
    private MapController mMapController;
    private GeoPoint mPoint;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_grid_map);

        mMapView = (MapView) findViewById(R.id.mapview);
        //设置缩放控件显示
        mMapView.setBuiltInZoomControls(true);

        //得到mapView的控件操作权（缩放 移动）
        mMapController = mMapView.getController();

        //假设构造一个经纬点（单位是微度 * 1E6）
        mPoint = new GeoPoint((int)(35.3421 * 1E6), (int)(110.34242 * 1E6));

        //把当前地图的界面移动到这个点上
        mMapController.setCenter(mPoint);

        //设置比例尺级别
        mMapController.setZoom(12);
    }
}
