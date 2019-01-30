package jp.sinya.contactlocationdemo.view.impl;

import android.content.Context;

/**
 * @author Koizumi Sinya
 * @date 2018/01/01. 21:28
 * @edithor
 * @date
 */
public class BasePresenter {
    private Context context;

    public void attach(Context context) {
        this.context = context;
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        context = null;
    }
}
