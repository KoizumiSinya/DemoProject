package studio.sinya.jp.demo_map_baidu.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import studio.sinya.jp.demo_map_baidu.R;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/30 15:14
 * editor：
 * updateDate：2015/9/30 15:14
 */
public class ControlMapActivity extends Activity {

    private Context context;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private LatLng currentLatLng;

    private Button zoomBtn;
    private Button rotateBtn;
    private Button overlookBtn;
    private Button saveScreenBtn;

    private String touchType;

    private TextView mStateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_control_map);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        mStateBar = (TextView) findViewById(R.id.state);
        setListener();
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

    private void setListener() {

        //地图触摸监听
        mBaiduMap.setOnMapTouchListener(new OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {

            }
        });

        //地图点击监听
        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                touchType = "one Click";
                currentLatLng = latLng;
                updateMapState();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        //地图长按监听
        mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                touchType = "long Click";
                currentLatLng = latLng;
                updateMapState();
            }
        });

        //地图双击监听
        mBaiduMap.setOnMapDoubleClickListener(new OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {
                touchType = "double Click";
                currentLatLng = latLng;
                updateMapState();
            }
        });

        //地图状态改变监听
        mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                updateMapState();
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                updateMapState();
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                updateMapState();
            }
        });


        zoomBtn = (Button) findViewById(R.id.zoombutton);
        rotateBtn = (Button) findViewById(R.id.rotatebutton);
        overlookBtn = (Button) findViewById(R.id.overlookbutton);
        saveScreenBtn = (Button) findViewById(R.id.savescreen);

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.equals(zoomBtn)) {
                    perfomZoom();
                } else if (view.equals(rotateBtn)) {
                    perfomRotate();
                } else if (view.equals(overlookBtn)) {
                    perfomOverlook();
                } else if (view.equals(saveScreenBtn)) {
                    // 截图，在SnapshotReadyCallback中保存图片到 sd 卡
                    mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                        public void onSnapshotReady(Bitmap snapshot) {
                            File file = new File("/mnt/sdcard/test.png");
                            FileOutputStream out;
                            try {
                                out = new FileOutputStream(file);
                                if (snapshot.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                                    out.flush();
                                    out.close();
                                }
                                Toast.makeText(context, "屏幕截图成功，图片存在: " + file.toString(), Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(context, "正在截取屏幕图片...", Toast.LENGTH_SHORT).show();

                }
                updateMapState();
            }

        };
        zoomBtn.setOnClickListener(onClickListener);
        rotateBtn.setOnClickListener(onClickListener);
        overlookBtn.setOnClickListener(onClickListener);
        saveScreenBtn.setOnClickListener(onClickListener);
    }

    /**
     * 更新地图显示面板
     */
    private void updateMapState() {
        if (mStateBar == null) {
            return;
        }

        String state = "";
        if (currentLatLng == null) {
            state = "点击、长按、双击地图，可以获取经纬度和地图状态";
        } else {
            state = String.format(touchType + ";\n" + "当前经度: %f 当前纬度: %f", currentLatLng.latitude, currentLatLng.longitude);
        }

        state += "\n";
        MapStatus status = mBaiduMap.getMapStatus();
        state += String.format("zoom=%.1f ratate=%d overlook=%d", status.zoom, (int) status.rotate, (int) status.overlook);
        mStateBar.setText(state);
    }

    /**
     * 地图俯视角度
     * -45 ~ 0
     */
    private void perfomOverlook() {
        EditText t = (EditText) findViewById(R.id.overlookangle);
        try {
            int overlookAngle = Integer.parseInt(t.getText().toString());
            MapStatus status = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(overlookAngle).build();
            MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
            mBaiduMap.setMapStatus(update);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "俯视角度有误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 处理地图旋转
     * 范围 -180 ~ 180
     */
    private void perfomRotate() {
        EditText t = (EditText) findViewById(R.id.rotateangle);
        try {
            int rotateAngle = Integer.parseInt(t.getText().toString());
            MapStatus status = new MapStatus.Builder(mBaiduMap.getMapStatus()).rotate(rotateAngle).build();
            MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
            mBaiduMap.animateMapStatus(update);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "旋转角度有误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    /**
     * 处理地图缩放
     * 3.0 ~ 19.0
     */
    private void perfomZoom() {
        EditText t = (EditText) findViewById(R.id.zoomlevel);
        try {
            float zoomLevel = Float.parseFloat(t.getText().toString());
            MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(zoomLevel);
            mBaiduMap.animateMapStatus(update);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "地图缩放级别有误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
