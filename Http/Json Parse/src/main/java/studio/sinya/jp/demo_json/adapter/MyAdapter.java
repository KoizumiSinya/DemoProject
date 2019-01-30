package studio.sinya.jp.demo_json.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import studio.sinya.jp.demo_json.R;
import studio.sinya.jp.demo_json.bean.Person;

public class MyAdapter extends BaseAdapter {

    private List<Person> dataList;
    private Context context;

    public void setData(List<Person> data) {
        this.dataList = data;
    }

    public MyAdapter(Context context , List<Person> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (dataList != null && dataList.size() > 0) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (dataList != null && dataList.size() > 0) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderView holderView;

        if(convertView == null){
            holderView = new HolderView();

            convertView = View.inflate(context, R.layout.item_chat_list, null);

            holderView.Name = (TextView) convertView.findViewById(R.id.txt_name);
            holderView.Data = (TextView) convertView.findViewById(R.id.txt_data);
            holderView.Message = (TextView) convertView.findViewById(R.id.txt_message);

            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }

        holderView.Name.setText(dataList.get(position).name);
        holderView.Data.setText(dataList.get(position).data);
        holderView.Message.setText(dataList.get(position).message);

        return convertView;
    }

    class HolderView{
        TextView Name, Data, Message;
    }
}