package javaproject.listfristloaddemo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sinya
 * @date 2018/09/16. 14:20
 * @edithor
 * @date
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private boolean isScrollEnd;

    private List<Bean> list;
    private List<Integer> animStore;
    private List<ObjectAnimator> animList;
    private Map<Integer, Boolean> mapAnimStatus;


    public MyAdapter(Context context) {
        this.context = context;
    }

    public Map<Integer, Boolean> getMapAnimStatus() {
        return mapAnimStatus;
    }

    public void setScrollEnd(boolean scrollEnd) {
        isScrollEnd = scrollEnd;
        if (isScrollEnd) {
            notifyDataSetChanged();
        } else {
            setStopAnim();
        }
    }

    public void setDataList(List<Bean> dataList) {
        this.list = dataList;
        this.animStore = new ArrayList<>();
        this.animList = new ArrayList<>();
        this.mapAnimStatus = new HashMap<>();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Bean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private void setStopAnim() {
        for (int i = 0; i < animList.size(); i++) {
            if (animList.get(i).isRunning()) {
                animList.get(i).end();
            }
        }
    }

    private void setStartAnim(final int position, ProgressBar bar) {
        if (animList.contains(position)) {
            return;
        }

        ObjectAnimator animator = ObjectAnimator.ofInt(bar, "progress", 0, 40);
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mapAnimStatus.put(getItem(position).getType(), true);
            }
        });
        animList.add(animator);
        animator.start();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.i("Sinya", "get view");
        HolderView holderView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_list_item, null);
            holderView = new HolderView(view);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }

        if (MainActivity.dataMap.get(getItem(position).getType()) != null && MainActivity.dataMap.get(getItem(position).getType())) {
            if (mapAnimStatus.get(getItem(position).getType()) == null || !mapAnimStatus.get(getItem(position).getType())) {
                Log.i("Sinya", "Item-" + getItem(position).toString() + "启动动画");
                setStartAnim(position, holderView.progressBar);
                animStore.add(position);
            }
        }
        holderView.tvName.setText(getItem(position).getName());
        return view;
    }

    class HolderView {
        TextView tvName;
        ProgressBar progressBar;

        public HolderView(View view) {
            tvName = view.findViewById(R.id.activity_list_item_tv_name);
            progressBar = view.findViewById(R.id.activity_list_item_progress);
        }
    }
}
