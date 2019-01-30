package jp.sinya.camerademo1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Sinya/temp.png";
        imagePath = Environment.getExternalStorageDirectory() + File.separator + "temp123.jpg";
        Log.i("Sinya", "onCreate: 路径-" + imagePath);
        imageView = findViewById(R.id.img);
    }

    public void openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    public void openCamera2(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //将系统拍照之后的图片路径更改成指定的文件目录保存
        Uri uri = Uri.fromFile(new File(imagePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 101);
    }

    public void openCamera3(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == AppCompatActivity.RESULT_OK) {

            //使用这个方式获取回来的图片实质上是个缩略图
            if (requestCode == 100) {
                Bundle bundle = intent.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);

            } else if (requestCode == 101) {
                Log.i("Sinya", "onActivityResult: 获取图片资源路径");
                FileInputStream fs = null;
                Bitmap bitmap = null;
                try {
                    fs = new FileInputStream(imagePath);
                    bitmap = BitmapFactory.decodeStream(fs);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fs != null) {
                        try {
                            fs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
