package jp.sinya.camera.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import jp.sinya.camera.demo2.picture.CameraPictureActivity;
import jp.sinya.camera.demo2.silent.CaptureService;
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

    public void silent(View view) {
        Window w =getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        Intent intent = new Intent(this, CaptureService.class);
        intent.putExtra("rotation", getWindowManager().getDefaultDisplay().getRotation());
        startService(intent);
    }
}
