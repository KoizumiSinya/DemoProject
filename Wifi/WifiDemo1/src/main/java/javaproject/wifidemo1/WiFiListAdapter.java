package javaproject.wifidemo1;

import android.content.Context;
import android.net.wifi.ScanResult;
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
public class WiFiListAdapter extends RecyclerView.Adapter<WiFiListAdapter.ViewHolder> {

    private Context context;
    private List<ScanResult> list;
    private OnItemClickListener onItemClickListener;

    public void setList(List<ScanResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public WiFiListAdapter(Context context, List<ScanResult> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wifi, parent, false);
        view.setOnClickListener(clickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScanResult result = list.get(position);

        holder.tvInfo.setText(result.SSID);

        if (result.capabilities.contains("WEP") || result.capabilities.contains("PSK") //
                || result.capabilities.contains("EAP") || result.capabilities.contains("EAP2")) {
            holder.imgStatus.setVisibility(View.VISIBLE);
        } else {
            holder.imgStatus.setVisibility(View.INVISIBLE);
        }

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

    public ScanResult getItem(int position) {
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
