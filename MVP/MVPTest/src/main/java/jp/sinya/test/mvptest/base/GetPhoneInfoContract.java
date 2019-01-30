package jp.sinya.test.mvptest.base;

/**
 * @author KoizumiSinya
 * @date 2016/11/21. 23:01
 * @editor
 * @date
 * @describe
 */
public interface GetPhoneInfoContract {

    interface View extends BaseView<Presenter> {

        void setTime(String time);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {

        void getTime();
    }

}
