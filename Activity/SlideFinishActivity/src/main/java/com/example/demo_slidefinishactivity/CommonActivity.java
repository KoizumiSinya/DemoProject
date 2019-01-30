package com.example.demo_slidefinishactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.demo_slidefinishactivity.widget.SlideFinishLayout;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        ((SlideFinishLayout) findViewById(R.id.root_layout)).setFinishListener(new SlideFinishLayout.onSlideFinishListener() {
            @Override
            public void onSlideFinish() {
                CommonActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        });

    }

}
