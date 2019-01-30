package javaproject.listfristloaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static Map<Integer, Boolean> dataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCheck();
    }

    private void initCheck() {
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.getId() == R.id.activity_main_check1) {
                    dataMap.put(1, b);
                } else if (compoundButton.getId() == R.id.activity_main_check2) {
                    dataMap.put(2, b);
                } else if (compoundButton.getId() == R.id.activity_main_check3) {
                    dataMap.put(3, b);
                } else if (compoundButton.getId() == R.id.activity_main_check4) {
                    dataMap.put(4, b);
                }
                Log.i("Sinya", MainActivity.dataMap.toString());
            }
        };

        CheckBox checkBox = findViewById(R.id.activity_main_check1);
        CheckBox checkBox2 = findViewById(R.id.activity_main_check2);
        CheckBox checkBox3 = findViewById(R.id.activity_main_check3);
        CheckBox checkBox4 = findViewById(R.id.activity_main_check4);

        checkBox.setOnCheckedChangeListener(checkListener);
        checkBox2.setOnCheckedChangeListener(checkListener);
        checkBox3.setOnCheckedChangeListener(checkListener);
        checkBox4.setOnCheckedChangeListener(checkListener);
    }

    public void open(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
