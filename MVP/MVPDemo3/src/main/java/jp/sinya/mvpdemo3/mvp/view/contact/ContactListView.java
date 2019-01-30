package jp.sinya.mvpdemo3.mvp.view.contact;

import java.util.List;

import jp.sinya.mvpdemo3.mvp.model.Contact;
import jp.sinya.mvpdemo3.mvp.view.base.BaseMvpLceView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 17:22
 * @edithor
 * @date
 */
public interface ContactListView extends BaseMvpLceView<List<Contact>> {
    void onResponseContactList(List<Contact> list);
}
