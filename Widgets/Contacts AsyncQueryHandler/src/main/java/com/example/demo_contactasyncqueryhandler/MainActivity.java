package com.example.demo_contactasyncqueryhandler;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_contactasyncqueryhandler.bean.CharacterParser;
import com.example.demo_contactasyncqueryhandler.bean.ContactBean;
import com.example.demo_contactasyncqueryhandler.utils.LogUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 連絡先をとるデモ
 *
 * @author Koizumi Sinya
 */
public class MainActivity extends Activity {

    private Context context;
    private Map<Long, ContactBean> contactMap;
    private ContactAsyncQueryHandler queryHandler;
    private CharacterParser characterParser;

    private TextView text_contact;
    private ProgressBar pb;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pb.setVisibility(View.INVISIBLE);
                    StringBuffer sb = new StringBuffer();
                    for (ContactBean bean : contactMap.values()) {
                        sb.append(bean.toString() + "\n");
                    }
                    text_contact.setText(sb.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        characterParser = new CharacterParser();
        text_contact = (TextView) findViewById(R.id.text_contact);
        pb = (ProgressBar) findViewById(R.id.progress);

    }

    public void onClick(View v) {
        text_contact.setText("");
        pb.setVisibility(View.VISIBLE);
        getContact();
    }

    public void getContact() {

        contactMap = new HashMap<>();
        queryHandler = new ContactAsyncQueryHandler(getContentResolver());

        // 电话号码 ID 姓名
        queryHandler.startQuery(1, null, Phone.CONTENT_URI, new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID, "sort_key"}, null, null, "sort_key COLLATE LOCALIZED asc");
        // 邮箱
        queryHandler.startQuery(2, null, Email.CONTENT_URI, new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID, "sort_key"}, null, null, null);
        // 地址
        queryHandler.startQuery(3, null, StructuredPostal.CONTENT_URI, new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID, "sort_key"}, null, null, null);
        // 备注
        queryHandler.startQuery(4, null, Data.CONTENT_URI, new String[]{Phone.CONTACT_ID, Note.NOTE,}, Data.MIMETYPE + "='" + Note.CONTENT_ITEM_TYPE + "'", null, null);
        // 职位
        queryHandler.startQuery(5, null, Data.CONTENT_URI, new String[]{Phone.CONTACT_ID, Organization.COMPANY, Organization.TITLE}, Data.MIMETYPE + "='" + Organization.CONTENT_ITEM_TYPE + "'", null, null);
        // 生日
        queryHandler.startQuery(6, null, Data.CONTENT_URI, new String[]{Phone.CONTACT_ID, Event.DATA1}, Data.MIMETYPE + "='" + Event.CONTENT_ITEM_TYPE + "'" + " and " + Event.TYPE + "='" + Event.TYPE_BIRTHDAY + "'", null, null);
        // IM
        queryHandler.startQuery(7, null, Data.CONTENT_URI, new String[]{Phone.CONTACT_ID, Im.DATA}, Data.MIMETYPE + " ='" + Im.CONTENT_ITEM_TYPE + "'", null, null);

        handler.sendEmptyMessageDelayed(0, 500);

    }

    private String getSortLetterBySortKey(String sortKey) {
        if (sortKey == null || "".equals(sortKey.trim())) {
            return null;
        }
        String letter = "#";
        // 汉字转换成拼音
        String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    private String getSortLetter(String name) {
        String letter = "#";
        if (name == null) {
            return letter;
        }
        // 汉字转换成拼音
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    private String formatNum(String num) {
        return num.replaceAll(" ", "").replace("+86", "");
    }

    // [+] カスタマイズのハンドル

    class ContactAsyncQueryHandler extends AsyncQueryHandler {

        public ContactAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                switch (token) {

                    case 1:
                        ContactBean bean = null;
                        while (cursor.moveToNext()) {

                            Set set = contactMap.keySet();

                            // 联系人ID
                            Long contactId = cursor.getLong(3);
                            if (!set.contains(contactId)) {
                                bean = new ContactBean();
                            }

                            bean.setId(contactId);

                            // 拼音Key
                            int nameIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
                            String sortKey = cursor.getString(cursor.getColumnIndex("sort_key"));
                            bean.setSortKey(sortKey);

                            // 名字
                            String name = cursor.getString(nameIndex);
                            bean.setName(name);

                            // 拼音索引
                            String sortLetters = getSortLetterBySortKey(sortKey);
                            if (sortLetters == null) {
                                sortLetters = getSortLetter(name);
                                bean.setSortLetters(sortLetters);
                            }

                            String strPhoneNumber = formatNum(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            if (bean.getPhone() == null) {
                                bean.setPhone(strPhoneNumber);
                            } else if (bean.getTell() == null) {
                                bean.setTell(strPhoneNumber);
                            } else {
                            }
                            contactMap.put(contactId, bean);
                        }
                        break;

                    case 2:
                        while (cursor.moveToNext()) {
                            Long contactId = cursor.getLong(3);
                            String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                            ContactBean tempBean = contactMap.get(contactId);
                            if (tempBean != null) {
                                tempBean.setEmail(email);
                                contactMap.put(contactId, tempBean);
                            }
                        }

                        break;

                    case 3:
                        while (cursor.moveToNext()) {
                            Long contactId = cursor.getLong(3);
                            String formatAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));

                            ContactBean tempBean = contactMap.get(contactId);
                            if (tempBean != null) {
                                tempBean.setAddress(formatAddress);
                                contactMap.put(contactId, tempBean);
                            }
                        }
                        break;

                    case 4:
                        while (cursor.moveToNext()) {
                            int contactId = cursor.getInt(0);
                            String noteinfo = cursor.getString(cursor.getColumnIndex(Note.NOTE));

                            ContactBean tempBean = contactMap.get(Long.valueOf(contactId));
                            if (tempBean != null) {
                                tempBean.setNotes(noteinfo);
                                contactMap.put(Long.valueOf(contactId), tempBean);
                            }
                        }
                        break;

                    case 5:
                        while (cursor.moveToNext()) {
                            int contactId = cursor.getInt(0);
                            //String company = cursor.getString(cursor.getColumnIndex(Organization.COMPANY));
                            String title = cursor.getString(cursor.getColumnIndex(Organization.TITLE));

                            ContactBean tempBean = contactMap.get(Long.valueOf(contactId));
                            if (tempBean != null) {
                                tempBean.setTitle(title);
                                contactMap.put(Long.valueOf(contactId), tempBean);
                            }
                        }
                        break;

                    case 6:
                        while (cursor.moveToNext()) {
                            int contactId = cursor.getInt(0);
                            String birthday = cursor.getString(cursor.getColumnIndex(Data.DATA1));

                            ContactBean tempBean = contactMap.get(Long.valueOf(contactId));
                            if (tempBean != null) {
                                tempBean.setBirthDate(birthday);
                                contactMap.put(Long.valueOf(contactId), tempBean);
                            }
                        }
                        break;

                    case 7:
                        while (cursor.moveToNext()) {
                            int contactId = cursor.getInt(0);
                            String imName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                            ContactBean tempBean = contactMap.get(Long.valueOf(contactId));
                            if (tempBean != null) {
                                tempBean.setIm(imName);
                                contactMap.put(Long.valueOf(contactId), tempBean);
                            }
                        }

                        break;
                    default:
                        break;
                }

            } else {
                Toast.makeText(context, "未获取权限 或 手机无联系人", Toast.LENGTH_SHORT).show();
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }
}

// [-] カスタマイズのハンドル


