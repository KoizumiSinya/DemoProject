package studio.sinya.jp.demo_httpframwork2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import studio.sinya.jp.demo_httpframwork2.activity.HttpDemoActivity;
import studio.sinya.jp.demo_httpframwork2.activity.JsonDemoActivity;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goActivity(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent1 = new Intent(this, HttpDemoActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn2:
                Intent intent2 = new Intent(this, JsonDemoActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
