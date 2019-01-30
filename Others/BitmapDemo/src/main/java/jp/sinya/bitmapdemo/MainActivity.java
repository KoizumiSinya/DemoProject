package jp.sinya.bitmapdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView view = findViewById(R.id.imageview);
        MyImageLoader.getInstance().displayImage(view, "http://192.168.1.5:8080/Sinya/maplestory.jpeg");
    }
}
