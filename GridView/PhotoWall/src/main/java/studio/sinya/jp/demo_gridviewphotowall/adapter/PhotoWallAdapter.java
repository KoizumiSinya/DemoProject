package studio.sinya.jp.demo_gridviewphotowall.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import studio.sinya.jp.demo_gridviewphotowall.R;
import studio.sinya.jp.demo_gridviewphotowall.bean.ImageBean;

/**
 * Created by SinyaKoizumi on 2015/10/19.
 */
public class PhotoWallAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener {

    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private Set<BitmapWorkerTask> taskCollection;

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mMemoryCache;

    /**
     * GridView的实例
     */
    private GridView mPhotoWall;

    /**
     * 第一张可见图片的下标
     */
    private int mFirstVisibleItem;

    /**
     * 一屏有多少张图片可见
     */
    private int mVisibleItemCount;

    /**
     * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
     */
    private boolean isFirstEnter = true;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public PhotoWallAdapter(Context context, int resource, String[] objects, GridView photoWall) {
        super(context, resource, objects);

        mPhotoWall = photoWall;
        taskCollection = new HashSet<>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_item, null);
        } else {
            view = convertView;
        }

        final ImageView photo = (ImageView) view.findViewById(R.id.photo);
        photo.setTag(url);
        setImageView(url, photo);
        return view;
    }

    /**
     * 给ImageView设置图片
     * ①先从LruCaChe中获取缓存
     * ②如果存在，则设置图片
     *
     * @param url
     * @param imageView
     */
    private void setImageView(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.null_photo);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null
     *
     * @param key LruCache的键，这里传入图片的URL地址
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 将一张图片存储到LruCache中
     *
     * @param key
     * @param bitmap
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务线程
     */
    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    // [+] OnScrollListener

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            Log.i("Sinya", "停止界面之后加载下载图片线程");
            loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
        } else {
            Log.i("Sinya", "滑动的时候关闭下载图片线程");
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("Sinya", "firstVisibleItem = " + firstVisibleItem + "; visibleItemCount = " + visibleItemCount);
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;

        // 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，执行的是onScroll。通过以下判断去加载图片
        if (isFirstEnter && visibleItemCount > 0) {
            Log.i("Sinya", "首次进入界面获取数据");
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }
    }

    // [-] OnScrollListener
    // [+] getImageData

    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        try {
            for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
                String imageUrl = ImageBean.imageUrl[i];
                Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
                if (bitmap == null) {
                    BitmapWorkerTask task = new BitmapWorkerTask();
                    taskCollection.add(task);
                    task.execute(imageUrl);
                } else {
                    ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [-] getImageData
    // [+] Task

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Log.i("Sinya", "下载地址:" + imageUrl);
            Bitmap bitmap = downloadBitmap(params[0]);
            if (bitmap != null) {
                //图片下载完成之后缓存到lruchache中
                addBitmapToMemoryCache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }

        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoInput(true);
                con.setDoOutput(true);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
                Log.i("Sinya", "下载图片成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }

    // [-] Task

}
