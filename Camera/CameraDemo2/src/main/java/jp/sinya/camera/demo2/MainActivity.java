package jp.sinya.camera.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.sinya.camera.demo2.picture.CameraPictureActivity;
import jp.sinya.camera.demo2.video.CameraVideoActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void picture(View view) {
        startActivity(new Intent(this, CameraPictureActivity.class));
    }

    public void video(View view) {
        startActivity(new Intent(this, CameraVideoActivity.class));
    }
}
