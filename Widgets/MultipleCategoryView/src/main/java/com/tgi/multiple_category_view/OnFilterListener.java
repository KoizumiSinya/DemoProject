package com.tgi.multiple_category_view;

import java.util.List;

/**
 * @author: Sinya
 * @date: 2019/02/27. 19:45
 * @editor:
 * @edit date:
 */
public interface OnFilterListener<T> {

    void onSelected(T item);

    void onSelected(List<T> items);

    void onNotingSelected();

    void onUnSelected(T item);

}
