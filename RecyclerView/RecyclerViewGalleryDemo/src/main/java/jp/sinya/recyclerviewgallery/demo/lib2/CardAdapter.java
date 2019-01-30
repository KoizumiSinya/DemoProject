package jp.sinya.recyclerviewgallery.demo.lib2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import jp.sinya.recyclerviewgallery.demo.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Integer> mList;
    private onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public CardAdapter(List<Integer> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.itemView.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.imageView.setImageResource(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

//        public void show() {
//            int parentHeight = ((View) imageView.getParent()).getHeight();
//            float scale = (parentHeight) / (float) imageView.getHeight();
//            imageView.setPivotX(imageView.getWidth() * 0.5f);
//            imageView.setPivotY(0);
//            imageView.animate().scaleX(scale).withEndAction(new Runnable() {
//                @Override
//                public void run() {
//                    //textView.setVisibility(View.VISIBLE);
//                    imageView.setColorFilter(Color.BLACK);
//                }
//            }).scaleY(scale).setDuration(200).start();
//        }
//
//        public void hide() {
//            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.grayIconTint));
//            imageView.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
//        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (listener != null) {
                listener.click(position);
            }
        }
    };

    public interface onItemClickListener {
        void click(int position);
    }

}