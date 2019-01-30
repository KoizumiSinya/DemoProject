package studio.sinya.jp.demo_httpframwork;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import studio.sinya.jp.demo_httpframwork.http.Request;
import studio.sinya.jp.demo_httpframwork.http.callback.StringCallback;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/14 14:58
 * editor：
 * updateDate：2015/9/14 14:58
 */
public class FragmentOne extends Fragment implements View.OnClickListener{
    private Button StringBtn, JsonBtn;
    private TextView ContentText;

    public FragmentOne() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        StringBtn = (Button) rootView.findViewById(R.id.mTestStringBtn);
        JsonBtn = (Button) rootView.findViewById(R.id.mTestJsonBtn);
        ContentText = (TextView) rootView.findViewById(R.id.mTestResultLabel);

        StringBtn.setOnClickListener(this);
        JsonBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTestStringBtn:
                requsetString();
                break;

            case R.id.mTestJsonBtn:

                break;
        }
    }

    private void requsetString(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "goNet_http.txt";
        Request request = new Request("http://www.baidu.com", Request.RequestMethod.GET);

        request.setCallBack(new StringCallback() {
            @Override
            public void onSuccess(Object result) {
                ContentText.setText((String) result);
            }

            @Override
            public void onFilure(Exception result) {
                result.printStackTrace();
            }
        }.setPath(path));

        request.execute();
    }
}
