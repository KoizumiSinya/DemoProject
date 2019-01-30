package jp.sinya.knobview;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

    private String[] items = {"Sample1", "Sample2", "Sample3", "Sample4", "Sample5", "Sample6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, SampleActivity1.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, SampleActivity2.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, SampleActivity3.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, SampleActivity4.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, SampleActivity5.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
