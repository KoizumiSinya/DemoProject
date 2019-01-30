package jp.sinya.test.retrofitdemo.interfaces;

import jp.sinya.test.retrofitdemo.bean.PhoneResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * @author KoizumiSinya
 * @date 2016/6/29.
 */
public interface PhoneService {

    //URL的支点
    @GET("/netpopo/shouji/query")
    Call<PhoneResult> getResult(@Header("apikey") String apiKey, @Query("shouji") String phone);

    /**
     * @Header用来添加Header
     * @Query用来添加查询关键字 params
     */
}
