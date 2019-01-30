package jp.sinya.swipeback.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import jp.sinya.swipeback.demo.R;
import jp.sinya.swipeback.demo.library2.SwipeBackFragment;

/**
 * @author Sinya
 * @date 2018/12/03. 21:50
 * @edithor
 * @date
 */
public abstract class BaseFragment extends SwipeBackFragment {

    protected TextView tv;
    protected Button btnBack;
    protected String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getTextContent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1, container, false);
        // 需要支持SwipeBack则这里必须调用toSwipeBackFragment(view);
        tv = view.findViewById(R.id.fragment_item1_tv);
        return attachToSwipeBack(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        tv.setText(content);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        tv = view.findViewById(R.id.fragment_item1_tv);
//
//        btnBack = view.findViewById(R.id.fragment_item1_back);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickBack();
//            }
//        });
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected String getTextContent() {
        return "";
    }

    protected abstract void onClickBack();

}
