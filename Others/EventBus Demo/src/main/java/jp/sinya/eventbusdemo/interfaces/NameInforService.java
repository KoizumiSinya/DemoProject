package jp.sinya.eventbusdemo.interfaces;

import java.util.Map;

import jp.sinya.eventbusdemo.bean.NameResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * 【一】@Path 注解
 * 1、通常是用在URL中带有参数的请求，例如【http://192.168.1.1/api/news/{newId}】
 * 案例：demo1
 * <p>
 * <p>
 * 2、如果是多参数的请求，例如【http://192.168.1.1/api/news/{newId}/{type}】
 * 案例：demo2
 * <p>
 * <p>
 * 【二】@Query 注解
 * 1、通常使用在URL后面带？的参数封装的请求。例如【http://192.168.1.1/api/news?newId=】
 * 案例：demo3
 * <p>
 * 2、多个参数的请求。例如【http://102.10.10.132/api/news?newsId={newId}&type={type}】
 * 案例：demo4
 * <p>
 * 3、多个参数，且个数不确定。例如【http://192.168.1.1/api/new?newId=1 & type = 1....】
 * 案例：demo5 demo6
 *
 * 【三】@Url 用于重定义接口地址
 * 【四】@Field 用于POST请求
 */

/**
 * @author KoizumiSinya
 * @date 2016/07/22.
 */
public interface NameInforService {

    // [+] GET

    @GET("news/{newsId}")
    Call<Object> demo1(@Part("newId") String newId);

    @GET("news/{newsId}/{type}")
    Call<Object> demo2(@Part("newId") String newId, @Path("type") String type);

    @GET("news")
    Call<Object> demo3(@Query("newId") String newId);

    @GET("news")
    Call<Object> demo4(@Query("newId") String newId, @Query("type") String type);

    @GET("news")
    Call<Object> demo5(@QueryMap Map<String, String> map);

    @GET("news")
    Call<Object> demo6(@Query("newId") String newsId, @QueryMap Map<String, String> map);

    // [-] GET
    // [+] POST

    //http://192.168.1.1/api/Comments/1 ; http://192.168.1.1/api/Comments/{newId}
    @FormUrlEncoded
    @POST("Comments/{newId}")
    Call<Object> demo7(@Path("nesId") String newId, @Field("reason") String reason);

    //http://192.168.1.1/api/Comments/1? access_token=123 ; http://192.168.1.1/api/Comments/{newId}?access_token={access_token}
    @FormUrlEncoded
    @POST("Comments/{newId}")
    Call<Object> demo8(@Path("nesId") String newId, @Query("access_token") String access_token, @Field("reason") String reason);

    //http://192.168.1.1/api/Comments/1? access_token=123 ; http://192.168.1.1/api/Comments/{newId}?access_token={access_token}
    @FormUrlEncoded
    @POST("Comments/{newId}")
    Call<Object> demo9(@Path("nesId") String newId, @Query("access_token") String access_token, @Body Object obj);

    // [-] POST
    // [+] DELETE

    //http://192.168.1.1/api/Comments/1 ; http://192.168.1.1/api/Comments/{newId}
    @DELETE("Comments/{newId}")
    Call<Object> demo10(@Path("newsId") String newId);

    //http://192.168.1.1/api/Comments/1?access_token = 123 ; http://192.168.1.1/api/Comments/{newId}?access_token={access_token}
    @DELETE("Comments/{newId}")
    Call<Object> demo11(@Path("newsId") String newId, @Query("access_token") String access_token);

    // [-] DELETE
    // [+] PUT

    //http://192.168.1.1/api/Account/1  ; http://192.168.1.1/api/Account/{accountId}
    @PUT("Account/{accountId}")
    Call<Object> demo12(@Path("accountId") String accountId, @Query("access_token") String access_token, @Body Object obj);

    // [+] PUT


    //这里是API分支节点
    @GET("/XingShiQiYuan/LookUp")
    Call<NameResult> getResult(@Query("key") String key, @Query("xingshi") String name);
}
