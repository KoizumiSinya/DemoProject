package com.qqjy.demo_socketbase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener {

    private Context context;
    private Button btn;
    private TextView content;
    private ProgressBar pb;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    content.setText((String) msg.obj);
                    pb.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        content = (TextView) findViewById(R.id.content);
        pb = (ProgressBar) findViewById(R.id.progress);
        btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                content.setText("");
                pb.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.1.128", 9955);
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String line = br.readLine();

                            Message message = new Message();
                            message.obj = line;
                            handler.sendMessageDelayed(message, 800);

                            br.close();
                            socket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                }.start();
                break;

            default:
                break;
        }
    }
}
