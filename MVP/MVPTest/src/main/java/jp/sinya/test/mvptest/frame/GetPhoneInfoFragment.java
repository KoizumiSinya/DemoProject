package jp.sinya.test.mvptest.frame;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.sinya.test.mvptest.R;
import jp.sinya.test.mvptest.base.GetPhoneInfoContract;

/**
 * @author KoizumiSinya
 * @date 2016/11/21. 23:26
 * @editor
 * @date
 * @describe
 */
public class GetPhoneInfoFragment extends Fragment implements GetPhoneInfoContract.View {

    @Bind(R.id.fragment_mvp_test_tv_time)
    TextView mTextView;

    @Bind(R.id.fragment_mvp_test_btn_get_time)
    Button mButton;

    private GetPhoneInfoContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mvp_test, null);
        initView();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    private void initView() {
    }

    @Override
    public void setTime(String time) {
        mTextView.setText(time);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setPresenter(GetPhoneInfoContract.Presenter presenter) {
        if (presenter != null) {
            this.mPresenter = presenter;
        }
    }

    @OnClick(R.id.fragment_mvp_test_btn_get_time)
    public void onClick_getTime() {
        mPresenter.getTime();
    }

}
