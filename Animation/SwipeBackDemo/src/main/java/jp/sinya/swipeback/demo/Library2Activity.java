package jp.sinya.swipeback.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.sinya.swipeback.demo.library2.SwipeBackActivity;

public class Library2Activity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library2);
    }

    public void start(View view) {
        Intent intent = new Intent(this, Library2FragmentActivity.class);
        startActivity(intent);
    }
}
