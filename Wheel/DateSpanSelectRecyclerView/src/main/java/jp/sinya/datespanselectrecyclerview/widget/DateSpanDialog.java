package jp.sinya.datespanselectrecyclerview.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import jp.sinya.datespanselectrecyclerview.R;

/**
 * @author KoizumiSinya
 * @date 2016/3/14.
 */
public class DateSpanDialog extends Dialog {

    public DateSpanDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_datespan);
    }
}
