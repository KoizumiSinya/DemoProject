package jp.sinya.mvpdemo3.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 17:00
 * @edithor
 * @date
 */
public abstract class MvpLceRecyclerViewAdapter<M, VH extends RecyclerView.ViewHolder> extends BaseMvpLceRecyclerViewAdapter<VH> {

    private Context context;
    private LayoutInflater inflater;

    public MvpLceRecyclerViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public abstract void refreshAdapter(boolean isDownRefresh, M data);
}
