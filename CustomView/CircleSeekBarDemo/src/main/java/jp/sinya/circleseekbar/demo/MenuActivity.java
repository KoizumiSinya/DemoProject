package jp.sinya.circleseekbar.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void click1(View view) {
        startActivity(new Intent(this, ClickNormalActivity.class));
    }

    public void click2(View view) {
        startActivity(new Intent(this, DemoActivity2.class));
    }

    public void click3(View view) {
        startActivity(new Intent(this, DemoActivity3.class));
    }

    public void click4(View view) {
        startActivity(new Intent(this, ClickNormalActivity.class));
    }
}
