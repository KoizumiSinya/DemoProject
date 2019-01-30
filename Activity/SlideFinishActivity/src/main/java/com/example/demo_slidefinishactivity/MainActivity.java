package com.example.demo_slidefinishactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    public void click(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn1:
                intent = new Intent(context, ViewPagerActivity.class);

            break;

            case R.id.btn2:
                intent = new Intent(context, CommonActivity.class);
                break;

            default:
                break;
        }

        startActivity(intent);
    }
}
