package jp.sinya.test.mvptest.base;

import jp.sinya.test.mvptest.model.PhoneInfo;
import jp.sinya.test.mvptest.model.PhoneInfoBize;
import jp.sinya.test.mvptest.model.PhoneInfoBizeIml;

/**
 * @author KoizumiSinya
 * @date 2016/11/21. 23:56
 * @editor
 * @date
 * @describe
 */
public class GetPhoneInfoPresenter implements GetPhoneInfoContract.Presenter {

    private GetPhoneInfoContract.View mGetPhoneInfoView;
    private PhoneInfoBize mPhoneInfoBiz;

    public GetPhoneInfoPresenter(GetPhoneInfoContract.View getPhoneInfoView) {
        mGetPhoneInfoView = getPhoneInfoView;
        mGetPhoneInfoView.setPresenter(this);
        mPhoneInfoBiz = new PhoneInfoBizeIml();
    }

    @Override
    public void onStart() {
        getTime();
    }

    @Override
    public void getTime() {
        mGetPhoneInfoView.showLoading();
        mPhoneInfoBiz.getPhoneInfo(new PhoneInfoBize.GetPhoneInfoCallBack() {
            @Override
            public void onGetPhoneInfo(PhoneInfo phoneInfo) {
                mGetPhoneInfoView.setTime(phoneInfo.getTime());
                mGetPhoneInfoView.hideLoading();
            }
        });

    }
}
