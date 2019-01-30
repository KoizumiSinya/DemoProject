package jp.sinya.stickylistviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Example activity.
 */
public class SectionListActivity extends Activity {

    SectionListItem[] exampleArray = {//
            new SectionListItem("Test 1 - A", "A"),//
            new SectionListItem("Test 2 - A", "A"),//
            new SectionListItem("Test 3 - A", "A"),//
            new SectionListItem("Test 4 - B", "B"),//
            new SectionListItem("Test" + " 5 - B", "B"),//
            new SectionListItem("Test 6 - C", "C"),//
            new SectionListItem("Test 7 - D", "D"),//
            new SectionListItem("Test 8 - D", "D"),//
            new SectionListItem("Test 9 - E", "A"),//
            new SectionListItem("Test 10 - E", "B"),//
            new SectionListItem("Test 11 - E", "B"), //
            new SectionListItem("Test 12 - E", "E"),//
            new SectionListItem("Test 13 - E", "E"),//
            new SectionListItem("Test" + " 14 - E", "E"),//
            new SectionListItem("Test 15 - E", "E"),//
            new SectionListItem("Test 16 - E", "E"),//
            new SectionListItem("Test 17 - E", "E"),//
            new SectionListItem("Test 18 - E", "E"),//
            new SectionListItem("Test 19 - E", "E"),//
            new SectionListItem("Test 20 - E", "E"),//
            new SectionListItem("Test 21 - E", "E"),//
            new SectionListItem("Test 22 - E", "E"),//
            new SectionListItem("Test" + " 23 - E", "E"),//
            new SectionListItem("Test 24 - E", "E"),//
            new SectionListItem("Test 25 - E", "E"),//
            new SectionListItem("Test 26 - E", "E"), //
            new SectionListItem("Test 27 - D", "F"),};

    private StandardArrayAdapter arrayAdapter;

    private SectionListAdapter sectionAdapter;

    private PinnedHeaderListView listView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        arrayAdapter = new StandardArrayAdapter(this, R.id.example_text_view, exampleArray);
        sectionAdapter = new SectionListAdapter(getLayoutInflater(), arrayAdapter);
        listView = (PinnedHeaderListView) findViewById(R.id.section_list_view);
        sectionAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SectionListActivity.this, "点击了" + exampleArray[position].toString(), Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(sectionAdapter);
        listView.setOnScrollListener(sectionAdapter);
        listView.setPinnedHeaderView(getLayoutInflater().inflate(R.layout.list_section, listView, false));
    }

    public class StandardArrayAdapter extends ArrayAdapter<SectionListItem> {

        final SectionListItem[] items;

        public StandardArrayAdapter(final Context context, final int textViewResourceId, final SectionListItem[] items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

    }
}