package jp.sinya.tallybook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jp.sinya.tallybook.model.Tally;

/**
 * @author Koizumi Sinya
 * @date 2018/01/02. 21:59
 * @edithor
 * @date
 */
public class TallyAdapter extends RecyclerView.Adapter<TallyAdapter.TallyViewHolder> {

    private List<Tally> dataList;
    private Context context;

    public TallyAdapter(Context context, List<Tally> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public TallyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TallyViewHolder holder = new TallyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycleview, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TallyViewHolder holder, int position) {
        holder.setTitle(dataList.get(position).getTitle());
        holder.setDate(dataList.get(position).getDate());
        holder.setCost(dataList.get(position).getCost());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class TallyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvCost;

        public TallyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvDate = itemView.findViewById(R.id.date);
            tvCost = itemView.findViewById(R.id.money);
        }

        private void setTitle(String title) {
            tvTitle.setText(title);
        }

        private void setDate(String date) {
            tvDate.setText(date);
        }

        private void setCost(float cost) {
            tvCost.setText("" + cost);
        }
    }
}

