package studio.sinya.jp.demo_json;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import studio.sinya.jp.demo_json.adapter.MyAdapter;
import studio.sinya.jp.demo_json.bean.Person;
import studio.sinya.jp.demo_json.bean.PersonRsult;


public class MainActivity extends Activity {

    private Context context;

    private ProgressBar bar;
    private Button btn1;
    private ListView listView;

    private List<Person> result;
    private MyAdapter adapter;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    if (msg.obj != null) {

                        parseJson((String) msg.obj);
//                        parseJsonForGSon((String) msg.obj);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        bar = (ProgressBar) findViewById(R.id.progress);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                getData("http://10.0.2.2:8080/QQJY/test/chat_list.json");
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        result = new ArrayList<>();
        adapter = new MyAdapter(context, result);
        listView.setAdapter(adapter);
    }


    private void getData(final String str) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conection = null;

                try {

                    URL url = new URL(str);
                    conection = (HttpURLConnection) url.openConnection();

                    conection.setRequestMethod("GET");
                    conection.setConnectTimeout(5000);
                    conection.setReadTimeout(5000);

                    int responseCode = conection.getResponseCode();

                    if (responseCode == 200) {

                        InputStream in = conection.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder builder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }

                        Message message = new Message();
                        message.what = 0;
                        message.obj = builder.toString();

                        // handler.sendMessage(message);

                        //handler设置延时1000毫秒之后发送message消息（模拟请求数据时候的缓冲）
                        handler.sendMessageDelayed(message, 1000);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJson(String str) {
        //①解析数据 并 将数据传递给基类
        try {

            result = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(str);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                Person bean = new Person();

                JSONObject jsonObj = items.getJSONObject(i);

                bean.name = jsonObj.getString("name");
                bean.data = jsonObj.getString("data");
                bean.message = jsonObj.getString("message");

                result.add(bean);
            }

            //②装填ListView
            if (result != null && result.size() > 0) {
                adapter.setData(result);
                adapter.notifyDataSetChanged();
                bar.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void parseJsonForGSon(String str) {

        Gson gson = new Gson();
        PersonRsult data = gson.fromJson(str, PersonRsult.class);

        adapter.setData(data.items);
        adapter.notifyDataSetChanged();
        bar.setVisibility(View.INVISIBLE);
    }

}
