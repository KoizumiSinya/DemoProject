package com.tgi.multiple_category_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tgi.multiple_category_view.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author: Sinya
 * @date: 2019/02/27. 20:05
 * @editor:
 * @edit date:
 */
public class CollapseView extends FrameLayout {

    private AppCompatButton btn;
    private AppCompatImageView img;

    public void setOnClickListener(OnClickListener listener) {
        btn.setOnClickListener(listener);
        img.setOnClickListener(listener);
    }

    public CollapseView(@NonNull Context context) {
        this(context, null);
    }

    public CollapseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_collapse, this, true);

        btn = findViewById(R.id.buttonOk);
        img = findViewById(R.id.imageArrow);
    }

    public void setText(String text) {
        btn.setText(text);
    }

    public void setHasText(boolean isHas) {
        if (isHas) {
            btn.setVisibility(VISIBLE);
        } else {
            btn.setVisibility(GONE);
        }
    }

    public void rotateArrow(float rotate) {
        img.setRotation(rotate);
    }

    public void turnIntoOkButton(float ratio) {
        if (btn.getVisibility() == VISIBLE) {

        }
    }

    private float getIncreasingScale(float ratio) {
        if (ratio < 0.5f) {
            return 0f;
        } else {
            return 2 * ratio - 1;
        }
    }

    private float getDecreasingScale(float ratio) {
        if (ratio > 0.5f) {
            return 0f;
        } else {
            return 1 - 2 * ratio;
        }
    }

    private void scale(float btnScale, float imgScale) {
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        img.setScaleX(imgScale);
        img.setScaleY(imgScale);
    }
}
