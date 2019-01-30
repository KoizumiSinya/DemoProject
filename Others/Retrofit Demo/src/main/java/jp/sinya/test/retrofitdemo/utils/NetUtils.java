package jp.sinya.test.retrofitdemo.utils;

import jp.sinya.test.retrofitdemo.bean.PhoneResult;
import jp.sinya.test.retrofitdemo.interfaces.PhoneService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static jp.sinya.test.retrofitdemo.api.PhoneApi.API_KEY;
import static jp.sinya.test.retrofitdemo.api.PhoneApi.BASE_URL;

/**
 * @author Koizumi Sinya
 * @date 2017/05/21. 1:19
 * @edithor
 * @date
 */
public class NetUtils {

    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = getRetrofit();
        }
        return retrofit;
    }


    public static void GetPhoneInfo(String phoneNumber) {
        PhoneService service = getRetrofitInstance().create(PhoneService.class);
        Call<PhoneResult> call = service.getResult(API_KEY, phoneNumber);
        call.enqueue(new BaseCallBack<PhoneResult>());
    }

    public static void GetPhoneInfo(long eventTag, String phoneNumber) {
        PhoneService service = getRetrofitInstance().create(PhoneService.class);
        Call<PhoneResult> call = service.getResult(API_KEY, phoneNumber);
        call.enqueue(new BaseCallBack<PhoneResult>(eventTag));
    }

    public static void GetPhoneInfo(String phoneNumber, BaseCallBack.CallBackListener listener) {
        PhoneService service = getRetrofitInstance().create(PhoneService.class);
        Call<PhoneResult> call = service.getResult(API_KEY, phoneNumber);
        call.enqueue(new BaseCallBack<PhoneResult>(listener));
    }

    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()//
                //.client(new OkHttpClient()) //
                .addConverterFactory(GsonConverterFactory.create())//
                .baseUrl(BASE_URL)//
                .build();
        return retrofit;
    }
}
