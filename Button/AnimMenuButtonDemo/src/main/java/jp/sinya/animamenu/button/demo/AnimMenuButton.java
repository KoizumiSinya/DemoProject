package jp.sinya.animamenu.button.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sinya
 * @date 2018/11/13. 16:15
 * @edithor
 * @date
 */
public class AnimMenuButton extends RelativeLayout {

    private ImageView img1;
    private ImageView img2;
    private List<ImageView> imgs;
    private ImageView imgButton;
    private boolean isOpen;

    public AnimMenuButton(Context context) {
        super(context);
    }

    public AnimMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_anim_button, this);
        img1 = findViewById(R.id.img1);
        img1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click item1", Toast.LENGTH_SHORT).show();
            }
        });
        img2 = findViewById(R.id.img2);
        img2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click item2", Toast.LENGTH_SHORT).show();
            }
        });

        imgs = new ArrayList<>();
        imgs.add(img1);
        imgs.add(img2);

        imgButton = findViewById(R.id.img_btn);
        imgButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    showCloseAnim(90);
                } else {
                    showOpenAnim(90);
                }
            }
        });
    }

    private void showOpenAnim(int dp) {
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);

        for (int i = 0; i < imgs.size(); i++) {
            AnimatorSet set = new AnimatorSet();

            //标题1与x轴负方向角度为20°，标题2为100°，转换为弧度
            double a = -Math.cos(60 * Math.PI / 180 * (i * 1f + 1));
            double b = -Math.sin(60 * Math.PI / 180 * (i * 1f + 1));
            double x = a * dp2px(dp);
            double y = b * dp2px(dp);

            set.playTogether(ObjectAnimator.ofFloat(imgs.get(i), "translationX", (float) (x * 0.25), (float) x), //
                    ObjectAnimator.ofFloat(imgs.get(i), "translationY", (float) (y * 0.25), (float) y), //
                    ObjectAnimator.ofFloat(imgs.get(i), "alpha", 0, 1), //
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleX", 0, 1), //
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleY", 0, 1));
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(i * 100);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = true;
                    if (!imgButton.isEnabled()) {
                        imgButton.setEnabled(true);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imgButton, "rotation", 0, 45).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();
        imgButton.setEnabled(false);
    }

    private void showCloseAnim(int dp) {
        for (int i = 0; i < imgs.size(); i++) {
            AnimatorSet set = new AnimatorSet();

            double a = -Math.cos(60 * Math.PI / 180 * (i * 1f + 1));
            double b = -Math.sin(60 * Math.PI / 180 * (i * 1f + 1));
            double x = a * dp2px(dp + 10);
            double y = b * dp2px(dp + 10);

            set.playTogether(//
                    ObjectAnimator.ofFloat(imgs.get(i), "translationX", (float) x, (float) (x * 0.25)), //
                    ObjectAnimator.ofFloat(imgs.get(i), "translationY", (float) y, (float) (y * 0.25)),//
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleX", 1, 1.1f), //
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleY", 1, 1.1f));
            set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(80);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = false;
                    if (!imgButton.isEnabled()) {
                        imgButton.setEnabled(true);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }


        for (int i = 0; i < imgs.size(); i++) {
            AnimatorSet set = new AnimatorSet();

            double a = -Math.cos(60 * Math.PI / 180 * (i * 1f + 1));
            double b = -Math.sin(60 * Math.PI / 180 * (i * 1f + 1));
            double x = a * dp2px(dp + 10);
            double y = b * dp2px(dp + 10);

            set.playTogether(//
                    ObjectAnimator.ofFloat(imgs.get(i), "translationX", (float) x, (float) (x * 0.25)), //
                    ObjectAnimator.ofFloat(imgs.get(i), "translationY", (float) y, (float) (y * 0.25)),//
                    ObjectAnimator.ofFloat(imgs.get(i), "alpha", 1.1f, 0), //
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleX", 1.1f, 0), //
                    ObjectAnimator.ofFloat(imgs.get(i), "scaleY", 1.1f, 0));
            set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(300);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    img1.setVisibility(View.GONE);
                    img2.setVisibility(View.GONE);

                    isOpen = false;
                    if (!imgButton.isEnabled()) {
                        imgButton.setEnabled(true);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        ObjectAnimator rotate = ObjectAnimator.ofFloat(imgButton, "rotation", 45, 0).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();
        imgButton.setEnabled(false);


    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
