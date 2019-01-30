package jp.sinya.swipeback.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.sinya.swipeback.demo.library.Slidr;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Slidr.attach(this);
    }

    public void start(View view) {
        Intent intent = new Intent(this, LibraryItem1Activity.class);
        startActivity(intent);
    }
}
