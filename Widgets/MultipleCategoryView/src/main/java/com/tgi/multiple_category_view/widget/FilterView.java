package com.tgi.multiple_category_view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tgi.multiple_category_view.Coord;
import com.tgi.multiple_category_view.FilterAdapter;
import com.tgi.multiple_category_view.IFilterModel;
import com.tgi.multiple_category_view.OnFilterCollapseListener;
import com.tgi.multiple_category_view.OnFilterItemListener;
import com.tgi.multiple_category_view.OnFilterListener;
import com.tgi.multiple_category_view.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: Sinya
 * @date: 2019/02/27. 20:04
 * @editor:
 * @edit date:
 */
public class FilterView<T extends IFilterModel> extends FrameLayout implements OnFilterItemListener, OnFilterCollapseListener {

    public static final String STATE_SUPER = "state_super";
    public static final String STATE_SELECTED = "state_selected";
    public static final String STATE_REMOVED = "state_removed";
    public static final String STATE_COLLAPSED = "state_collapsed";

    private TextView tvCollapse;
    private View vLineTop;
    private View vLineBottom;

    private FrameLayout flContainer;
    private CollapseView collapseView;
    private CollapseFilterView collapseFilterView;
    private CollapseFilterContainer collapseFilterContainer;
    private ExpandFilterView expandFilterView;
    private HorizontalScrollView collapseScroll;
    private ScrollView expandScroll;

    private int expandedBackground;
    private int collapsedBackground;

    private LinkedHashMap<FilterItemView, Coord> selectedFilters = new LinkedHashMap<>();
    private LinkedHashMap<FilterItemView, Coord> removedFilters = new LinkedHashMap<>();
    private LinkedHashMap<FilterItemView, T> items = new LinkedHashMap<>();

    private List<T> selectedItems = new ArrayList<>();
    private List<T> removedItems = new ArrayList<>();


    private FilterAdapter<T> filterAdapter;
    private OnFilterListener<T> onFilterListener;

    private int margin;
    private String noSelectedItemText;
    private String textToReplaceArrow;
    private boolean replaceArrowByText;
    private boolean isBusy;
    private boolean isCollapsed;


    public void setNoSelectedItemText(String text) {
        this.noSelectedItemText = text;
        tvCollapse.setText(noSelectedItemText);
    }

    public void setTextToReplaceArrow(String text) {
        this.textToReplaceArrow = text;
        collapseView.setText(textToReplaceArrow);
    }

    public void setReplaceArrowByText(boolean flag) {
        this.replaceArrowByText = flag;
        collapseView.setHasText(replaceArrowByText);
    }

    public void setCollapsedBackground(int bg) {
        this.collapsedBackground = bg;
        collapseFilterContainer.setContainerBackground(collapsedBackground);
        collapseFilterContainer.invalidate();
    }

    public void setExpandedBackground(int bg) {
        this.expandedBackground = bg;
        // TODO: 2019/02/28
    }

    public FilterView(@NonNull Context context) {
        this(context, null);
    }

    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.filter, this, true);
        tvCollapse = findViewById(R.id.collapsedText);

        collapseView = findViewById(R.id.collapseView);
        collapseView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        collapseFilterView = findViewById(R.id.collapsedFilter);
        collapseFilterView.setCollapseListener(this);

        collapseFilterContainer = findViewById(R.id.collapsedContainer);
        collapseFilterContainer.setCollapseListener(this);

        expandFilterView = findViewById(R.id.expandedFilter);
        expandFilterView.setOnFilterCollapseListener(this);

        collapseScroll = findViewById(R.id.collapsedFilterScroll);
        expandScroll = findViewById(R.id.expandedFilterScroll);
        vLineTop = findViewById(R.id.dividerTop);
        vLineBottom = findViewById(R.id.dividerBottom);
        flContainer = findViewById(R.id.container);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Filter, 0, 0);

        int color = typedArray.getColor(R.styleable.Filter_collapsedBackground, Color.WHITE);
        collapseFilterContainer.setContainerBackground(color);

        color = typedArray.getColor(R.styleable.Filter_collapsedBackground, Color.WHITE);
        expandFilterView.setBackgroundColor(color);

        typedArray.recycle();
    }

    public void build() {
        if (!isValidate()) {
            return;
        }

        items.clear();
        expandFilterView.post(new Runnable() {
            @Override
            public void run() {
                if (filterAdapter != null && filterAdapter.getItems() != null) {
                    for (int i = 0; i < filterAdapter.getItems().size(); i++) {
                        FilterItemView view = filterAdapter.createView(filterAdapter.getItems().get(i), i);
                        expandFilterView.addView(view);
                        items.put(view, filterAdapter.getItems().get(i));
                    }
                }

                if (!isCollapsed) {
                    collapse(1);
                }
            }
        });

        expandFilterView.setMargin(margin);
        collapseFilterView.setMargin(margin);
    }

    private boolean isValidate() {
        if (filterAdapter != null && filterAdapter.getItems() != null && !filterAdapter.getItems().isEmpty()) {
            return true;
        }
        return false;
    }

    private void collapse(final long duration) {
        if (isBusy || collapseFilterView.isBusy()) {
            return;
        }

        isBusy = true;
        removedFilters.clear();
        isCollapsed = true;

        removeItemsFromParent();
        flContainer.bringToFront();
        flContainer.requestFocus();


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, duration).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / duration;
                collapseView.rotateArrow(180 * ratio);
                collapseView.turnIntoOkButton(ratio);

                int index = 0;
                while (selectedFilters.keySet().iterator().hasNext()) {
                    FilterItemView itemView = selectedFilters.keySet().iterator().next();
                    Coord coord = selectedFilters.get(itemView);

                    if (coord == null) {
                        index++;
                        continue;
                    }

                    float x = coord.getX();
                    float y = coord.getY();

                    if (index < calculateCount(collapseScroll.getMeasuredWidth(), itemView.getCollapsedSize(), margin)) {
                        itemView.setTranslationX(itemView.getStartX() + (x - itemView.getStartX()) * ratio);
                        itemView.setTranslationY(itemView.getY() + (y - itemView.getStartY()) * ratio);
                    } else {
                        itemView.setTranslationX(x);
                        itemView.setTranslationY(y);
                        itemView.setAlpha(ratio);
                    }
                    itemView.increase(ratio);

                    if (ratio == 1f) {
                        itemView.removeFromParent();
                        expandFilterView.addView(itemView);
                        itemView.setTranslationX(0f);
                        itemView.setTranslationY(0f);
                    }
                    index++;
                }


                while (removedFilters.keySet().iterator().hasNext()) {
                    FilterItemView itemView = removedFilters.keySet().iterator().next();
                    Coord coord = removedFilters.get(itemView);
                    if (coord == null) {
                        continue;
                    }

                    itemView.setAlpha(ratio);
                    itemView.removeFromParent();
                    expandFilterView.addView(itemView);
                    itemView.setTranslationX(coord.getX() * (1 - ratio));
                    itemView.setTranslationY(coord.getY() * (1 - ratio));
                }

                tvCollapse.setAlpha(1 - ratio);
                vLineTop.setAlpha(2 * ratio);

                collapseFilterContainer.setTranslationY(-flContainer.getHeight() * (1 - ratio));
                expandScroll.setTranslationY(-flContainer.getHeight() * (1 - ratio));

                if (ratio == 1f) {
                    expandScroll.bringToFront();
                    expandScroll.requestFocus();
                    tvCollapse.setVisibility(GONE);
                    isBusy = false;
                }

            }
        });
        valueAnimator.start();

        notifyListener();
    }

    private void removeItemsFromParent() {
        while (selectedFilters.keySet().iterator().hasNext()) {
            FilterItemView next = selectedFilters.keySet().iterator().next();
            remove(next);
        }
    }

    private void remove(FilterItemView itemView) {
        float x = itemView.getX();
        float y = itemView.getY();
        itemView.removeFromParent();
        collapseFilterContainer.addView(itemView);

        itemView.setTranslationX(x);
        itemView.setTranslationY(y);
        itemView.setStartX(x);
        itemView.setStartY(y);
        itemView.bringToFront();

    }

    private void notifyListener() {
        if (selectedFilters.isEmpty()) {
            if (onFilterListener != null) {
                onFilterListener.onNotingSelected();
            }
        } else {
            if (onFilterListener != null) {
                onFilterListener.onSelected(getSelectedItems());
            }
        }
    }

    private List<T> getSelectedItems() {
        List<T> list = new ArrayList();
        while (selectedFilters.keySet().iterator().hasNext()) {
            T t = items.get(selectedFilters.keySet().iterator().next());
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }


    @Override
    public void collapse() {
        collapse(400);
    }

    @Override
    public void expand() {
        if (collapseFilterView.isBusy() || isBusy) {
            return;
        }

        isBusy = true;
        isCollapsed = false;

        removeItemsFromParent();
        flContainer.bringToFront();
        flContainer.requestFocus();


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 400f).setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = (float) animation.getAnimatedValue() / 400;
                collapseView.rotateArrow(180 * ratio);
                collapseView.turnIntoOkButton(ratio);

                int index = 0;
                while (selectedFilters.keySet().iterator().hasNext()) {
                    FilterItemView itemView = selectedFilters.keySet().iterator().next();
                    Coord coord = selectedFilters.get(itemView);

                    if (index < calculateCount(collapseScroll.getMeasuredWidth(), itemView.getCollapsedSize(), margin)) {
                        itemView.setTranslationX(itemView.getStartX() + (coord.getX() - itemView.getStartX()) * ratio);
                        itemView.setTranslationY(itemView.getStartY() + (coord.getY() - itemView.getStartY()) * ratio);
                    } else {
                        itemView.setTranslationX(coord.getX());
                        itemView.setTranslationY(coord.getY());
                        itemView.setAlpha(ratio);
                    }

                    itemView.increase(ratio);

                    if (ratio == 1f) {
                        itemView.removeFromParent();
                        expandFilterView.addView(itemView);
                        itemView.setTranslationX(0);
                        itemView.setTranslationY(0);
                    }

                    index++;
                }

                while (removedFilters.keySet().iterator().hasNext()) {
                    FilterItemView itemView = removedFilters.keySet().iterator().next();
                    Coord coord = removedFilters.get(itemView);

                    itemView.setAlpha(1 - ratio);
                    itemView.removeFromParent();
                    expandFilterView.addView(itemView);
                    itemView.setTranslationX(coord.getX() * (1 - ratio));
                    itemView.setTranslationY(coord.getY() * (1 - ratio));
                }

                tvCollapse.setAlpha(1 - ratio);
                vLineTop.setAlpha(2 * ratio);
                collapseFilterContainer.setTranslationY(-flContainer.getHeight() * (1 - ratio));
                expandScroll.setTranslationY(-flContainer.getHeight() * (1 - ratio));

                if (ratio == 1f) {
                    expandScroll.bringToFront();
                    expandScroll.requestFocus();
                    tvCollapse.setVisibility(GONE);
                    isBusy = false;
                }
            }
        });
        valueAnimator.start();

        while (removedFilters.keySet().iterator().hasNext()) {
            FilterItemView itemView = removedFilters.keySet().iterator().next();
            Coord coord = removedFilters.get(itemView);
            itemView.setTranslationX(coord.getX());
            itemView.setTranslationY(coord.getY());
            itemView.increase(1f);
            itemView.deselect(true);
        }
    }

    public void deselectAll() {
        while (selectedFilters.keySet().iterator().hasNext()) {
            selectedFilters.keySet().iterator().next().deselect(false);
        }
        selectedFilters.clear();
        selectedItems.clear();

        if (onFilterListener != null) {
            onFilterListener.onNotingSelected();
        }
    }

    @Override
    public void toggle() {
        if (collapseFilterView.isBusy() || isBusy) {
            return;
        }

        if (isCollapsed) {
            expand();
        } else {
            collapse();
        }
    }

    @Override
    public void onItemSelected(FilterItemView view) {
        T t = items.get(view);
        if (items.containsKey(view)) {
            selectedItems.add(t);
        }
        selectedFilters.put(view, new Coord((int) view.getX(), (int) view.getY()));
        if (onFilterListener != null) {
            onFilterListener.onUnSelected(t);
        }
    }

    @Override
    public void onItemUnSelected(FilterItemView view) {
        T t = items.get(view);
        if (items.containsKey(view)) {
            selectedItems.remove(t);
        }
        selectedFilters.remove(view);
        if (onFilterListener != null) {
            onFilterListener.onUnSelected(t);
        }
    }

    @Override
    public void onItemRemoved(final FilterItemView view) {
        Coord coord = selectedFilters.get(view);
        if (coord != null && collapseFilterView.removeItem(view)) {
            selectedFilters.remove(view);
            selectedItems.remove(items.get(view));
            removedFilters.put(view, coord);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(view);
                    if (selectedFilters.isEmpty()) {
                        tvCollapse.setVisibility(VISIBLE);
                        tvCollapse.setAlpha(1f);
                    }
                }
            }, 200);

            notifyListener();
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER, parcelable);
        bundle.putBoolean(STATE_COLLAPSED, isCollapsed);

        if (selectedItems instanceof Serializable) {
            bundle.putSerializable(STATE_SELECTED, (Serializable) selectedItems);
        }
        if (removedItems instanceof Serializable) {
            bundle.putSerializable(STATE_REMOVED, (Serializable) removedItems);
        }
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = ((Bundle) state).getParcelable(STATE_SUPER);
            super.onRestoreInstanceState(bundle);
            selectedItems = (List<T>) bundle.getSerializable(STATE_SELECTED);
            removedItems = (List<T>) bundle.getSerializable(STATE_REMOVED);
            isCollapsed = bundle.getBoolean(STATE_COLLAPSED);

            expandFilterView.post(new Runnable() {
                @Override
                public void run() {
                    restore(expandFilterView.getFilters());
                }
            });
        }
    }

    private boolean checkName(T item, FilterItemView itemView) {
        return itemView.getStrName().equals(item.getName());
    }

    private void restore(final LinkedHashMap<FilterItemView, Coord> filters) {
        selectedFilters.clear();
        expandFilterView.post(new Runnable() {
            @Override
            public void run() {
                while (filters.keySet().iterator().hasNext()) {
                    FilterItemView itemView = filters.keySet().iterator().next();
                    Coord coord = filters.get(itemView);

                    for (T selectedItem : selectedItems) {
                        if (checkName(selectedItem, itemView)) {
                            selectedFilters.put(itemView, coord);
                            itemView.select(false);
                        } else {
                            removedFilters.put(itemView, coord);
                            itemView.deselect(false);
                        }
                    }
                }

                if (isCollapsed) {
                    collapse(1);
                } else {
                    expand();
                }
            }
        });
    }

    private int calculateCount(int width, int itemWidth, int margin) {
        return width / (itemWidth + margin);
    }

    private int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
