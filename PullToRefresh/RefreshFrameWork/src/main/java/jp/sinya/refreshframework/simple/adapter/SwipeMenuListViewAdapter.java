package jp.sinya.refreshframework.simple.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import jp.sinya.refreshframework.R;
import jp.sinya.refreshframework.swipemenulistview.SlidingItembean;

/**
 * @author KoizumiSinya
 * @date 2016/05/26.
 */
public class SwipeMenuListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private List<SlidingItembean> beanList;
    private OnMenuClickListener mOnMenuClickListener;
    private int menuWidth;

    public SwipeMenuListViewAdapter(Context context, int menuWidth) {
        mContext = context;
        this.menuWidth = menuWidth;
    }

    public void setData(List<SlidingItembean> list) {
        this.beanList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (beanList == null) {
            return 0;
        }
        return beanList.size();
    }

    @Override
    public SlidingItembean getItem(int position) {
        if (beanList != null && beanList.size() > 0) {
            return beanList.get(position);
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
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.simple_swipe_menu_listview_item, null);
            holderView = new HolderView(convertView);

            holderView.ll_menu1.setOnClickListener(this);
            holderView.ll_menu1.setTag(position);
            holderView.ll_menu2.setOnClickListener(this);
            holderView.ll_menu2.setTag(position);

            convertView.setTag(holderView);

        } else {
            holderView = (HolderView) convertView.getTag();
        }

        LinearLayout.LayoutParams ll_main = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        holderView.rl_main_layout.setLayoutParams(ll_main);

        LinearLayout.LayoutParams ll_menu = new LinearLayout.LayoutParams(menuWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        holderView.ll_menu_layout.setLayoutParams(ll_menu);

        SlidingItembean bean = getItem(position);
        holderView.tv_username.setText(bean.getName());
        holderView.tv_content.setText(bean.getPath());
        holderView.tv_menu1.setText(bean.getSetTop());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_menu1:
                int position = (int) v.getTag();
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.clickMenu(position, 0);
                }
                break;


            case R.id.ll_menu2:
                int position2 = (int) v.getTag();
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.clickMenu(position2, 1);
                }
                break;
            default:
                break;
        }

    }

    class HolderView {

        RelativeLayout rl_main_layout;
        ImageView imag_icon;
        TextView tv_username, tv_content;

        LinearLayout ll_menu_layout, ll_menu1, ll_menu2;
        TextView tv_menu1, tv_menu2;

        public HolderView(View converView) {
            rl_main_layout = (RelativeLayout) converView.findViewById(R.id.rl_main_layout);
            imag_icon = (ImageView) converView.findViewById(R.id.imag_icon);
            tv_username = (TextView) converView.findViewById(R.id.tv_username);
            tv_content = (TextView) converView.findViewById(R.id.tv_content);

            ll_menu_layout = (LinearLayout) converView.findViewById(R.id.ll_menu_layout);

            ll_menu1 = (LinearLayout) converView.findViewById(R.id.ll_menu1);
            ll_menu2 = (LinearLayout) converView.findViewById(R.id.ll_menu2);

            tv_menu1 = (TextView) converView.findViewById(R.id.tv_menu1);
            tv_menu2 = (TextView) converView.findViewById(R.id.tv_menu2);
        }
    }

    public interface OnMenuClickListener {
        public void clickMenu(int position, int index);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.mOnMenuClickListener = listener;
    }


}
