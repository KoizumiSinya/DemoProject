package studio.sinya.jp.demo_httpframwork2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studio.sinya.jp.demo_httpframwork2.R;
import studio.sinya.jp.demo_httpframwork2.adapter.MyAdapter;
import studio.sinya.jp.demo_httpframwork2.bean.Person;
import studio.sinya.jp.demo_httpframwork2.bean.PersonRsult;
import studio.sinya.jp.demo_httpframwork2.http.MyHttpClient;
import studio.sinya.jp.demo_httpframwork2.http.MyHttpConfig;

/**
 * 用来模拟两种Json数据格式（对象 、数组）
 * author : Sinya Koizumi
 * createDate ：2015/9/22 16:58
 * editor：
 * updateDate：2015/9/22 16:58
 */
public class JsonDemoActivity extends Activity {
    private Context context;

    private Button btn1, btn2;
    private TextView content;
    private ScrollView scrollView;
    private ProgressBar bar;
    private ListView listView;

    private List<Person> result;
    private MyAdapter adapter;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case 0:
                    if (msg.obj != null) {
                        PersonRsult data = (PersonRsult) msg.obj;
                        result = data.items;
                    } else {
                        result = new ArrayList<>();
                    }

                    adapter.setData(result);
                    adapter.notifyDataSetChanged();
                    setListViewVisible();
                    break;

                case 1:
                    if (msg.obj != null) {
                        Person person = (Person) msg.obj;
                        String str = person.name + " - " + person.age + " - " + person.work;
                        content.setText(str);
                    } else {
                        content.setText("数据请求失败，请稍后重试");
                    }
                    setContentVisible();
                    break;
            }

            bar.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_demo);
        context = this;

        scrollView = (ScrollView) findViewById(R.id.scroll);
        content = (TextView) findViewById(R.id.txt_content);

        bar = (ProgressBar) findViewById(R.id.progress);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                MyHttpClient.request(handler, 1, MyHttpConfig.personGet);

            }
        });

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                MyHttpClient.request(handler, 0, MyHttpConfig.arrayJson);

            }
        });

        listView = (ListView) findViewById(R.id.listView);

        result = new ArrayList<>();
        adapter = new MyAdapter(context, result);
        listView.setAdapter(adapter);
    }

    private void setContentVisible() {
        listView.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void setListViewVisible() {
        listView.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }
}
