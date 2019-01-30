package jp.sinya.mvpdemo2.simple.demo4;


import jp.sinya.mvpdemo2.simple.demo4.base.BaseView_4;

/**
 * 作者: Dream on 2017/8/28 21:52
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//用于V层和M层交互的接口
public interface LoginView_4 extends BaseView_4 {

    void onLoginResult(String result);

}
