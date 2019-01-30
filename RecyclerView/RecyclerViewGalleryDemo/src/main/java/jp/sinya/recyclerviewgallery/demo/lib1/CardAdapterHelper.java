package jp.sinya.recyclerviewgallery.demo.lib1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class CardAdapterHelper {
    private boolean isHorizontal;
    private int mPagePadding = 15;
    private int mShowLeftCardWidth = 15;

    public CardAdapterHelper(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public void onCreateViewHolder(ViewGroup parent, View itemView) {
        if (isHorizontal) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            lp.width = parent.getWidth() - dp2px(itemView.getContext(), 2 * (mPagePadding + mShowLeftCardWidth));
            itemView.setLayoutParams(lp);
        }
    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = dp2px(itemView.getContext(), mPagePadding);
        itemView.setPadding(padding, 0, padding, 0);

        int startMargin = position == 0 ? padding + dp2px(itemView.getContext(), mShowLeftCardWidth) : 0;
        int endMargin = position == itemCount - 1 ? padding + dp2px(itemView.getContext(), mShowLeftCardWidth) : 0;
        if (isHorizontal) {
            setViewMargin(itemView, startMargin, 0, endMargin, 0);
        } else {
            setViewMargin(itemView, 0, startMargin, 0, endMargin);
        }
    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}