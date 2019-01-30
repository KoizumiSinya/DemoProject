package jp.sinya.mvpdemo1.baiduhome;

import jp.sinya.mvpdemo1.base.BasePresenter;
import jp.sinya.mvpdemo1.base.BaseView;

/**
 * @author Koizumi Sinya
 * @date 2017/02/23. 0:01
 * @edithor
 * @date
 */
public interface BaiduContract {

    interface View extends BaseView<Presenter> {
        public void showLoading();

        public void closeLoading();

        public void setResult(String result);
    }

    interface Presenter extends BasePresenter {
        public void requestData();
    }
}
