package jp.sinya.recyclerviewgallery.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.sinya.recyclerviewgallery.demo.lib2.CardAdapter;
import jp.sinya.recyclerviewgallery.demo.lib2.DiscreteScrollView;
import jp.sinya.recyclerviewgallery.demo.lib2.transform.Pivot;
import jp.sinya.recyclerviewgallery.demo.lib2.transform.ScaleTransformer;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        List<Integer> res = new ArrayList<>();
        res.add(R.mipmap.test_bake);
        res.add(R.mipmap.test_boil);
        res.add(R.mipmap.test_defrost);
        res.add(R.mipmap.test_fry);
        res.add(R.mipmap.test_fully);
        res.add(R.mipmap.test_grill);
        res.add(R.mipmap.test_keeping_warm);
        res.add(R.mipmap.test_roasting);
        res.add(R.mipmap.test_scaling);
        res.add(R.mipmap.test_sous_vide);
        res.add(R.mipmap.test_steaming);

        final DiscreteScrollView scrollView = findViewById(R.id.div_recycler_view);
        CardAdapter cardAdapter = new CardAdapter(res);
        scrollView.setAdapter(cardAdapter);
        scrollView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

            }
        });
        cardAdapter.setListener(new CardAdapter.onItemClickListener() {
            @Override
            public void click(int position) {
                if (position == scrollView.getCurrentItem()) {
                    Toast.makeText(Main2Activity.this, "进入Item", Toast.LENGTH_SHORT).show();
                } else {
                    scrollView.smoothScrollToPosition(position);
                }
            }
        });
        scrollView.setItemTransformer(new ScaleTransformer.Builder() //
                .setMaxScale(1.0f)//
                .setMinScale(0.9f)//
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());
    }
}
