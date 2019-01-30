package jp.sinya.mvpdemo3.mvp.view.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.adapter.ContactListAdapter;
import jp.sinya.mvpdemo3.mvp.adapter.MvpLceRecyclerViewAdapter;
import jp.sinya.mvpdemo3.mvp.model.Contact;
import jp.sinya.mvpdemo3.mvp.view.base.BaseMvpLceRefreshActivity;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 17:18
 * @edithor
 * @date
 */
public class ContactListActivity extends BaseMvpLceRefreshActivity<List<Contact>, ContactListView, ContactListPresenter> implements ContactListView {

    @Override
    protected int initLayout() {
        return R.layout.activity_contact_list;
    }

    @Override
    protected ContactListPresenter initPresenter() {
        return new ContactListPresenter();
    }

    @Override
    protected ContactListView initMvpView() {
        return this;
    }

    @Override
    protected MvpLceRecyclerViewAdapter createAdapter() {
        return new ContactListAdapter(this, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(false);
    }

    @Override
    public void loadData(boolean isPullToRefresh) {
        getPresenter().requestContactList(isPullToRefresh);
    }

    @Override
    public void onResponseContactList(List<Contact> list) {
        getAdapter().refreshAdapter(true, list);
    }

}
