package com.tgi.multiple_category_view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgi.multiple_category_view.OnFilterItemListener;
import com.tgi.multiple_category_view.R;

import java.io.Serializable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author: Sinya
 * @date: 2019/02/27. 19:37
 * @editor:
 * @edit date:
 */
public class FilterItemView extends FrameLayout implements Serializable {
    private boolean isFilterSelected;
    private boolean isIncreased;

    private float startX;
    private float startY;

    @ColorInt
    private int cancelIconTint = Color.WHITE;
    @ColorInt
    private int color;
    @ColorInt
    private int checkedColor;
    @ColorInt
    private int strokeColor;
    @ColorInt
    private int checkedTextColor;
    @ColorInt
    private int textColor;

    @DrawableRes
    private int cancelIcon = R.drawable.ic_cancel;

    private Typeface typeface;

    private TextView tvName;
    private RelativeLayout rlBg;

    private AppCompatImageView imgCancel;
    private AppCompatImageView imgLeft;
    private AppCompatImageView imgRight;

    private String strName;

    private float circlePosition;
    private float cornerRadius = 100f;

    private int collapsedSize;
    private int fullSize;
    private int strokeWidth;

    private OnFilterItemListener onFilterItemListener;

    public int getFullSize() {
        return fullSize;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public FilterItemView(@androidx.annotation.NonNull Context context) {
        super(context);
    }

    public FilterItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        tvName.setTypeface(typeface);
    }

    public String getStrName() {
        return tvName.getText().toString();
    }

    public void setStrName(String strName) {
        this.strName = strName;
        tvName.setText(strName);
    }

    public float getCirclePosition() {
        return rlBg.getWidth() / 2f + 1f;
    }

    public int getCollapsedSize() {
        return imgLeft.getWidth();
    }


    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        updateBackground();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_filter, this, true);

        strokeWidth = dp2px(getContext(), 1.25f);

        imgLeft = findViewById(R.id.viewLeft);
        imgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIncreased) {
                    if (isFilterSelected) {
                        deselect(true);
                    } else {
                        select(true);
                    }
                } else {
                    dismiss();
                }
            }
        });

        imgRight = findViewById(R.id.viewRight);
        imgRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imgLeft.performClick();
            }
        });

        rlBg = findViewById(R.id.textBackground);
        rlBg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName.performClick();
            }
        });

        tvName = findViewById(R.id.textView);
        tvName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIncreased) {
                    if (isFilterSelected) {
                        deselect(true);
                    } else {
                        select(true);
                    }
                }
            }
        });

        imgCancel = findViewById(R.id.buttonCancel);
        imgCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isIncreased) {
                    dismiss();
                } else {
                    imgLeft.performClick();
                }
            }
        });
        imgCancel.setSupportBackgroundTintList(ColorStateList.valueOf(cancelIconTint));
        isIncreased = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        imgCancel.setBackgroundResource(cancelIcon);
        if (fullSize == 0) {
            fullSize = getMeasuredWidth();
        }
    }

    private void updateBackground() {

    }

    public void select(boolean flag) {
        isIncreased = true;
        isFilterSelected = true;
        updateView();

        if (flag) {
            if (onFilterItemListener != null) {
                onFilterItemListener.onItemSelected(this);
            }
        }
    }

    public void deselect(boolean flag) {
        isFilterSelected = false;
        updateView();

        if (flag) {
            if (onFilterItemListener != null) {
                onFilterItemListener.onItemUnSelected(this);
            }
        }
    }

    public void dismiss() {
        if (onFilterItemListener != null) {
            onFilterItemListener.onItemRemoved(this);
        }
    }

    private void updateView() {
        updateTextColor();
        updateBackground();
    }

    private void updateTextColor() {
        int color;

        if (isFilterSelected) {
            color = checkedTextColor;
        } else {
            color = textColor;
        }

        if (color != 0) {
            tvName.setTextColor(color);
        }
    }

    public void removeFromParent() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }


    public void increase(float ratio) {
        tvName.setScaleX(1f);
        tvName.setAlpha(ratio);
        imgCancel.setAlpha(1 - imgCancel.getAlpha());
        rlBg.setScaleX(ratio);
        imgLeft.setTranslationX(circlePosition * (1 - ratio));
        imgRight.setTranslationX(-circlePosition * (1 - ratio));

        if (ratio == 1f) {
            imgCancel.setVisibility(View.GONE);
            fullSize = getMeasuredWidth();
        }

        isIncreased = true;
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
