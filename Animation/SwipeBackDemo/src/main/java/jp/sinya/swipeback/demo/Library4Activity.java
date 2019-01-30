package jp.sinya.swipeback.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.sinya.swipeback.demo.library4.SwipeBaseActivity;

public class Library4Activity extends SwipeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library4);
    }

    public void start(View view) {
        Intent intent = new Intent(this, Library4ItemActivity.class);
        startActivity(intent);
    }
}
