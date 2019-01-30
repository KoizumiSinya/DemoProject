package jp.sinya.swipeback.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start1(View view) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }

    public void start2(View view) {
        Intent intent = new Intent(this, Library2Activity.class);
        startActivity(intent);
    }

    public void start3(View view) {
        Intent intent = new Intent(this, Library3Activity.class);
        startActivity(intent);
    }

    public void start4(View view) {
        Intent intent = new Intent(this, Library4Activity.class);
        startActivity(intent);
    }
}
