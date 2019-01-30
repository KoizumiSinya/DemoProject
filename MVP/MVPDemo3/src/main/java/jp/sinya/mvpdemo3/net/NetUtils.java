package jp.sinya.mvpdemo3.net;

import java.util.List;

import jp.sinya.mvpdemo3.constants.Constants;
import jp.sinya.mvpdemo3.mvp.model.Contact;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Koizumi Sinya
 * @date 2018/01/11. 21:06
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

    public static void getContactList(Callback callback) {
        ContactListService service = getRetrofitInstance().create(ContactListService.class);
        Call<List<Contact>> call = service.getContactList();
        call.enqueue(callback);
    }

    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()//
                .client(new OkHttpClient()) //
                .addConverterFactory(GsonConverterFactory.create())//
                .baseUrl(Constants.BASE_URL)//
                .build();
        return retrofit;
    }
}