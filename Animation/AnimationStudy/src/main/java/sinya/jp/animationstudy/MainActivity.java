package sinya.jp.animationstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);
        List<String> strings = new ArrayList<>();
        strings.add("VectorDrawable 实现箭头左右运动动画");
        strings.add("VectorDrawable 实现变色动画");
        strings.add("VectorDrawable 轨迹动画");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, ArrowActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case 1: {
                        Intent intent = new Intent(MainActivity.this, ColorChangeActivity.class);
                        startActivity(intent);
                    }
                    case 2: {
                        Intent intent = new Intent(MainActivity.this, TrimPathActivity.class);
                        startActivity(intent);
                    }
                    break;
                    default:
                        break;
                }
            }
        });
    }
}
