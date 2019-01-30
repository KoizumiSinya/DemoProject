package jp.sinya.pathstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jp.sinya.pathstudy.activity.AddArcActivity;
import jp.sinya.pathstudy.activity.ArcToActivity;
import jp.sinya.pathstudy.activity.LineToActivity;
import jp.sinya.pathstudy.activity.PathByDashActivity;
import jp.sinya.pathstudy.activity.PathMeasureTestActivity;
import jp.sinya.pathstudy.activity.PathOpActivity;
import jp.sinya.pathstudy.activity.PathPosTanActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lineTo(View view) {
        startActivity(new Intent(this, LineToActivity.class));
    }

    public void arcTo(View view) {
        startActivity(new Intent(this, ArcToActivity.class));
    }

    public void addArc(View view) {
        startActivity(new Intent(this, AddArcActivity.class));
    }

    public void pathOp(View view) {
        startActivity(new Intent(this, PathOpActivity.class));
    }

    public void pathMeasure(View view) {
        startActivity(new Intent(this, PathMeasureTestActivity.class));
    }

    public void pathDash(View view) {
        startActivity(new Intent(this, PathByDashActivity.class));
    }
    public void pathPosTan(View view) {
        startActivity(new Intent(this, PathPosTanActivity.class));
    }
}
