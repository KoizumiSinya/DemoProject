package studio.sinya.jp.demo_httpbase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Context context;
    private TextView title;
    private TextView content;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                //使用安卓原生的JSON解析单个对象的json数据
                case 0:

                    if (msg.obj != null) {

                        try {
                            JSONObject json = new JSONObject((String) msg.obj);
                            String name = json.getString("name");
                            int age = json.getInt("age");
                            String work = json.getString("work");

                            title.setText("HttpURLConnection请求:");
                            content.setText(name + " - " + age + " - " + work);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    break;

                //使用apache的http请求
                case 1:

                    if (msg.obj != null) {
                        String result = (String) msg.obj;
                        title.setText("HttpClient请求:");
                        content.setText(result);
                    }

                    break;

                //使用安卓原生的JSON解析 对数组型的json数据进行解析
                case 2:
                    if (msg.obj != null) {
                        try {
                            JSONArray jsonArray = new JSONArray((String) msg.obj);

                            StringBuilder builder = new StringBuilder();

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject json = jsonArray.getJSONObject(i);
                                String name = json.getString("name");
                                int age = json.getInt("age");
                                String work = json.getString("work");

                                builder.append(name + " - " + age + " - " + work +" ; ");

                            }

                            title.setText("HttpURLConnection请求，并使用JSON原生解析数组型的json数据: :");
                            content.setText(builder.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                // HttpURLConnectionGet("http://10.0.2.2:8080/QQJY/test/a.json");
                HttpURLConnectionGet("http://10.0.2.2:8080/QQJY/test/jsonarray.json");
                break;

            case R.id.btn2:
                HttpClientGet("http://www.baidu.com");
                break;

        }
    }

    private void HttpURLConnectionGet(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try {
                    URL url = new URL(str);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {

                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        StringBuilder builder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }

                        Message message = new Message();
                        message.what = 2;
                        message.obj = builder.toString();
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void HttpClientGet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);

                    HttpResponse response = httpClient.execute(httpGet);
                    int responseCode = response.getStatusLine().getStatusCode();

                    if (responseCode == 200) {
                        HttpEntity entity = response.getEntity();
                        String result = EntityUtils.toString(entity, "UTF-8");

                        Message message = new Message();
                        message.what = 1;
                        message.obj = result;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
