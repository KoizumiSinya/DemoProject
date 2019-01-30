package jp.sinya.stickylayoutdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TabFragment2 extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private ListView mListView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        mListView = (ListView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        for (int i = 0; i < 50; i++) {
            mDatas.add(mTitle + " -> " + i);
        }
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.id_info, mDatas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //Log.e("tag", "convertView = " + convertView);
                return super.getView(position, convertView, parent);
            }
        });
        return view;
    }

    public static TabFragment2 newInstance(String title) {
        TabFragment2 tabFragment = new TabFragment2();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
