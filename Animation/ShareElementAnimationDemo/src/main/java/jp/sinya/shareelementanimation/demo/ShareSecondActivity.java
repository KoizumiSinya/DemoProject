package jp.sinya.shareelementanimation.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.widget.ImageView;

public class ShareSecondActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChangeBounds shareTrans = new ChangeBounds();

        //会直接跳动到对应的位置，没有明显的动画特效
        //Slide shareTrans = new Slide();

        //Explode shareTrans = new Explode();

        //ChangeTransform shareTrans = new ChangeTransform();
        //ChangeImageTransform shareTrans = new ChangeImageTransform();
        //ChangeClipBounds shareTrans = new ChangeClipBounds();

        shareTrans.setDuration(2000);
        getWindow().setSharedElementEnterTransition(shareTrans);

        setContentView(R.layout.activity_share_second);
        img = findViewById(R.id.activity_share_second_img);


    }
}
//