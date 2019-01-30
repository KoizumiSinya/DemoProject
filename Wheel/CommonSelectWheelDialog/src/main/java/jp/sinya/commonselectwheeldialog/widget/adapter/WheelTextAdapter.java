package jp.sinya.commonselectwheeldialog.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.sinya.commonselectwheeldialog.R;

/**
 * @author KoizumiSinya
 * @date 2016/08/04. 12:14
 * @editor
 * @date
 * @describe
 */
public class WheelTextAdapter extends AbstractWheelTextAdapter {

    private List<String> list;

    /**
     * @param context
     * @param list
     * @param currentItem
     */
    public WheelTextAdapter(Context context, List<String> list, int currentItem) {
        //24是 选中之后的文字尺寸， 14是 未选中时候的文字尺寸（单位SP）
        super(context, R.layout.view_common_select_wheel_text_item, NO_RESOURCE, currentItem, 24, 14);
        this.list = list;
        setItemTextResource(R.id.view_common_select_wheel_text_item_tv_name);
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
        return view;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        return list.get(index) + "";
    }
}
