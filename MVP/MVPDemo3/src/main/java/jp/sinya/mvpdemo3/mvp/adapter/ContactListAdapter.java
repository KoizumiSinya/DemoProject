package jp.sinya.mvpdemo3.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jp.sinya.mvpdemo3.R;
import jp.sinya.mvpdemo3.mvp.model.Contact;

/**
 * 联系人列表 RecyclerView adapter
 *
 * @author Koizumi Sinya
 * @date 2018/01/11. 21:16
 * @edithor
 * @date
 */
public class ContactListAdapter extends MvpLceRecyclerViewAdapter<List<Contact>, ContactListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Contact> dataList;

    private OnItemClickListener itemClickListener;

    public ContactListAdapter(Context context, List<Contact> dataList) {
        super(context);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_contact, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = dataList.get(position);
        holder.tvName.setText(contact.getFirst_name() + " " + contact.getLast_name());
        holder.itemView.setTag(position);
    }

    public Contact getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    @Override
    public void refreshAdapter(boolean isDownRefresh, List<Contact> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvName;
        TextView tvDate;

        public ViewHolder(View view) {
            super(view);
            imgIcon = view.findViewById(R.id.image_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}