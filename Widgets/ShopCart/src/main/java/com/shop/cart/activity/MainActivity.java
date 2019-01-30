package com.shop.cart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shop.cart.R;
import com.shop.cart.bean.MedicineBean;
import com.shop.cart.utils.ImageLoaderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private List<MedicineBean> mList;

    private HashMap<MedicineBean, Boolean> isSelected;

    private ShopCarAdapter mAdapter;

    private CheckBox cbSelectAll;
    private ListView lv;
    private TextView tvTotal;

    private String[] arrays = new String[]{"http://p3.maiyaole.com/img/50082/50082920/org_org.jpg","http://p3.maiyaole.com/img/5421/5421160/org_org.jpg","http://p3.maiyaole.com/img/201403/18/org_20140318163757977.jpg",
            "http://p3.maiyaole.com/img/201311/05/org_20131105154243228.jpg","http://p2.maiyaole.com/img/201403/18/org_20140318161348114.jpg","http://p1.maiyaole.com/img/50003/50003590/330_330.jpg",
            "http://p4.maiyaole.com/img/7835/7835068/org_org.jpg","http://p1.maiyaole.com/img/201403/18/org_20140318153848796.jpg","http://p1.maiyaole.com/img/201507/08/org_20150708221453531.jpg",
            "http://p3.maiyaole.com/img/971/971706/org_org.jpg","http://p2.maiyaole.com/img/50087/50087902/org_org.jpg","http://p1.maiyaole.com/img/50007/50007206/org_org.jpg","http://p2.maiyaole.com/img/201512/21/org_20151221111224709.jpg",
            "http://p1.maiyaole.com/img/201403/18/org_20140318163447145.jpg","http://p2.maiyaole.com/img/201403/18/org_20140318154746153.jpg","http://p1.maiyaole.com/img/972/972424/org_org.jpg",
            "http://p3.maiyaole.com/img/971/971777/org_org.jpg?a=1350062195","http://p2.maiyaole.com/img/972/972669/org_org.jpg","http://p4.maiyaole.com/img/971/971919/org_org.jpg","http://p1.maiyaole.com/img/50092/50092422/org_org.jpg",
            "http://p2.maiyaole.com/img/971/971744/org_org.jpg?a=1956486983","http://p2.maiyaole.com/img/972/972419/org_org.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        initView();
        initData();
        mAdapter = new ShopCarAdapter(this);
        lv.setAdapter(mAdapter);
        handler.sendEmptyMessage(0);
    }

    private void initView() {
        cbSelectAll = (CheckBox) findViewById(R.id.cbSelectAll);
        lv = (ListView) findViewById(R.id.lv);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        cbSelectAll.setOnCheckedChangeListener(this);
    }

    private void initData() {
        isSelected = new HashMap<>();
        mList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            MedicineBean medicineBean = new MedicineBean();
            medicineBean.name = "贵州百灵 维C银翘片" + i;
            medicineBean.describe = "解热镇痛。用于感冒引起的头痛，发热，鼻噻，流涕，咽痛。";
            medicineBean.price = 1.20f + i;
            medicineBean.number = 2 + i;
            medicineBean.img = arrays[new Random().nextInt(21)];
            mList.add(medicineBean);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    cbSelectAll.setChecked(isSelected.size() == mList.size());
                case 0:
                    float total = 0f;
                    Iterator<Map.Entry<MedicineBean, Boolean>> iterator = isSelected.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<MedicineBean, Boolean> entry = iterator.next();
                        MedicineBean b = entry.getKey();
                        total += b.number * b.price;
                    }
                    tvTotal.setText(getString(R.string.total, String.format("%.2f", total)));
                    break;
            }
        }
    };


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked && isSelected.size() != mList.size()) {
            return;
        }
        isSelected.clear();
        if (isChecked) {
            for (int i = 0; i < mList.size(); i++) {
                isSelected.put(mList.get(i), true);
            }
        }
        mAdapter.notifyDataSetChanged();
        handler.sendEmptyMessage(0);
    }

    private class ShopCarAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private DisplayImageOptions options;

        private SimpleImageLoadingListener listener;

        public ShopCarAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            options = ImageLoaderFactory.getImageLvOptions(new RoundedBitmapDisplayer(10));
            listener = new ImageLoaderFactory.AnimateFirstDisplayListener();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (null == view) {
                view = mInflater.inflate(R.layout.lv_item_shop_cart, parent, false);
                holder = new ViewHolder();
                holder.cbItem = (CheckBox) view.findViewById(R.id.cbItem);
                holder.tvGoodNum = (TextView) view.findViewById(R.id.tvGoodNum);
                holder.tvMedicineName = (TextView) view.findViewById(R.id.tvMedicineName);
                holder.tvMedicineInfo = (TextView) view.findViewById(R.id.tvMedicineInfo);
                holder.tvMedicinePrice = (TextView) view.findViewById(R.id.tvMedicinePrice);
                holder.imgCartAdd = (ImageView) view.findViewById(R.id.imgCartAdd);
                holder.imgCartDel = (ImageView) view.findViewById(R.id.imgCartDel);
                holder.imgCartCut = (ImageView) view.findViewById(R.id.imgCartCut);
                holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.setPosition(position);
            holder.init();
            return view;
        }

        private class ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
            CheckBox cbItem;

            TextView tvGoodNum;
            TextView tvMedicineName;
            TextView tvMedicineInfo;
            TextView tvMedicinePrice;

            private ImageView imgCartAdd;
            private ImageView imgCartDel;
            private ImageView imgCartCut;
            private ImageView imgIcon;

            MedicineBean bean;
            private int position;

            public void setPosition(int position) {
                this.position = position;
            }

            public void init() {
                bean = mList.get(position);
                tvGoodNum.setText(String.valueOf(bean.number));
                tvMedicineName.setText(bean.name);
                tvMedicineInfo.setText(bean.describe);
                tvGoodNum.setText(String.valueOf(bean.number));
                tvMedicinePrice.setText(getString(R.string.price, String.valueOf(bean.price)));
                ImageLoader.getInstance().displayImage(bean.img, imgIcon, options, listener);
                cbItem.setOnCheckedChangeListener(this);
                imgCartAdd.setOnClickListener(this);
                imgCartDel.setOnClickListener(this);
                imgCartCut.setOnClickListener(this);
                boolean b = isSelected.get(bean) != null && isSelected.get(bean) ? true : false;
                cbItem.setChecked(b);
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isSelected.put(bean, isChecked);
                    handler.sendEmptyMessage(1);
                } else if (isSelected.containsKey(bean) && !isChecked) {
                    isSelected.remove(bean);
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.imgCartCut:
                        if (bean.number > 1) {
                            bean.number--;
                        }
                        tvGoodNum.setText(String.valueOf(bean.number));
                        handler.sendEmptyMessage(0);
                        break;
                    case R.id.imgCartAdd:
                        bean.number++;
                        tvGoodNum.setText(String.valueOf(bean.number));
                        handler.sendEmptyMessage(0);
                        break;
                    case R.id.imgCartDel:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("确定要删除吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (isSelected.containsKey(bean)) {
                                    isSelected.remove(bean);
                                }
                                mList.remove(bean);
                                notifyDataSetChanged();
                                handler.sendEmptyMessage(0);
                            }
                        }).show();
                        break;
                }
            }
        }
    }
}
