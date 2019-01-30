package jp.sinya.animarecyclerview.demo;

//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.app.Activity;
//import android.graphics.Color;
//import android.support.animation.DynamicAnimation;
//import android.support.animation.FloatPropertyCompat;
//import android.support.animation.SpringAnimation;
//import android.support.animation.SpringForce;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.LinearInterpolator;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hebao.app.R;
//import com.hebao.app.activity.adapter.DailySignAdapter;
//import com.hebao.app.entity.DaySignEntity;
//import com.hebao.app.util.LogUtils;
//import com.hebao.app.util.TextViewUtils;
//import com.hebao.app.util.Utils;
//import com.hebao.app.view.CircleImageView;
//import com.hebao.app.view.DynamicChangeProgressBar;
//
//import java.util.ArrayList;
//import java.util.Random;
//
///**
// * @author COCO
// * @date 2018/11/29
// */
@Deprecated
public class DailySignNewDialog /*extends BaseEmptyDialog implements View.OnClickListener */ {
//    private final int NORMAL_SIGN = 1;
//    private final int SPECIAL_SIGN_FINAL_DAY = 2;
//    private final int SIGN_CARD_WIDTH = 74;
//
//    private Activity activity;
//    private int screenWidth;
//    private float density;
//    private DaySignEntity daySignEntity;
//    private int signStyleType = NORMAL_SIGN;
//
//    private View rootView;
//
//    //顶部
//    private RelativeLayout rlDailySignFrame;
//    private ImageView imgDialogClose;
//    private RelativeLayout rlSignListParentLayout;
//    private ImageView imgLeftSlideShadow;
//    private ImageView imgRightSlideShadow;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager linearLayoutManager;
//
//    //顶部文案
//    private LinearLayout llTopTitleLayout;
//    private TextView tvTitle;
//    private TextView tvTitleWithExperienceTip;
//    private LinearLayout llSubTitleLayout;
//    private TextView tvSubTitle;
//    private TextView tvSubTitleFlag;
//    //底部文案提示
//    private TextView tvBottomSignTip;
//
//    //第7天放大居中卡片
//    private RelativeLayout rlSignMaxDayLayout;
//    private TextView tvSignMaxDay;
//    private TextView tvSignRandomMultipleFlag;
//    private TextView tvMaxDayExperienceMultiple;
//
//    //成长值
//    private ImageView imgLeftRing;
//    private ImageView imgRightRing;
//    private RelativeLayout rlBottomGradeLayout;
//    private CircleImageView imgGradeUserHead;
//    private TextView tvGradeUserLevel;
//    private DynamicChangeProgressBar gradeProgressBar;
//
//    //特权
//    private RelativeLayout rlSignBottomFrameLayout;
//    private RelativeLayout rlSignBottomParentLayout;
//    private RelativeLayout rlBottomPrivilegeLayout;
//    private ImageView imgPrivilegeStyle;
//    private TextView tvPrivilegeUpgradeTip;
//
//    private DailySignAdapter dailySignAdapter;
//    private boolean isRotateAnimFinish = true;
//    private int llTopTitleLayoutHeight;
//
//    public DailySignNewDialog(Activity activity, int type, DaySignEntity daySignEntity) {
//        super(activity, null);
//        this.activity = activity;
//        screenWidth = Utils.getScreenDisplay(activity)[0];
//        density = activity.getResources().getDisplayMetrics().density;
//        this.daySignEntity = daySignEntity;
//        this.signStyleType = type;
//
//        //TODO
//        setContentView(getDialogView());
//        // dialog.setCancelable(false);
//    }
//
//    private View getDialogView() {
//        rootView = activity.getLayoutInflater().inflate(R.layout.dialog_new_daily_sign, null);
//
//        //顶部卡片
//        rlDailySignFrame = rootView.findViewById(R.id.rl_daily_sign_top_frame_layout); //small: w:290 h:206  big: w:290 h:275    <!--69: 通过改变高度放大弹窗-->
//        imgDialogClose = rootView.findViewById(R.id.img_dialog_close);
//        imgDialogClose.setOnClickListener(this);
//
//        llTopTitleLayout = rootView.findViewById(R.id.ll_top_title_layout); //<!--small: marginTop: 60 big: 30dp -->
//        tvTitle = rootView.findViewById(R.id.tv_sign_title); // <!--small:size 34 : big: 48 -->
//        tvTitleWithExperienceTip = rootView.findViewById(R.id.tv_sign_title_with_experience_tip);
//        llSubTitleLayout = rootView.findViewById(R.id.ll_sign_subTitle); //<!--small: marginTop: 10 big: 3-->
//        tvSubTitle = rootView.findViewById(R.id.tv_sign_subTitle); //small: textSize: 48sp big: 34-->
//        tvSubTitleFlag = rootView.findViewById(R.id.tv_sub_title_flag); //small: textSize: 14sp big: 18-->
//
//        //第7天放大居中卡片View
//        //  <!--big 水平居中： w: 103 h:154 水平居中--><!--small 列表右对齐： w: 69dp h:103dp-->
//        rlSignMaxDayLayout = rootView.findViewById(R.id.rl_anim_sign_max_day_layout);
//        tvSignMaxDay = rootView.findViewById(R.id.tv_sign_max_day); //<!--small: textSize: 13 big: 19sp-->
//        tvSignRandomMultipleFlag = rootView.findViewById(R.id.tv_random__multiple_flag); //<!--small: textSize: 13 big: 19sp-->
//        tvMaxDayExperienceMultiple = rootView.findViewById(R.id.tv_random_experience_multiple);//<!--small: textSize: 16 big: 24sp-->
//
//        tvBottomSignTip = rootView.findViewById(R.id.tv_bottom_sign_tip); //<!--normal layout: marginTop: 8 special: marginTop: 44-->
//
//        //底部成长值卡片
//        rlSignBottomFrameLayout = rootView.findViewById(R.id.rl_daily_sign_bottom_frame_layout);
//        rlSignBottomParentLayout = rootView.findViewById(R.id.rl_daily_sign_bottom_layout);
//        rlSignBottomParentLayout.setRotationX(-90);
//        rlSignBottomParentLayout.setOnClickListener(this);
//
//        imgLeftRing = rootView.findViewById(R.id.img_left_ring);
//        imgRightRing = rootView.findViewById(R.id.img_right_ring);
//        rlBottomGradeLayout = rootView.findViewById(R.id.rl_bottom_grade_layout);
//        imgGradeUserHead = rootView.findViewById(R.id.img_sign_userHead);
//        tvGradeUserLevel = rootView.findViewById(R.id.tv_sign_userLevel);
//        gradeProgressBar = rootView.findViewById(R.id.sign_grade_growth_progress_bar);
//        gradeProgressBar.setBackColor(Color.parseColor("#EFDFCB"));
//
//        //底部特权卡片1
//        rlBottomPrivilegeLayout = rootView.findViewById(R.id.rl_bottom_privilege_layout);
//        imgPrivilegeStyle = rootView.findViewById(R.id.img_privilege_style);
//        tvPrivilegeUpgradeTip = rootView.findViewById(R.id.tv_privilege_upgrade_tip);
//
//        rlSignListParentLayout = rootView.findViewById(R.id.rl_sign_list_parent_layout);
//        imgLeftSlideShadow = rootView.findViewById(R.id.img_left_slideShadow);
//        imgRightSlideShadow = rootView.findViewById(R.id.img_right_slideShadow);
//
//        recyclerView = rootView.findViewById(R.id.daily_sign_recycler_view);
//        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                imgRightSlideShadow.setVisibility(!recyclerView.canScrollHorizontally(1) ? View.GONE : View.VISIBLE);
//            }
//        });
//        dailySignAdapter = new DailySignAdapter(activity, setAdapterData());
//        dailySignAdapter.setItemClickListener(new DailySignAdapter.ItemClickListener() {
//            @Override
//            public void setOnItemClickListener(View itemView, int position) {
//                Toast.makeText(activity, "position:" + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        recyclerView.setAdapter(dailySignAdapter);
//
//        if (NORMAL_SIGN == signStyleType) {
//            llTopTitleLayout.setVisibility(View.VISIBLE);
//            llSubTitleLayout.setPadding(0, (int) (10 * density), 0, 0);
//            rlSignListParentLayout.setVisibility(View.GONE);
//
//        } else {
//            llTopTitleLayout.setVisibility(View.GONE);
//            llTopTitleLayout.setPadding(0, (int) (30 * density), 0, 0);
//            llTopTitleLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    llTopTitleLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//                    llTopTitleLayoutHeight = llTopTitleLayout.getMeasuredHeight();
//                }
//            });
//
//            llSubTitleLayout.setPadding(0, (int) (3 * density), 0, 0);
//            tvSubTitle.setTextSize(34);
//            tvSubTitleFlag.setTextSize(14);
//            rlSignListParentLayout.setVisibility(View.VISIBLE);
//            rlSignListParentLayout.setPadding(0, (int) (52 * density), 0, 0);
//            //rlSignListParentLayout.setTranslationX(screenWidth);
//            recyclerView.setScrollX(-screenWidth);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//
//        return rootView;
//    }
//
//    //TODO 模拟数据
//    private ArrayList<SignBean> setAdapterData() {
//        ArrayList<SignBean> list = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 7; i++) {
//            list.add(new SignBean(random.nextInt(5) + 1, i == 1));
//        }
//
//        return list;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_dialog_close:
//                startScaleAnimation(rootView, DynamicAnimation.SCALE_X, 1, 0);
//                //TODO 整百红包弹窗显示
//                break;
//
//            case R.id.rl_daily_sign_bottom_layout:
//                if (valueAnimator != null && valueAnimator.isRunning()) {
//                    return;
//                }
//                startRotateValueAnimation();
//                break;
//        }
//    }
//
//    private ValueAnimator valueAnimator;
//
//    private void startRotateValueAnimation() {
//        rlSignBottomParentLayout.setPivotY(0 * density);
//        rlSignBottomParentLayout.setPivotX(rlSignBottomParentLayout.getWidth() / 2);
//        valueAnimator = ValueAnimator.ofInt(0, -10, 10, 0);
//        valueAnimator.setDuration(500);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                rlSignBottomParentLayout.setRotationX(value);
//            }
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                rlSignBottomParentLayout.setRotationX(0);
//            }
//        });
//        valueAnimator.start();
//    }
//
//    public class SignBean {
//        public int multiple;
//        public boolean isSelected;
//
//        public SignBean(int i, boolean b) {
//            this.multiple = i;
//            this.isSelected = b;
//        }
//    }
//
//    /**
//     * 弹窗动画
//     */
//    @Override
//    protected void onDialogShow() {
//        isRotateAnimFinish = true;
//        startScaleAnimation(rootView, DynamicAnimation.SCALE_X, 0, 1.0f);
//
//        if (NORMAL_SIGN == signStyleType) {
//            rootView.postDelayed(new Runnable() {//弹窗高度动画  small h:206  big: h:275
//
//                @Override
//                public void run() {
//                    //Dialog高度动画
//                    float targetHeight = 275 * density;
//                    startCustomPropertyAnimation("BgHeight", rlDailySignFrame,
//                            rlDailySignFrame.getHeight(), targetHeight);
//
//                    //Title平移
//                    float titlePaddingTop = 30 * density;
//                    startCustomPropertyAnimation("titlePaddingTop", llTopTitleLayout,
//                            llTopTitleLayout.getPaddingTop(), titlePaddingTop);
//
//                    float subTitlePaddingTop = 3 * density;
//                    startCustomPropertyAnimation("subTitlePaddingTop", llSubTitleLayout,
//                            llSubTitleLayout.getPaddingTop(), subTitlePaddingTop);
//
//                    //Title字体动画
//                    startCustomPropertyAnimation("titleTextSize", tvSubTitle, 48, 34);
//                    startCustomPropertyAnimation("titleTextSize", tvSubTitleFlag, 18, 14);
//                    startAlphaAnimation(tvTitle, DynamicAnimation.ALPHA, 1, 0);
//
//                    //列表滑动进入
//                    rootView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            rlSignListParentLayout.setVisibility(View.VISIBLE);
//                            rlSignListParentLayout.setScrollX(-screenWidth);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            imgLeftSlideShadow.setVisibility(View.VISIBLE);
//                            imgRightSlideShadow.setVisibility(View.VISIBLE);
//
//                            //TODO 模拟数据
//                            //setAdapterData();
//                            startScrollAnimation(rlSignListParentLayout, DynamicAnimation.SCROLL_X, -screenWidth, 0);
//
//                            //卡片打勾动画
//                            startSelectedCardAnimation();
//                        }
//                    }, 300);
//                }
//            }, 800);
//        } else {
//            //列表滑动进入
//            rootView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    imgLeftSlideShadow.setVisibility(View.VISIBLE);
//                    imgRightSlideShadow.setVisibility(View.VISIBLE);
//
//                    //移动签到当天卡片到列表第二个位置(第三个卡片)
//                    startListScrollAnimation();
//                    //startScrollAnimation(rlSignListParentLayout, DynamicAnimation.SCROLL_X, -screenWidth, 0);
//                }
//            }, 100);
//        }
//    }
//
//    private void startListScrollAnimation() {
//        LogUtils.I("COCO", "recyclerview 宽度：" + recyclerView.getMeasuredWidth());
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(-screenWidth, 0);
//        valueAnimator.setDuration(500);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                rlSignListParentLayout.setScrollX(value);
//            }
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                int finalPosition = linearLayoutManager.findViewByPosition(selectedPosition).getLeft()
////                        - linearLayoutManager.findViewByPosition(selectedPosition).getWidth();
////                startCustomPropertyAnimation("ListScrollHorizontal", recyclerView,
////                        0, finalPosition);
//
//                recyclerView.scrollToPosition(6);
//                rootView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Dialog高度动画
//                        float targetBgHeight = 275 * density;
//                        startCustomPropertyAnimation("BgHeight", rlDailySignFrame, rlDailySignFrame.getHeight(), targetBgHeight);
//
//                        //头部和底部文案显示
//                        startAlphaAnimation(tvTitleWithExperienceTip, DynamicAnimation.ALPHA, 0, 1);
//                        startAlphaAnimation(tvBottomSignTip, DynamicAnimation.ALPHA, 0, 1);
//
//                        //列表渐变消失
//                        startAlphaAnimation(recyclerView, DynamicAnimation.ALPHA, 1, 0);
//
//                        rootView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                final View itemView = linearLayoutManager.findViewByPosition(6);
//                                if (itemView == null) {
//                                    return;
//                                }
//                                final int left = itemView.getLeft();
//                                final int top = (int) (rlSignListParentLayout.getTop() + 52 * density);
//
//                                rlSignMaxDayLayout.setVisibility(View.VISIBLE);
//                                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rlSignMaxDayLayout.getLayoutParams();
//                                params.setMargins(left, top, 0, 0);
//                                rlSignMaxDayLayout.setLayoutParams(params);
//
//                                // 第7天卡片放大
//                                startMaxDayCardScaleAnim(103, 154);
//
//                                //第7天卡片平移
//                                final int targetLeft = (int) (290 * density / 2 - 103 * density / 2);
//                                final int targetTop = (int) (275 * density / 2 - 154 * density / 2);
//                                startMaxDayCardTranslateAnim(left, top, targetLeft, targetTop);
//
//                                //卡片字体缩放
//                                startMaxDayCardTextAnim(13, 19, 16, 24);
//
//                                //TODO 抽取随机倍数
//                                rootView.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        startMaxDayCardScaleAnim(69, 103);
//
//                                        startMaxDayCardTranslateAnim(rlSignMaxDayLayout.getLeft(),//
//                                                rlSignMaxDayLayout.getTop(),//
//                                                (int) (itemView.getLeft() + 5 * density),//
//                                                (int) (llTopTitleLayoutHeight + 20 * density));
//
//                                        //卡片字体缩放
//                                        startMaxDayCardTextAnim(19, 13, 24, 16);
//
//                                        rlSignListParentLayout.setPadding(0, (int) (20 * density), 0, 0);
//                                        startAlphaAnimation(recyclerView, DynamicAnimation.ALPHA, 0, 1);
//
//                                        //头部随机倍数文案隐藏
//                                        startAlphaAnimation(tvTitleWithExperienceTip, DynamicAnimation.ALPHA, 1, 0);
//                                        llTopTitleLayout.setAlpha(0);
//                                        llTopTitleLayout.setVisibility(View.VISIBLE);
//                                        startAlphaAnimation(llTopTitleLayout, DynamicAnimation.ALPHA, 0, 1);
//
//                                        //体验金金额
//                                        startExperienceAmountScrollAnim(tvSubTitle, 6.71d, 20.13d);
//                                    }
//                                }, 500);
//                            }
//                        }, 100);
//                    }
//                }, 500);
//            }
//        });
//        valueAnimator.start();
//    }
//
//    private void startMaxDayCardTextAnim(int tartValue1, int endValue1, int startValue2, int endValue2) {
//        startCustomPropertyAnimation("MaxDayCardTitle", tvSignMaxDay, tartValue1, endValue1);
//        startCustomPropertyAnimation("MaxDayCardMultipleTip", tvSignRandomMultipleFlag, startValue2, endValue2);
//    }
//
//    private void startMaxDayCardScaleAnim(int i, int i2) {
//        startCustomPropertyAnimation("MaxDayCardWidth", rlSignMaxDayLayout, rlSignMaxDayLayout.getWidth(), i * density);
//        startCustomPropertyAnimation("MaxDayCardHeight", rlSignMaxDayLayout, rlSignMaxDayLayout.getHeight(), i2 * density);
//    }
//
//    private void startMaxDayCardTranslateAnim(int left, int top, int targetLeft, int targetTop) {
//        startCustomPropertyAnimation("MaxDayCardCenterLeft", rlSignMaxDayLayout, left, targetLeft);
//        startCustomPropertyAnimation("MaxDayCardCenterTop", rlSignMaxDayLayout, top, targetTop);
//    }
//
//    private void startExperienceAmountScrollAnim(final TextView view, double startValue, final double endValue) {
//        TextViewUtils.scrollNumByTextView(startValue, endValue, view, 1, 0,
//                new TextViewUtils.NumberScrollListener() {
//                    @Override
//                    public void numberScrollChange(double currentNum) {
//                        view.setText(Utils.formatAmountToString(currentNum));
//                    }
//
//                    @Override
//                    public void numberScrollEnd() {
//                        view.setText(Utils.formatAmountToString(endValue));
//                        startBottomCardAnimation();//吊牌动画
//                    }
//                });
//    }
//
//    private void startSelectedCardAnimation() {
//        rootView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //选中卡片 打钩和蒙层Alpha动画
//                dailySignAdapter.refreshSelectedView(true, 1);
//                //体验金金额滚动
//                rootView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startExperienceAmountScrollAnim(tvSubTitle, 6.71d, 20.13d);
//                    }
//                }, 200);
//            }
//        }, 800);
//    }
//
//    private void startBottomCardAnimation() {
//        rootView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //底部吊牌动画
//                rlSignBottomFrameLayout.setVisibility(View.VISIBLE);
//                startCustomPropertyAnimation("rlBottomCardHeight", rlSignBottomParentLayout, 0, 100 * density);
//                rootView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startAlphaAnimation(imgDialogClose, DynamicAnimation.ALPHA, 0, 1);
//                        rlSignBottomParentLayout.setPivotY(0 * density);
//                        rlSignBottomParentLayout.setPivotX(rlSignBottomParentLayout.getWidth() / 2);
//                        startRotateAnimation(rlSignBottomParentLayout, DynamicAnimation.ROTATION_X, -90, 0);
//                    }
//                }, 100);
//                rootView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //TODO 成长值进度动画
//                        gradeProgressBar.setCurExperience(1f * 2585 / 99999, 16);
//                        gradeProgressBar.updateExperience(1f * 2587 / 99999, 18 - 16);
//
//                        //TODO 成长值进度动画结束后，等级信息收缩消失，特权信息显示
//                        //startScaleAnimation(rlBottomGradeLayout, DynamicAnimation.SCALE_X, 1, 0);
//                    }
//                }, 800);
//            }
//        }, 200);
//    }
//
//    @Override
//    protected void onDialogHide() {
//        super.onDialogHide();
//        //TODO 释放资源
//    }
//
//    private void startAlphaAnimation(final View view, DynamicAnimation.ViewProperty viewProperty, float startValue, float finalPosition) {
//        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, finalPosition);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
//            @Override
//            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//                if (view.getId() == tvTitle.getId()) { //头部文案，底部文案显示
//                    tvTitleWithExperienceTip.setAlpha(1 - value);
//                    tvBottomSignTip.setAlpha(1 - value);
//                }
//                if (view.getId() == imgDialogClose.getId()) { //关闭按钮,左右环显示
//                    imgDialogClose.setAlpha(value);
//                    imgLeftRing.setAlpha(value);
//                    imgRightRing.setAlpha(value);
//                }
//            }
//        });
//        springAnimation.start();
//    }
//
//    private SpringAnimation startRotateAnimation(final View view, DynamicAnimation.ViewProperty viewProperty,
//                                                 float startValue, float finalPosition) {
//        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, finalPosition);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.start();
//
//        return springAnimation;
//    }
//
//    private void startScrollAnimation(final View view, DynamicAnimation.ViewProperty viewProperty, float startValue, float finalPosition) {
//        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, finalPosition);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.start();
//    }
//
//    private void startScaleAnimation(final View view, DynamicAnimation.ViewProperty viewProperty, float startValue, float finalPosition) {
//        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty, finalPosition);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
//            @Override
//            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//                if (view.getId() == rootView.getId()) {//弹窗缩放
//                    rootView.setScaleX(value);
//                    rootView.setScaleY(value);
//
//                } else if (view.getId() == rlBottomGradeLayout.getId()) {//等级信息显示
//                    rlBottomGradeLayout.setScaleX(value);
//                    rlBottomGradeLayout.setScaleY(value);
//                    rlBottomPrivilegeLayout.setScaleX(1 - value);
//                    rlBottomPrivilegeLayout.setScaleY(1 - value);
//                }
//            }
//        });
//        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
//            @Override
//            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
//            }
//        });
//        springAnimation.start();
//    }
//
//    /**
//     * 自定义属性动画
//     */
//    private void startCustomPropertyAnimation(final String customDynamicType, View view, float startValue, final float finalValue) {
//        FloatPropertyCompat<View> textSize =
//                new FloatPropertyCompat<View>(customDynamicType) {
//                    @Override
//                    public float getValue(View view) {
//                        return view.getHeight();
//                    }
//
//                    @Override
//                    public void setValue(View view, float value) {
//                        if (customDynamicType.equals("BgHeight") && view.getId() == rlDailySignFrame.getId()) {
//                            ViewGroup.LayoutParams params = view.getLayoutParams();
//                            params.height = (int) value;
//                            view.setLayoutParams(params);
//                        }
//                        if (customDynamicType.equals("titlePaddingTop") && view.getId() == llTopTitleLayout.getId()) {
//                            view.setPadding(0, (int) value, 0, 0);
//                        }
//                        if (customDynamicType.equals("subTitlePaddingTop") && view.getId() == llSubTitleLayout.getId()) {
//                            view.setPadding(0, (int) value, 0, 0);
//                        }
//                        if (customDynamicType.equals("titleTextSize") && view.getId() == tvSubTitle.getId()) {
//                            tvSubTitle.setTextSize(value);
//
//                        } else if (customDynamicType.equals("titleTextSize") && view.getId() == tvSubTitleFlag.getId()) {
//                            tvSubTitleFlag.setTextSize(value);
//
//                        } else if (customDynamicType.equals("rlBottomCardHeight") && view.getId() == rlSignBottomParentLayout.getId()) {
//                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                            layoutParams.height = (int) value;
//                            view.setLayoutParams(layoutParams);
//
//                        } else if (customDynamicType.equals("ListScrollHorizontal") && view.getId() == recyclerView.getId()) {
//                            recyclerView.smoothScrollBy((int) value, 0);
//
//                        } else if (customDynamicType.equals("MaxDayCardMultipleTip") && view.getId() == tvSignRandomMultipleFlag.getId()) {
//                            tvSignRandomMultipleFlag.setTextSize(value);
//                            tvMaxDayExperienceMultiple.setTextSize(value);
//
//                        } else if (customDynamicType.equals("MaxDayCardTitle") && view.getId() == tvSignMaxDay.getId()) {
//                            tvSignMaxDay.setTextSize(value);
//
//                        } else if (customDynamicType.equals("MaxDayCardWidth") && view.getId() == rlSignMaxDayLayout.getId()) {
//                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                            layoutParams.width = (int) value;
//                            view.setLayoutParams(layoutParams);
//
//                        } else if (customDynamicType.equals("MaxDayCardHeight") && view.getId() == rlSignMaxDayLayout.getId()) {
//                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                            layoutParams.height = (int) value;
//                            view.setLayoutParams(layoutParams);
//
//                        } else if (customDynamicType.equals("MaxDayCardCenterLeft") && view.getId() == rlSignMaxDayLayout.getId()) {
//                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rlSignMaxDayLayout.getLayoutParams();
//                            params.setMargins((int) value, params.topMargin, 0, 0);
//                            rlSignMaxDayLayout.setLayoutParams(params);
//
//                        } else if (customDynamicType.equals("MaxDayCardCenterTop") && view.getId() == rlSignMaxDayLayout.getId()) {
//                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rlSignMaxDayLayout.getLayoutParams();
//                            params.setMargins(params.leftMargin, (int) value, 0, 0);
//                            rlSignMaxDayLayout.setLayoutParams(params);
//
//                        }
//                    }
//                };
//        SpringAnimation springAnimation = new SpringAnimation(view, textSize, finalValue);
//        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
//        springAnimation.setStartValue(startValue);
//        springAnimation.start();
//    }
}
