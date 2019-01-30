package jp.sinya.demo.scaleruleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ScaleRulerView scaleRulerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleRulerView = findViewById(R.id.scale_rule_view);
    }

    public void add(View view) {
        scaleRulerView.setSelectorValue(scaleRulerView.getSelectorValue() + 100);
    }

    public void subtract(View view) {
        scaleRulerView.setSelectorValue(scaleRulerView.getSelectorValue() - 100);
    }
}
