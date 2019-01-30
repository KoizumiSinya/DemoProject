package jp.sinya.test.mvptest.model;

/**
 * @author KoizumiSinya
 * @date 2016/11/21. 23:00
 * @editor
 * @date
 * @describe
 */
public interface PhoneInfoBize {

    interface GetPhoneInfoCallBack {

        void onGetPhoneInfo(PhoneInfo phoneInfo);
    }


    void getPhoneInfo(GetPhoneInfoCallBack getPhoneInfoCallBack);
}
