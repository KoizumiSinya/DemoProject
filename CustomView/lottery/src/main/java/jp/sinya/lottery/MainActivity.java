package jp.sinya.lottery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout rlCard1;
    private RelativeLayout rlCard2;
    private RelativeLayout rlCard3;
    private RelativeLayout rlCard4;
    private RelativeLayout rlCard5;
    private RelativeLayout rlCard6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        rlCard1 = findViewById(R.id.card1);
        rlCard2 = findViewById(R.id.card2);
        rlCard3 = findViewById(R.id.card3);
        rlCard4 = findViewById(R.id.card4);
        rlCard5 = findViewById(R.id.card5);
        rlCard6 = findViewById(R.id.card6);

        rlCard1.setOnClickListener(cardClick);
        rlCard2.setOnClickListener(cardClick);
        rlCard3.setOnClickListener(cardClick);
        rlCard4.setOnClickListener(cardClick);
        rlCard5.setOnClickListener(cardClick);
        rlCard6.setOnClickListener(cardClick);

        setCardEnable(false);

    }

    private void setCardEnable(boolean enable) {
        rlCard1.setEnabled(enable);
        rlCard2.setEnabled(enable);
        rlCard3.setEnabled(enable);
        rlCard4.setEnabled(enable);
        rlCard5.setEnabled(enable);
        rlCard6.setEnabled(enable);
    }

    View.OnClickListener cardClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setCardEnable(false);
            onSelectFlipCardAnimation(view, 100);

            switch (view.getId()) {
                case R.id.card1:
                    onSelectFlipCardAnimation(rlCard2, 600);
                    onSelectFlipCardAnimation(rlCard3, 600);
                    onSelectFlipCardAnimation(rlCard4, 600);
                    onSelectFlipCardAnimation(rlCard5, 600);
                    onSelectFlipCardAnimation(rlCard6, 600);

                    break;
                case R.id.card2:
                    onSelectFlipCardAnimation(rlCard1, 600);
                    onSelectFlipCardAnimation(rlCard3, 600);
                    onSelectFlipCardAnimation(rlCard4, 600);
                    onSelectFlipCardAnimation(rlCard5, 600);
                    onSelectFlipCardAnimation(rlCard6, 600);
                    break;
                case R.id.card3:
                    onSelectFlipCardAnimation(rlCard2, 600);
                    onSelectFlipCardAnimation(rlCard1, 600);
                    onSelectFlipCardAnimation(rlCard4, 600);
                    onSelectFlipCardAnimation(rlCard5, 600);
                    onSelectFlipCardAnimation(rlCard6, 600);
                    break;
                case R.id.card4:
                    onSelectFlipCardAnimation(rlCard2, 600);
                    onSelectFlipCardAnimation(rlCard3, 600);
                    onSelectFlipCardAnimation(rlCard1, 600);
                    onSelectFlipCardAnimation(rlCard5, 600);
                    onSelectFlipCardAnimation(rlCard6, 600);
                    break;
                case R.id.card5:
                    onSelectFlipCardAnimation(rlCard2, 600);
                    onSelectFlipCardAnimation(rlCard3, 600);
                    onSelectFlipCardAnimation(rlCard4, 600);
                    onSelectFlipCardAnimation(rlCard1, 600);
                    onSelectFlipCardAnimation(rlCard6, 600);
                    break;
                case R.id.card6:
                    onSelectFlipCardAnimation(rlCard2, 600);
                    onSelectFlipCardAnimation(rlCard3, 600);
                    onSelectFlipCardAnimation(rlCard4, 600);
                    onSelectFlipCardAnimation(rlCard5, 600);
                    onSelectFlipCardAnimation(rlCard1, 600);
                    break;
                default:
                    break;
            }
        }
    };


    public void start(View view) {
        startFlipAnimation();
    }

    public void reset(View view) {
        resetView(rlCard1);
        resetView(rlCard2);
        resetView(rlCard3);
        resetView(rlCard4);
        resetView(rlCard5);
        resetView(rlCard6);
    }

    private void startFlipAnimation() {
        onOpenFlipCardAnimation(rlCard1, 0, false);
        onOpenFlipCardAnimation(rlCard2, 60, false);
        onOpenFlipCardAnimation(rlCard3, 120, false);

        onOpenFlipCardAnimation(rlCard4, 40, false);
        onOpenFlipCardAnimation(rlCard5, 100, false);
        onOpenFlipCardAnimation(rlCard6, 160, true);
    }

    private void startMoveAnimation() {
        openExchangeXAnim(rlCard1, rlCard2, 100);
        openExchangeXAnim(rlCard5, rlCard6, 100);

        openExchangeXAnim(rlCard1, rlCard3, 300);
        openExchangeYAnim(rlCard2, rlCard4, 300, false);

        openExchangeXAnim(rlCard4, rlCard3, 500);
        openExchangeYAnim(rlCard1, rlCard5, 500, false);

        openExchangeXAnim(rlCard4, rlCard5, 700);
        openExchangeXAnim(rlCard2, rlCard6, 700);

        openExchangeXAnim(rlCard2, rlCard1, 900);
        openExchangeYAnim(rlCard3, rlCard6, 900, false);

        openExchangeXAnim(rlCard6, rlCard5, 1100);
        openExchangeYAnim(rlCard4, rlCard2, 1100, false);

        openExchangeXAnim(rlCard1, rlCard4, 1300);
        openExchangeYAnim(rlCard5, rlCard3, 1300, false);

        openExchangeXAnim(rlCard5, rlCard4, 1500);
        openExchangeYAnim(rlCard2, rlCard1, 1500, true);
    }

    private void startMergeYAnimation() {
        mergeYAnimation(rlCard3, rlCard4, rlCard3.getHeight() / 2, 100, false, true);
        mergeYAnimation(rlCard6, rlCard5, rlCard3.getHeight() / 2, 100, false, true);
        mergeYAnimation(rlCard1, rlCard2, rlCard3.getHeight() / 2, 100, true, true);
    }

    private void startSplitYAnimation() {
        mergeYAnimation(rlCard4, rlCard3, rlCard3.getHeight() / 2, 100, false, false);
        mergeYAnimation(rlCard5, rlCard6, rlCard3.getHeight() / 2, 100, false, false);
        mergeYAnimation(rlCard2, rlCard1, rlCard3.getHeight() / 2, 100, true, false);
    }

    private void startMergeXAnimation() {
        mergeXAnimation(rlCard3, rlCard3.getWidth(), 100, false, true);
        mergeXAnimation(rlCard4, rlCard4.getWidth(), 100, false, true);
        mergeXAnimation(rlCard1, -rlCard1.getWidth(), 100, false, true);
        mergeXAnimation(rlCard2, -rlCard2.getWidth(), 100, true, true);
    }

    private void startSplitXAnimation() {
        mergeXAnimation(rlCard3, -rlCard3.getWidth(), 100, false, false);
        mergeXAnimation(rlCard4, -rlCard4.getWidth(), 100, false, false);
        mergeXAnimation(rlCard1, rlCard1.getWidth(), 100, false, false);
        mergeXAnimation(rlCard2, rlCard2.getWidth(), 100, true, false);
    }

    private void resetView(View view) {
        final View faceView = view.findViewById(R.id.face_view);//正面内容
        faceView.setVisibility(View.VISIBLE);
        faceView.setRotationY(0);

        final View backView = view.findViewById(R.id.back_view);//背面图案
        backView.setVisibility(View.GONE);
        backView.setRotationY(0);
        faceView.setTranslationY(0);

        view.setTranslationX(0);
        view.setTranslationY(0);
    }

    private void testOpenAllView() {
        openAllView(rlCard1);
        openAllView(rlCard2);
        openAllView(rlCard3);
        openAllView(rlCard4);
        openAllView(rlCard5);
        openAllView(rlCard6);
    }

    private void openAllView(View view) {
        final View faceView = view.findViewById(R.id.face_view);//正面内容
        final View backView = view.findViewById(R.id.back_view);//背面图案

        faceView.setVisibility(View.VISIBLE);
        faceView.setRotationY(0);
        backView.setVisibility(View.GONE);
    }

    private void onOpenFlipCardAnimation(View view, int startDelay, final boolean isLastView) {

        //获取每个card的正面显示内容，和背面图案
        final View faceView = view.findViewById(R.id.face_view);//正面内容
        final View backView = view.findViewById(R.id.back_view);//背面图案

        //正面显示内容，先向右边抖动一下，然后从右向左反转过来
        ValueAnimator faceAnim = ValueAnimator.ofFloat(0, 20, -90);
        //背面图案，在正面的抖动动画结束的时候，也是从右向左反转过来
        final ValueAnimator backAnim = ValueAnimator.ofFloat(90, 0);
        faceAnim.setInterpolator(new LinearInterpolator());
        backAnim.setInterpolator(new LinearInterpolator());
        faceAnim.setDuration(320);
        faceAnim.setStartDelay(startDelay);
        backAnim.setDuration(80);

        //不停的修改正面内容的Y轴参数
        faceAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                //Log.i("Sinya", "旋转角度：" + rotate);
                faceView.setRotationY(rotate);
            }
        });

        faceAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //启动的位置，设置在正面内容的中心点
                faceView.setPivotX(dp2px(80) / 2f);
                faceView.setPivotY(dp2px(110) / 2f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //当正面内容的动画结束的时候，显示背面图案
                backAnim.start();
                faceView.setVisibility(View.GONE);
            }
        });

        backAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                backView.setRotationY(rotate);
            }
        });
        backAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                backView.setPivotX(dp2px(80) / 2f);
                backView.setPivotY(dp2px(110) / 2f);
                backView.setRotationY(90);
                backView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isLastView) {
                    startMoveAnimation();
                }
            }
        });
        faceAnim.start();
    }

    private void onSelectFlipCardAnimation(final View view, int startDelay) {

        //获取每个card的正面显示内容，和背面图案
        final View faceView = view.findViewById(R.id.face_view);//正面内容
        final View backView = view.findViewById(R.id.back_view);//背面图案

        //正面显示内容，先向右边抖动一下，然后从右向左反转过来
        ValueAnimator backAnim = ValueAnimator.ofFloat(0, 20, -90);
        //背面图案，在正面的抖动动画结束的时候，也是从右向左反转过来
        final ValueAnimator faceAnim = ValueAnimator.ofFloat(90, 0);
        faceAnim.setInterpolator(new LinearInterpolator());
        faceAnim.setDuration(80);

        backAnim.setInterpolator(new LinearInterpolator());
        backAnim.setDuration(320);
        backAnim.setStartDelay(startDelay);

        //不停的修改正面内容的Y轴参数
        backAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                //Log.i("Sinya", "旋转角度：" + rotate);
                backView.setRotationY(rotate);
            }
        });

        backAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //启动的位置，设置在正面内容的中心点
                backView.setPivotX(dp2px(80) / 2f);
                backView.setPivotY(dp2px(110) / 2f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //当正面内容的动画结束的时候，显示背面图案
                faceAnim.start();
                backView.setVisibility(View.GONE);
            }
        });

        faceAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                faceView.setRotationY(rotate);
            }
        });
        faceAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                faceView.setPivotX(dp2px(80) / 2f);
                faceView.setPivotY(dp2px(110) / 2f);
                faceView.setRotationY(90);
                faceView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        backAnim.start();
    }

    private void openExchangeXAnim(final View view1, final View view2, int startDelay) {
        final ValueAnimator exchangeAnim = ValueAnimator.ofFloat(view1.getTranslationX(), view1.getTranslationX() + dp2px(100));
        exchangeAnim.setDuration(200);
        exchangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view1.setTranslationX(value);
            }
        });
        exchangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim.setFloatValues(view1.getTranslationX(), view1.getTranslationX() + dp2px(100));
            }
        });
        exchangeAnim.setStartDelay(startDelay);
        exchangeAnim.start();

        final ValueAnimator exchangeAnim2 = ValueAnimator.ofFloat(view2.getTranslationX(), view2.getTranslationX() - dp2px(100));
        exchangeAnim2.setDuration(200);
        exchangeAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view2.setTranslationX(value);
            }
        });
        exchangeAnim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim2.setFloatValues(view2.getTranslationX(), view2.getTranslationX() - dp2px(100));
            }
        });
        exchangeAnim2.setStartDelay(startDelay);
        exchangeAnim2.start();
    }

    private void openExchangeYAnim(final View view1, final View view2, int startDelay, final boolean isLastAnim) {
        final ValueAnimator exchangeAnim = ValueAnimator.ofFloat(view1.getTranslationY(), view1.getTranslationY() + dp2px(150));
        exchangeAnim.setDuration(200);
        exchangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view1.setTranslationY(value);
                Log.i("Sinya", "view1 移动Y: " + value);
            }
        });
        exchangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim.setFloatValues(view1.getTranslationY(), view1.getTranslationY() + dp2px(150));
            }
        });
        exchangeAnim.setStartDelay(startDelay);
        exchangeAnim.start();

        final ValueAnimator exchangeAnim2 = ValueAnimator.ofFloat(view2.getTranslationY(), view2.getTranslationY() - dp2px(150));
        exchangeAnim2.setDuration(200);
        exchangeAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view2.setTranslationY(value);
                Log.i("Sinya", "view2 移动Y: " + value);
            }
        });
        exchangeAnim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim2.setFloatValues(view2.getTranslationY(), view2.getTranslationY() - dp2px(150));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isLastAnim) {
                    startMergeYAnimation();
                }
            }
        });
        exchangeAnim2.setStartDelay(startDelay);
        exchangeAnim2.start();
    }

    private void mergeYAnimation(final View view1, final View view2, final int distance, int startDelay, final boolean isLastAnim, final boolean isMerge) {
        final ValueAnimator exchangeAnim = ValueAnimator.ofFloat(view1.getTranslationY(), view1.getTranslationY() + distance);
        exchangeAnim.setDuration(200);
        exchangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view1.setTranslationY(value);
                Log.i("Sinya", "view1 移动Y: " + value);
            }
        });
        exchangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim.setFloatValues(view1.getTranslationY(), view1.getTranslationY() + distance);
            }
        });
        exchangeAnim.setStartDelay(startDelay);
        exchangeAnim.start();

        final ValueAnimator exchangeAnim2 = ValueAnimator.ofFloat(view2.getTranslationY(), view2.getTranslationY() - distance);
        exchangeAnim2.setDuration(200);
        exchangeAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view2.setTranslationY(value);
                Log.i("Sinya", "view2 移动Y: " + value);
            }
        });
        exchangeAnim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim2.setFloatValues(view2.getTranslationY(), view2.getTranslationY() - distance);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isLastAnim) {
                    if (isMerge) {
                        startMergeXAnimation();
                    } else {
                        setCardEnable(true);
                    }
                }
            }
        });
        exchangeAnim2.setStartDelay(startDelay);
        exchangeAnim2.start();
    }


    private void mergeXAnimation(final View view, final int distance, int startDelay, final boolean isLastAnim, final boolean isMerge) {
        final ValueAnimator exchangeAnim = ValueAnimator.ofFloat(view.getTranslationX(), view.getTranslationX() + distance);
        exchangeAnim.setDuration(200);
        exchangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setTranslationX(value);
            }
        });
        exchangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                exchangeAnim.setFloatValues(view.getTranslationX(), view.getTranslationX() + distance);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isLastAnim) {
                    if (isMerge) {
                        startSplitXAnimation();
                    } else {
                        startSplitYAnimation();
                    }
                }
            }
        });
        exchangeAnim.setStartDelay(startDelay);
        exchangeAnim.start();
    }


    private int dp2px(double dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
