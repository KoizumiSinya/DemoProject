package jp.sinya.bitmapdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Koizumi Sinya
 * @date 2018/01/03. 18:34
 * @edithor
 * @date
 */
public class MyImageLoader {
    private static MyImageLoader loader;
    private LruCache<String, Bitmap> cache;

    private MyImageLoader() {
        //最大可用的内存空间
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        cache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static MyImageLoader getInstance() {
        if (loader == null) {
            synchronized (MyImageLoader.class) {
                if (loader == null) {
                    loader = new MyImageLoader();
                }
            }
        }
        return loader;
    }

    public void displayImage(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            downLoadImage(imageView, url);
        }
    }

    private void downLoadImage(ImageView imageView, String url) {
        ImageLoadTask task = new ImageLoadTask(imageView);
        task.execute(url);
    }

    private Bitmap getBitmapFromCache(String url) {
        return cache.get(url);
    }

    private void putBitmapToCache(Bitmap bitmap, String url) {
        if (bitmap != null) {
            cache.put(url, bitmap);
        }
    }

    /**
     * 常见的方式是写一个内部类的 Task来实现加载图片，大致代码模板如下
     * 但是希望封装一个更合理的工具类型的Task，就需要创建一个自定义且附带内部参数的Task
     */
    class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {
        private ImageView imageView;

        public ImageLoadTask(ImageView imageView) {
            this.imageView = imageView;
        }


        public byte[] getBytes(InputStream is) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //把所有的变量收集到一起，然后一次性把数据发送出去
            byte[] buffer = new byte[1024]; // 用数据装
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();

            return outputStream.toByteArray();
        }

        /**
         * 预先处理
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //可以用来进行显示进度条的初始化、设置
        }

        /**
         * 后台进程执行
         *
         * @param strings
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                byte[] data = getBytes(inputStream);
                bitmap = BitmapUtil.ratio(data, imageView.getWidth(), imageView.getHeight());
                putBitmapToCache(bitmap, strings[0]);
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        /**
         * UI线程，用于对后台任务的结果处理，当doInBackground结束返回的时候 执行
         *
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.i("Sinya", "下载完毕");
            imageView.setImageBitmap(bitmap);
        }

        /**
         * UI线程，当当doInBackground方法在进行的过程中，调用publishProgress的话，就会执行
         * 一般可以用来处理对 progress控件的更新
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}