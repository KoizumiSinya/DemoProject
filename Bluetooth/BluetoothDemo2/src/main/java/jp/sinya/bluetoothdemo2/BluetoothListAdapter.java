package jp.sinya.bluetoothdemo2;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Koizumi Sinya
 * @date 2018/03/18. 16:26
 * @edithor
 * @date
 */
public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.ViewHolder> {

    private Context context;
    private List<BluetoothDevice> list;
    private OnItemClickListener onItemClickListener;

    public void setList(List<BluetoothDevice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public BluetoothListAdapter(Context context, List<BluetoothDevice> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bluetooth, parent, false);
        view.setOnClickListener(clickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BluetoothDevice result = list.get(position);

        holder.tvInfo.setText(result.getAddress() + ":" + result.getName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = (int) v.getTag();
                onItemClickListener.click(position);
            }
        }
    };

    public BluetoothDevice getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStatus;
        TextView tvInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            imgStatus = itemView.findViewById(R.id.img_wifi_lock);
            tvInfo = itemView.findViewById(R.id.tv_wifi_info);
        }
    }

    public interface OnItemClickListener {
        void click(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
