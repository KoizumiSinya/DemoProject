package jp.sinya.bezierstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.sinya.bezierstudy.activity.BezierVectorDemoActivity;
import jp.sinya.bezierstudy.activity.SecondBezierActivity;
import jp.sinya.bezierstudy.activity.ShopCarBezierActivity;
import jp.sinya.bezierstudy.activity.ThridBezierActivity;
import jp.sinya.bezierstudy.activity.Wave2Activity;
import jp.sinya.bezierstudy.activity.WaveActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickSecondBezier(View v) {
        Intent intent = new Intent(this, SecondBezierActivity.class);
        startActivity(intent);
    }

    public void clickThirdBezier(View v) {
        Intent intent = new Intent(this, ThridBezierActivity.class);
        startActivity(intent);
    }

    public void clickBezierVector(View v) {
        Intent intent = new Intent(this, BezierVectorDemoActivity.class);
        startActivity(intent);
    }

    public void clickWave(View v) {
        Intent intent = new Intent(this, WaveActivity.class);
        startActivity(intent);
    }

    public void clickWave2(View v) {
        Intent intent = new Intent(this, Wave2Activity.class);
        startActivity(intent);
    }

    public void clickShopCard(View v) {
        Intent intent = new Intent(this, ShopCarBezierActivity.class);
        startActivity(intent);
    }
}
