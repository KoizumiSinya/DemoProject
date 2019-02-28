package com.tgi.multiple_category_view;

import com.tgi.multiple_category_view.widget.FilterItemView;

import java.util.List;

/**
 * @author: Sinya
 * @date: 2019/02/27. 19:36
 * @editor:
 * @edit date:
 */
public abstract class FilterAdapter<T> {

    private List<T> items;

    public FilterAdapter() {
    }

    public FilterAdapter(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public abstract FilterItemView createView(T item, int position);
}
