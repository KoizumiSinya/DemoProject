package jp.sinya.animarecyclerview.demo;

//import android.support.v7.widget.RecyclerView;
//import android.app.Activity;
//import android.graphics.Color;
//import android.support.animation.DynamicAnimation;
//import android.support.animation.SpringAnimation;
//import android.support.animation.SpringForce;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.hebao.app.R;
//import com.hebao.app.util.LogUtils;
//import com.hebao.app.view.dialog.DailySignNewDialog;
//
//import java.util.ArrayList;
//
@Deprecated
public class DailySignAdapter /*extends RecyclerView.Adapter*/ {
//
//    private ArrayList<DailySignNewDialog.SignBean> dataList;
//    private ItemClickListener itemClickListener;
//    private Activity activity;
//    private boolean isShowSelectedAnimation;
//    private int mCurSignDay;
//
//    public DailySignAdapter(Activity activity, ArrayList<DailySignNewDialog.SignBean> dataList) {
//        this.activity = activity;
//        if (dataList == null) {
//            dataList = new ArrayList<>();
//        }
//        this.dataList = dataList;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.recyclerview_item_daily_sign_new, parent, false);
//        return new DailySignViewHolder(view);
//    }
//
//    public void setData(ArrayList<DailySignNewDialog.SignBean> dataList, int curSignDay) {
//        if (dataList == null) {
//            dataList = new ArrayList<>();
//        }
//        this.dataList = dataList;
//        this.mCurSignDay = curSignDay;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        DailySignNewDialog.SignBean signBean = dataList.get(position);
//
//        final DailySignViewHolder signViewHolder;
//        if (holder instanceof DailySignViewHolder) {
//            signViewHolder = (DailySignViewHolder) holder;
//
//            int curDay = position + 1;
//            String dayTip = curDay + "天";
//            signViewHolder.tvContinueSignDay.setText(dayTip);
//            signViewHolder.tvExperienceMultipleTip.setText(String.format("x%s", signBean.multiple));
//
//            if (curDay == 7) {
//                signViewHolder.tvExperienceMultipleTip.setTextColor(Color.parseColor("#FF4240"));
//                signViewHolder.rlSignCardBg.setBackgroundResource(R.drawable.sign_bg_7thtyj);
//                signViewHolder.tvExperienceMultipleTip.setText("x??");
//            } else {
//                signViewHolder.tvExperienceMultipleTip.setTextColor(activity.getResources().getColor(R.color.text_black_w));
//                signViewHolder.rlSignCardBg.setBackgroundResource(signBean.multiple > 1 ? R.drawable.sign_bg_twotyj : R.drawable.sign_bg_onetyj);
//            }
//            if (position < 6) { //TODO
//                signViewHolder.imgSignBgSelected.setAlpha(1.0f);
//                signViewHolder.imgSignTicked.setScaleX(1);
//            }
//            if (signBean.isSelected && isShowSelectedAnimation) {
//                startSpringAnimation(signViewHolder, signViewHolder.imgSignBgSelected, DynamicAnimation.ALPHA, 0, 1);
//                startSpringAnimation(signViewHolder, signViewHolder.imgSignTicked, DynamicAnimation.SCALE_X, 0, 1);
//            }
//
//            signViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemClickListener != null) {
//                        itemClickListener.setOnItemClickListener(v, signViewHolder.getAdapterPosition());
//                        LogUtils.I("COCO", "getAdapterPosition with daily sign" + signViewHolder.getAdapterPosition());
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    public void setItemClickListener(ItemClickListener listener) {
//        this.itemClickListener = listener;
//    }
//
//    public void refreshSelectedView(boolean b, int selectedPosition) {
//        this.isShowSelectedAnimation = b;
//        notifyItemChanged(selectedPosition);
//    }
//
//    public interface ItemClickListener {
//        void setOnItemClickListener(View itemView, int position);
//    }
//
//    static class DailySignViewHolder extends RecyclerView.ViewHolder {
//        RelativeLayout rlSignCardBg;
//        TextView tvContinueSignDay;
//        TextView tvExperienceMultipleTip;
//        ImageView imgSignBgSelected;
//        ImageView imgSignTicked;
//
//        DailySignViewHolder(View itemView) {
//            super(itemView);
//            rlSignCardBg = itemView.findViewById(R.id.rl_sign_card_bg);
//            tvContinueSignDay = itemView.findViewById(R.id.tv_continue_sign_day);
//            tvExperienceMultipleTip = itemView.findViewById(R.id.tv_experience_multiple_tip);
//            imgSignBgSelected = itemView.findViewById(R.id.img_sign_bg_selected);
//            imgSignTicked = itemView.findViewById(R.id.img_sign_ticked);
//        }
//    }
//
//    private void startSpringAnimation(final DailySignViewHolder viewHolder,
//                                      final View view, DynamicAnimation.ViewProperty viewProperty, float startValue,
//                                      float finalPosition) {
//        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, finalPosition);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
//            @Override
//            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//                if (view.getId() == viewHolder.imgSignTicked.getId()) {
//                    view.setScaleX(value);
//                    view.setScaleY(value);
//                }
//            }
//        });
//        springAnimation.start();
//    }
}