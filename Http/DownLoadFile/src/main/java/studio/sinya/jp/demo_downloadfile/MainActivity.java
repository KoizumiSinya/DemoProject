package studio.sinya.jp.demo_downloadfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import studio.sinya.jp.demo_downloadfile.download.DownLoadActivity;
import studio.sinya.jp.demo_downloadfile.download.Download1Activity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void down(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn1:
                intent = new Intent(MainActivity.this, DownLoadActivity.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                intent = new Intent(MainActivity.this, Download1Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
