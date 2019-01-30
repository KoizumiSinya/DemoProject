package jp.sinya.shareelementanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

public class ExplodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explode);
        Explode explode = new Explode();
        explode.setDuration(1000);
        getWindow().setEnterTransition(explode);
    }
}
