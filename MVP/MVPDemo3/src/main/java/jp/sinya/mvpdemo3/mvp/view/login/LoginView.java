package jp.sinya.mvpdemo3.mvp.view.login;

import jp.sinya.mvpdemo3.mvp.model.UserConfig;
import jp.sinya.mvpdemo3.mvp.view.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/21. 12:14
 * @edithor
 * @date
 */
public interface LoginView extends BaseView {
    void onLoginResponse(UserConfig config);
}
