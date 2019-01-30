package studio.sinya.jp.demo_httpframwork2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import studio.sinya.jp.demo_httpframwork2.R;
import studio.sinya.jp.demo_httpframwork2.http.MyHttpClient;
import studio.sinya.jp.demo_httpframwork2.http.MyHttpConfig;

/**
 * 用来模拟请求 百度网站 的信息
 * author : Sinya Koizumi
 * createDate ：2015/9/22 16:58
 * editor：
 * updateDate：2015/9/22 16:58
 */
public class HttpDemoActivity extends Activity {

    private Button btn1;
    private TextView content;
    private ProgressBar bar;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            bar.setVisibility(View.INVISIBLE);
            content.setVisibility(View.VISIBLE);
            switch (msg.what) {
                case 0:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        content.setText(str);
                    }else{
                        content.setText("数据请求失败，请稍后重试");
                    }
                    break;
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demo);

        content = (TextView) findViewById(R.id.txt_content);

        bar = (ProgressBar) findViewById(R.id.progress);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                content.setVisibility(View.INVISIBLE);
                //MyHttpClient.request(handler, 0, MyHttpConfig.baidu);
                MyHttpClient.request(handler, 0, MyHttpConfig.baidu2);
            }
        });

    }
}
