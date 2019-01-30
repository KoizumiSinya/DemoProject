package jp.sinya.mvpdemo2.simple.demo7;


import jp.sinya.mvpdemo2.simple.demo7.base.BaseView_7;

/**
 * 作者: Dream on 2017/8/28 21:52
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//用于V层和M层交互的接口
public interface LoginView_7 extends BaseView_7 {

    void onLoginResult(String result);

}
