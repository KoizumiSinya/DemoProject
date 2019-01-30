package studio.sinya.jp.demo_map_baidu.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.view.View.OnClickListener;

import com.baidu.mapapi.map.BitmapDescriptorFactory;

import studio.sinya.jp.demo_map_baidu.R;

/**
 * 定位功能
 * author : Sinya Koizumi
 * createDate ：2015/9/30 16:43
 * editor：
 * updateDate：2015/9/30 16:43
 */
public class LocationActivity extends Activity {

    private Context context;

    private LocationClient mLocationClient;
    public MyLocationListener myListener = new MyLocationListener();
    private LocationMode mCurrentMode;

    private BitmapDescriptor mCurrentMarker;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private OnCheckedChangeListener radioBtnListener;
    private Button requestLocationBtn;

    //是否是首次定位
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_location_map);

        requestLocationBtn = (Button) findViewById(R.id.button1);
        mCurrentMode = LocationMode.NORMAL;
        requestLocationBtn.setText("普通");

        OnClickListener btnOncClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode) {

                    case NORMAL:
                        requestLocationBtn.setText("跟随");
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;

                    case COMPASS:
                        requestLocationBtn.setText("普通");
                        mCurrentMode = LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;

                    case FOLLOWING:
                        requestLocationBtn.setText("罗盘");
                        mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;
                }
            }
        };

        requestLocationBtn.setOnClickListener(btnOncClickListener);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioBtnListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.defaulticon) {
                    mCurrentMarker = null;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
                }

                if (checkedId == R.id.customicon) {
                    mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_geo);
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                }
            }
        };

        radioGroup.setOnCheckedChangeListener(radioBtnListener);

        initMapView();
    }

    private void initMapView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //设置定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //初始化定位
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        //设置坐标类型
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);

        mLocationClient.setLocOption(option);
        mLocationClient.start();
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
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public class MyLocationListener implements BDLocationListener {

        public void onReceivePoi(BDLocation poiLocation) {

        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mMapView == null) {
                return;
            }

            MyLocationData locationData = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).direction(100).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();

            mBaiduMap.setMyLocationData(locationData);

            if (isFirst) {
                isFirst = !isFirst;

                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(update);
            }
        }
    }
}


