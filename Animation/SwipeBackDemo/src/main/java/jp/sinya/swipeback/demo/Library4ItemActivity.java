package jp.sinya.swipeback.demo;

import android.os.Bundle;
import android.view.View;

import jp.sinya.swipeback.demo.library4.SwipeBaseActivity;

public class Library4ItemActivity extends SwipeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library4_item);
    }

    public void back(View view) {
        finish();
    }
}
