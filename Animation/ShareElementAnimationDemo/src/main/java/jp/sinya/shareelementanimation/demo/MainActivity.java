package jp.sinya.shareelementanimation.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startExplode(View view) {

        startActivity(new Intent(this, ExplodeActivity.class));


    }

    public void startSlide(View view) {

    }

    public void startFade(View view) {

    }

    public void startShare(View view) {
        startActivity(new Intent(this, ShareActivity.class));
    }

    public void start(View view) {

    }

    public void start6(View view) {

    }

}
