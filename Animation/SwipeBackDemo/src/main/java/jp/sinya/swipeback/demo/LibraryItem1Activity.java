package jp.sinya.swipeback.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.sinya.swipeback.demo.library.Slidr;

public class LibraryItem1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_item1);
        Slidr.attach(this);
    }

    public void back(View view) {
        finish();
    }
}
