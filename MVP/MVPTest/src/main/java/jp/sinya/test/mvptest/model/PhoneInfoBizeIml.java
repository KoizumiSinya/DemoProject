package jp.sinya.test.mvptest.model;

import android.os.Build;

/**
 * @author KoizumiSinya
 * @date 2016/11/21. 23:00
 * @editor
 * @date
 * @describe
 */
public class PhoneInfoBizeIml implements PhoneInfoBize {


    @Override
    public void getPhoneInfo(final GetPhoneInfoCallBack getPhoneInfoCallBack) {

        //在这里模拟请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    PhoneInfo phoneInfo = new PhoneInfo();
                    phoneInfo.setTime(System.currentTimeMillis() + "");
                    phoneInfo.setMobileType(Build.MODEL);
                    phoneInfo.setMobileVersion(Build.VERSION.RELEASE);

                    Thread.sleep(1000);

                    getPhoneInfoCallBack.onGetPhoneInfo(phoneInfo);

                } catch (Exception e) {

                }
            }
        }).start();

    }
}
