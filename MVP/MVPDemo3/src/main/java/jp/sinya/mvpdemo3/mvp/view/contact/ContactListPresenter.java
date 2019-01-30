package jp.sinya.mvpdemo3.mvp.view.contact;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import jp.sinya.mvpdemo3.mvp.model.Contact;
import jp.sinya.mvpdemo3.mvp.presenter.base.BasePresenter;
import jp.sinya.mvpdemo3.net.NetUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 17:26
 * @edithor
 * @date
 */
public class ContactListPresenter extends BasePresenter<ContactListView> {
    private List<Contact> list;

    public ContactListPresenter() {
    }

    public void requestContactList(final boolean isPullToRefresh) {
        getMvpView().showLoading(isPullToRefresh);

        NetUtils.getContactList(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                list = response.body();
                Collections.sort(list, new Contact.DateComparator());
                Log.i("Sinya", list.toString());
                getMvpView().onResponseContactList(list);
                getMvpView().showContent(isPullToRefresh);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                getMvpView().showError(isPullToRefresh);
                Toast.makeText((Context) getMvpView(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
