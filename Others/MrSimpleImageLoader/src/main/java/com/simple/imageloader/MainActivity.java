package com.simple.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.simple.imageloader.org.simple.imageloader.cache.DoubleCache;
import com.simple.imageloader.org.simple.imageloader.config.ImageLoaderConfig;
import com.simple.imageloader.org.simple.imageloader.core.SimpleImageLoader;
import com.simple.imageloader.org.simple.imageloader.policy.ReversePolicy;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image);
        ImageLoaderConfig config = new ImageLoaderConfig() //
                .setLoadingPlaceholder(R.mipmap.loading) //
                .setNotFoundPlaceholder(R.mipmap.not_found) //
                .setCache(new DoubleCache(this)) //
                .setThreadCount(4) //
                .setLoadPolicy(new ReversePolicy());
        // 初始化
        SimpleImageLoader.getInstance().init(config);
        SimpleImageLoader.getInstance().displayImage(imageView, "http://192.168.1.61:8080/Sinya/maplestory.jpeg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleImageLoader.getInstance().stop();
    }
}
