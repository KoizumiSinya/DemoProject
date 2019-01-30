package jp.sinya.coordinatorlayout.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Sinya
 * @date 2018/10/30. 13:01
 * @edithor
 * @date
 */
public class MenuFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_menu, null);

        initText(layout);
        initRecyclerView(layout);

        return layout;
    }

    private void initText(View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int position = arguments.getInt("position");
            TextView textView = root.findViewById(R.id.tv_fragment);
            textView.setText("This is " + position + " page");
        }
    }

    private void initRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerAdapter());
    }

}
