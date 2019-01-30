package jp.sinya.test.retrofitdemo.api;

import jp.sinya.test.retrofitdemo.bean.PhoneResult;

public class PhoneApi {

    /**
     * HOST地址
     */
    public static final String BASE_URL = "http://apis.baidu.com";
    /**
     * 开发者Key
     */
    public static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";

    /**
     * 获取PhoneApi实例
     * @return
     */
    public static PhoneApi getApi(){
        return ApiHolder.phoneApi;
    }

    static class ApiHolder{
        private static PhoneApi phoneApi = new PhoneApi();
    }

    private PhoneResult mPhoneResult;


}