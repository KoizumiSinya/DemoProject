package jp.sinya.circlelistview.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnVerticalClick(View v) {
        startActivity(new Intent(this, VerticalActivity.class));
    }

    public void OnHorizontalClick(View v) {
        startActivity(new Intent(this, HorizontalActivity.class));
    }

    public void OnGalleryClick(View v) {
        startActivity(new Intent(this, GalleryActivity.class));
    }

}
