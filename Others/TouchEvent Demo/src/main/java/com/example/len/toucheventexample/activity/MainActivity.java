package com.example.len.toucheventexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.len.toucheventexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_normal_view = (Button) findViewById(R.id.bt_normal_view);
        bt_normal_view.setOnClickListener(this);

        Button bt_button = (Button) findViewById(R.id.bt_button);
        bt_button.setOnClickListener(this);

        Button bt_scroll_View = (Button) findViewById(R.id.bt_scroll_View);
        bt_scroll_View.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_normal_view:
                startActivity(TestNormalViewActivity.class);
                break;
            case R.id.bt_button:
                startActivity(TestButtonActivity.class);
                break;
            case R.id.bt_scroll_View:
                startActivity(TestScrollViewActivity.class);
                break;
        }
    }

    void startActivity(Class<?> cls){
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
