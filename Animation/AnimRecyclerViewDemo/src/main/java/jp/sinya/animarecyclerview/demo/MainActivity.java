package jp.sinya.animarecyclerview.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

@Deprecated
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout llParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llParent = findViewById(R.id.activity_main_ll);
        recyclerView = findViewById(R.id.activity_main_recycler);

        //500毫秒 内滑动完对应的宽度
        float width = screenWidth(this);
        float speed = 500 / width;
        Log.i("Sinya", "滑动速率：" + speed);

        ScrollSpeedLinearLayoutManger linearLayoutManager = new ScrollSpeedLinearLayoutManger(this, speed, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new ItemAdapter());

        reset();
    }

    public void start(View view) {
        reset();
        startEnterAnimation(2);
    }

    private void reset() {
        llParent.setTranslationX(screenWidth(this));
        recyclerView.scrollToPosition(0);
    }

    private void startEnterAnimation(int stopPosition) {
        SpringForce spring = new SpringForce(0).setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW);
        SpringAnimation animation = new SpringAnimation(llParent, DynamicAnimation.TRANSLATION_X).setSpring(spring);
        animation.start();
    }

    private void startMoveItemAniamtion(int index) {
        int position = 5;
        Log.i("Sinya", "滑动到第：" + position);
        recyclerView.smoothScrollToPosition(position);
    }

    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(screenWidth(this), 0);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                llParent.setTranslationX(value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    public int screenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    class ItemAdapter extends RecyclerView.Adapter<HolderView> {

        @NonNull
        @Override
        public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, null);
            HolderView holderView = new HolderView(view);
            return holderView;
        }

        @Override
        public void onBindViewHolder(@NonNull HolderView viewHolder, int i) {
            viewHolder.tv.setText("item " + (i++));
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }

    class HolderView extends RecyclerView.ViewHolder {
        TextView tv;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_layout_tv);
        }
    }
}
