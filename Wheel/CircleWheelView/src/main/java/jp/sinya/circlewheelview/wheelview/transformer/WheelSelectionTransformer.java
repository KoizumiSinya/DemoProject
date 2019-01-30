package jp.sinya.circlewheelview.wheelview.transformer;

import android.graphics.drawable.Drawable;

import jp.sinya.circlewheelview.wheelview.WheelView;


public interface WheelSelectionTransformer {
    void transform(Drawable drawable, WheelView.ItemState itemState);
}
