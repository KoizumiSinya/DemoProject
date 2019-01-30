package com.example.demo_fragmentactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOne extends Fragment {

    private Bundle dataBundle;

    public static FragmentOne newInstance(Bundle bundle) {
        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        TextView text = (TextView) view.findViewById(R.id.tv_pager);
        text.setText(dataBundle.getString("fragment"));
        return view;
    }


}
