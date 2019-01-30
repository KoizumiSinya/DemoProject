package jp.sinya.mvpdemo1.baiduhome;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import jp.sinya.mvpdemo1.R;
import jp.sinya.mvpdemo1.otto.OttoManager;

/**
 * @author Koizumi Sinya
 * @date 2017/02/20. 12:39
 * @edithor
 * @date
 */
public class BaiduFragment extends Fragment implements BaiduContract.View {


    private TextView tvContent;
    private ProgressBar progressBar;
    private Button btn;
    private BaiduContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_baidu_home, null);

        init(view);
        //initOtto();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.onStart();
    }

    private void init(View v) {
        tvContent = (TextView) v.findViewById(R.id.fragment_baidu_home_tv);
        progressBar = (ProgressBar) v.findViewById(R.id.fragment_baidu_home_progressbar);
        btn = (Button) v.findViewById(R.id.fragment_baidu_home_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestData();
            }
        });
    }

    private void initOtto() {
        OttoManager.getInstance().register(this);
    }

    @Override
    public void showLoading() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void closeLoading() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setResult(final String result) {
        tvContent.post(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(result);
            }
        });
    }

    @Override
    public void setPresenter(BaiduContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }
}
