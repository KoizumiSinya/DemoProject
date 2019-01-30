package jp.sinya.contactlocationdemo.view.impl;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import jp.sinya.contactlocationdemo.model.Contact;
import jp.sinya.contactlocationdemo.net.HttpUtils;
import jp.sinya.contactlocationdemo.view.MainView;

/**
 * @author Koizumi Sinya
 * @date 2018/01/01. 21:28
 * @edithor
 * @date
 */
public class MainPresenter extends BasePresenter {
    private MainView view;
    private Contact contact;

    public MainPresenter(MainView view) {
        this.view = view;
        attach((Context) view);
    }

    public void searchPhone(String phone) {
        if (phone == null || phone.isEmpty() || phone.trim().equals("")) {
            view.showToast("手机号码不能为空");
            return;
        }
        if (phone.length() != 11) {
            view.showToast("请输入正确的手机号码");
            return;
        }

        view.showLoad();
        sendHttp(phone);
    }

    private void sendHttp(String phone) {
        final Map<String, String> param = new HashMap<>();
        param.put("tel", phone);
        HttpUtils http = new HttpUtils(new HttpUtils.HttpResponse() {
            @Override
            public void onSuccess(Object obj) {
                Log.i("Sinya", obj.toString());
                String json = obj.toString();
                int index = json.indexOf("{");
                if (index != 0) {
                    json = json.substring(index, json.length());
                }
                contact = parseWithJSON(json);
                view.updateView(contact);
                view.closeLoad();
            }

            @Override
            public void onError(String err) {
                view.showToast(err);
                view.closeLoad();
            }
        });
        http.sendGetHttp("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm", param);
    }

    private Contact parseWithJSON(String json) {
        Contact contact = new Contact();

        try {
            JSONObject jsonObject = new JSONObject(json);
            contact.setMts(jsonObject.getString("mts"));
            contact.setCatName(jsonObject.getString("catName"));
            contact.setCarrier(jsonObject.getString("carrier"));
            contact.setTelString(jsonObject.getString("telString"));
            contact.setProvince(jsonObject.getString("province"));
            contact.setAreaVid(jsonObject.getString("areaVid"));
            contact.setIspVid(jsonObject.getString("ispVid"));
        } catch (JSONException e) {
            e.printStackTrace();
            view.showToast("数据结果无法转换成JSONObject");
        }

        return contact;
    }
}
