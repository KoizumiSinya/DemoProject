package studio.sinya.jp.demo_fallphotoview.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import studio.sinya.jp.demo_fallphotoview.R;
import test.ZoomImageView2;

/**
 * Created by SinyaKoizumi on 2015/10/22.
 */
public class ImageDetailsActivity extends Activity {

    private ZoomImageView2 imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details_view);

        imageView = (ZoomImageView2) findViewById(R.id.zoom_image);
        String imagePath = getIntent().getStringExtra("image_path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);
    }
}
