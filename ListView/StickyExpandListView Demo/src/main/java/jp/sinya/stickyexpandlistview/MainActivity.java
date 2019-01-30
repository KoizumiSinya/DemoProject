package jp.sinya.stickyexpandlistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements SectionIndexer {

    private Context context;

    private ExpandableListView mExpandableListView;
    private LinearLayout titleLayout;
    private TextView titleView;

    private List<Student> dataList;
    private StudentListAdapter adapter;

    private int lastFirstVisibleItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initData();

        titleLayout = (LinearLayout) findViewById(R.id.ll_title);
        titleView = (TextView) findViewById(R.id.tv_title);

        adapter = new StudentListAdapter(dataList, context);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_listview);
        mExpandableListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }

        mExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });


        mExpandableListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);

                if (firstVisibleItem != lastFirstVisibleItem) {
                    MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    if (dataList.size() > 0) {
                        titleView.setText(dataList.get(getPositionForSection(section)).letter);
                    }
                }

                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;

            }
        });
    }

    private void initData() {
        dataList = new ArrayList<>();
        dataList.add(new Student("JLSDJF", "A"));
        dataList.add(new Student("UKJHSF", "A"));
        dataList.add(new Student("SDF", "A"));
        dataList.add(new Student("MNLKSDF", "C"));
        dataList.add(new Student("RETER", "G"));
        dataList.add(new Student("ACAC", "G"));
        dataList.add(new Student("Make", "G"));
        dataList.add(new Student("OFSFOSD", "G"));
        dataList.add(new Student("RTHGDFH", "Y"));
        dataList.add(new Student("FFT", "Y"));
        dataList.add(new Student("NJUY", "Y"));
        dataList.add(new Student("SFSD", "Y"));
        dataList.add(new Student("EDFRG", "U"));
        dataList.add(new Student("WSXSW", "U"));
        dataList.add(new Student("QWSW", "U"));
        dataList.add(new Student("RDEFR", "O"));
        dataList.add(new Student("SZXSW", "O"));
        dataList.add(new Student("FTGRG", "O"));
        dataList.add(new Student("YTYTGY", "O"));
        dataList.add(new Student("WEWEWS", "O"));
        dataList.add(new Student("QAWSED", "O"));
        dataList.add(new Student("ZXSDC", "O"));
        dataList.add(new Student("IUN", "O"));
        dataList.add(new Student("OLOOI", "P"));
        dataList.add(new Student("HUJUY", "P"));
        dataList.add(new Student("GBGHNJ", "P"));
        dataList.add(new Student("GTG", "R"));
        dataList.add(new Student("XDCD", "R"));
        dataList.add(new Student("VFBG", "R"));
        dataList.add(new Student("SSEDE", "S"));
        dataList.add(new Student("LLL", "S"));
        dataList.add(new Student("KIKOPBH", "S"));
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                char firstChar = dataList.get(i).letter.charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (dataList.size() > 0) {
            return dataList.get(position).letter.charAt(0);
        } else {
            return -1;
        }
    }
}
