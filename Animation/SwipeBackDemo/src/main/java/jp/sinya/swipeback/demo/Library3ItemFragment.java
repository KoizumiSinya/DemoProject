package jp.sinya.swipeback.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import jp.sinya.swipeback.demo.library3.swipe.SwipeBackFragment;


/**
 * @author Sinya
 * @date 2018/12/04. 19:09
 * @edithor
 * @date
 */
public class Library3ItemFragment extends SwipeBackFragment {

    private Button btnNext;
    private Button btnLast;
    private TextView tvContent;
    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = "This is " + getActivity().getSupportFragmentManager().getBackStackEntryCount() + " fragment";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library3_item, container, false);
        initViews(view);
        return attachToSwipeBack(view);
    }

    private void initViews(View view) {
        btnNext = view.findViewById(R.id.fragment_library3_item_btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new Library3ItemFragment());
            }
        });

        btnLast = view.findViewById(R.id.fragment_library3_item_btn_last);
        tvContent = view.findViewById(R.id.fragment_library3_item_tv);

        tvContent.setText(content);
    }
}
