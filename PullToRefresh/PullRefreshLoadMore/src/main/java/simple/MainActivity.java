package simple;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sinya.demo_pullrefreshloadmore.R;

/**
 * Created by Koizumi Sinya on 2015/12/25.
 */
public class MainActivity extends ListActivity {

    private String[] texts;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        texts = this.getResources().getStringArray(R.array.list_menu);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, texts));
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    }
}
