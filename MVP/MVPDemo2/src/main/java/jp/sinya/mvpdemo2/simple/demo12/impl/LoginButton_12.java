package jp.sinya.mvpdemo2.simple.demo12.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import jp.sinya.mvpdemo2.simple.demo12.BaseButton_12;
import jp.sinya.mvpdemo2.simple.demo8.LoginPresenter_8;
import jp.sinya.mvpdemo2.simple.demo8.LoginView_8;

/**
 * 作者: Dream on 2017/8/29 21:09
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class LoginButton_12 extends BaseButton_12<LoginView_8, LoginPresenter_8> implements LoginView_8, View.OnClickListener {

    public LoginButton_12(Context context) {
        super(context);
    }

    public LoginButton_12(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    public LoginPresenter_8 createPresenter() {
        return new LoginPresenter_8();
    }

    @Override
    public LoginView_8 createView() {
        return this;
    }

    @Override
    public void onClick(View view) {
        getPresenter().login("Dream", "123456");
    }

    @Override
    public void onLoginResult(String result) {
        Toast.makeText(getContext(), "登录结果：" + result, Toast.LENGTH_LONG).show();
    }
}
