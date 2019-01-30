package jp.sinya.coordinatorlayout.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Sinya
 * @date 2018/10/30. 13:07
 * @edithor
 * @date
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.HolderView> {


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        HolderView holderView = new HolderView(layout);
        return holderView;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        holderView.textView.setText("Item: " + i);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class HolderView extends RecyclerView.ViewHolder {
        public TextView textView;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
        }
    }
}
