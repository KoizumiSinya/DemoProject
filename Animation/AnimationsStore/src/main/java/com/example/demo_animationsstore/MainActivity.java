package com.example.demo_animationsstore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;

import animationslibrary.AnimationHelp;
import animationslibrary.Techniques;

public class MainActivity extends Activity {
    private Context mContext;
    private ListView mListView;
    private MyAdapter mAdapter;
    private View animView;
    private AnimationHelp.AnimationOperation rope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mListView = (ListView) findViewById(R.id.listview);
        animView = findViewById(R.id.hello_world);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);

        rope = AnimationHelp.with(Techniques.RollIn).duration(1000).playOn(animView);// after start,just click mTarget view, rope is not init
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Techniques technique = (Techniques) view.getTag();
                rope = AnimationHelp.with(technique).duration(1200).interpolate(new AccelerateDecelerateInterpolator()).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(mContext, "canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(animView);
            }
        });

        findViewById(R.id.hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rope != null) {
                    rope.stop(true);
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Techniques.values().length;
        }

        @Override
        public Object getItem(int position) {
            return Techniques.values()[position].getAnimator();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item, null, false);
            TextView t = (TextView) v.findViewById(R.id.list_item_text);
            Object o = getItem(position);
            int start = o.getClass().getName().lastIndexOf(".") + 1;
            String name = o.getClass().getName().substring(start);
            t.setText(name);
            v.setTag(Techniques.values()[position]);
            return v;
        }
    }
}
