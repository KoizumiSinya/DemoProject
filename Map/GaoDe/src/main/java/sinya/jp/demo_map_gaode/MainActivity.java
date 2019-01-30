package sinya.jp.demo_map_gaode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sinya.jp.demo_map_gaode.map.GridViewActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goActivity(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(this, GridViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
