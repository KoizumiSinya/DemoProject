package jp.sinya.mvpdemo2.simple.demo9;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import jp.sinya.mvpdemo2.R;
import jp.sinya.mvpdemo2.simple.demo8.LoginPresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.LoginView_8;

/**
 * 作者: Dream on 2017/8/29 20:37
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class LoginFragment_9 extends Fragment implements LoginView_8 {

    private LoginPresenter_8 presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter = new LoginPresenter_8();
                presenter.attachView(LoginFragment_9.this);
                presenter.login("Dream", "123456");
            }
        });
    }

    @Override
    public void onLoginResult(String result) {
        Toast.makeText(getContext(), "登录结果：" + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            this.presenter.detachView();
        }
    }
}
