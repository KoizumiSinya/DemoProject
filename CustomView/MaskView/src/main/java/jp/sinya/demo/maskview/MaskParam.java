package jp.sinya.demo.maskview;

import android.graphics.Color;
import android.support.constraint.solver.widgets.ConstraintWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sinya
 * @date 2018/07/25. 16:12
 * @edithor
 * @date
 */
public class MaskParam {

    /**
     * 0 circle; 1 rectRound
     */
    private int typeMask;
    private int bgColor;
    private int margin;
    private int roundRadius;
    private int radius;
    private List<Tip> tips;

    public int getBgColor() {
        if (bgColor == 0) {
            bgColor = Color.parseColor("#CC000000");
        }
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getRoundRadius() {
        return roundRadius;
    }

    public void setRoundRadius(int roundRadius) {
        this.roundRadius = roundRadius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTypeMask() {
        return typeMask;
    }

    public void setTypeMask(int typeMask) {
        this.typeMask = typeMask;
    }

    public List<Tip> getTips() {
        if (this.tips == null) {
            this.tips = new ArrayList<>();
        }
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public void addTip(Tip tip) {
        getTips().add(tip);
    }

    public static class Tip {
        private TipText text;
        private TipImage image;

        public TipText getText() {
            return text;
        }

        public void setText(TipText text) {
            this.text = text;
        }

        public TipImage getImage() {
            return image;
        }

        public void setImage(TipImage image) {
            this.image = image;
        }
    }

    public static class TipText {
        private String tip;
        private int color;
        private int size;
        private int marginLeft;
        private int marginTop;
        private int marginRight;
        private int marginBottom;
        private int width;
        private int height;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getMarginTop() {
            return marginTop;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }

        private ConstraintWidget.ContentAlignment toLocationOfImage;

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public int getColor() {
            if (color == 0) {
                color = Color.parseColor("#FFFFFF");
            }
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public ConstraintWidget.ContentAlignment getToLocationOfImage() {
            return toLocationOfImage;
        }

        public void setToLocationOfImage(ConstraintWidget.ContentAlignment toLocationOfImage) {
            this.toLocationOfImage = toLocationOfImage;
        }
    }

    public static class TipImage {
        private int width;
        private int height;
        private int resId;
        private int marginLeft;
        private int marginTop;
        private int marginRight;
        private int marginBottom;

        public int getMarginTop() {
            return marginTop;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        private ConstraintWidget.ContentAlignment toLocationOfMask;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }

        public ConstraintWidget.ContentAlignment getToLocationOfMask() {
            return toLocationOfMask;
        }

        public void setToLocationOfMask(ConstraintWidget.ContentAlignment toLocationOfMask) {
            this.toLocationOfMask = toLocationOfMask;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }
    }
}
