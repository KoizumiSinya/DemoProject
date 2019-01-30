package jp.sinya.mvpdemo3.net;

import java.util.List;

import jp.sinya.mvpdemo3.mvp.model.Contact;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Koizumi Sinya
 * @date 2018/01/11. 21:06
 * @edithor
 * @date
 */
public interface ContactListService {
    // https://s3.eu-central-1.amazonaws.com/roboteam/contacts_mock_short.json
    @GET("/roboteam/contacts_mock_short.json")
    Call<List<Contact>> getContactList();
}
