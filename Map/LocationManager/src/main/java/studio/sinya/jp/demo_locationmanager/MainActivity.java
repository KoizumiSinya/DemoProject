package studio.sinya.jp.demo_locationmanager;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends Activity {

    private TextView positionText;
    private LocationManager manager;
    private String provider;
    private Location location;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String currentAddressDeatil = (String) msg.obj;
                    positionText.setText(currentAddressDeatil);
                    break;
            }

            return false;
        }
    });

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        positionText = (TextView) findViewById(R.id.location);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getData();

        manager.requestLocationUpdates(provider, 5000, 1, locationListener);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.removeUpdates(locationListener);
        }
    }

    private void showLocation(Location location) {
        String currentLocation = "Latitude: " + location.getLatitude() + "; \nLongitude: " + location.getLongitude();
        positionText.setText(currentLocation);
    }

    public void getLocation(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                getData();
                break;

            case R.id.btn2:
                getDeatil(location);
                break;
        }
        getData();
    }

    public void getData() {

        //获取所有可以适用的位置提供器
        List<String> providerList = manager.getProviders(true);

        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "无法获取地址", Toast.LENGTH_SHORT).show();
            return;
        }

        location = manager.getLastKnownLocation(provider);

        if (location != null) {
            showLocation(location);
        } else {
            Toast.makeText(this, "获取的地址信息为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void getDeatil(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder url = new StringBuilder();
                    url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                    url.append(location.getLatitude()).append(",");
                    url.append(location.getLongitude());
                    url.append("&sensor=false");

                    HttpClient httpClient = new DefaultHttpClient();
                    Log.i("Sinya", "请求的url:" + url.toString());
                    HttpGet httpGet = new HttpGet(url.toString());

                    httpGet.addHeader("Accept-Language", "zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "UTF-8");
                        Log.i("Sinya", "服务器返回的数据:" + response);

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultArray = jsonObject.getJSONArray("results");

                        if (resultArray.length() > 0) {
                            JSONObject subObject = resultArray.getJSONObject(0);
                            String address = subObject.getString("formatted_address");

                            Message message = new Message();
                            message.what = 0;
                            message.obj = address;
                            handler.sendMessage(message);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
