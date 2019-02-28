package com.tgi.multiple_category_view;

import com.tgi.multiple_category_view.widget.FilterItemView;

/**
 * @author: Sinya
 * @date: 2019/02/27. 19:44
 * @editor:
 * @edit date:
 */
public interface OnFilterItemListener {
    void onItemSelected(FilterItemView view);

    void onItemUnSelected(FilterItemView view);

    void onItemRemoved(FilterItemView view);
}
