package jp.sinya.mvpdemo2.simple.demo15.impl.refresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

/**
 * 作者: Dream on 16/6/27 22:39
 * QQ:510278658
 * E-mail:510278658@qq.com
 */
public abstract class BaseRefreshAdapter<M,VH extends RecyclerView.ViewHolder> extends BaseRecyclerAdapter<VH> {

    private Context context;
    private LayoutInflater inflater;

    public BaseRefreshAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public abstract void refreshAdapter(boolean isDownRefresh,M data);
}
