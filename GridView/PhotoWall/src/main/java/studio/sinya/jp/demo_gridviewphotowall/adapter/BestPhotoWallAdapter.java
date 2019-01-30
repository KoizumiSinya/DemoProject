package studio.sinya.jp.demo_gridviewphotowall.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import libcore.io.DiskLruCache;
import studio.sinya.jp.demo_gridviewphotowall.R;


/**
 * Created by Administrator on 2015/10/25.
 */
public class BestPhotoWallAdapter extends ArrayAdapter<String> {

    /**
     * 记录所有正在下载或者等待的任务
     */
    private Set<BitmapTask> taskCollection;

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mMemoryCache;

    private DiskLruCache mDiskLruCache;

    /**
     * Grid实例
     */
    private GridView mPhotoWall;

    /**
     * 记录每一个Item的高度
     */
    private int mItemHeight = 0;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public BestPhotoWallAdapter(Context context, int resource, List<String> objects, GridView photoWall) {
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

        try {
            File cacheDir = getDiskCacheDir(context, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }

            //创建DiskLruCache实例对象 初始化缓存数据
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
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

        final ImageView imageview = (ImageView) view.findViewById(R.id.photo);
        if (imageview.getLayoutParams().height != mItemHeight) {
            imageview.getLayoutParams().height = mItemHeight;
        }

        //给Imageview设置一个tag 保证异步加载的图片不会乱序
        imageview.setTag(url);
        imageview.setImageResource(R.mipmap.null_photo);
        loadBitmaps(imageview, url);

        return view;
    }

    public void loadBitmaps(ImageView imageView, String imageUrl) {
        try {
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);

            if (bitmap == null) {
                BitmapTask task = new BitmapTask();
                taskCollection.add(task);
                task.execute(imageUrl);
            }else {
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
    /**
     * 获取当前应用程序的版本号
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return 1;
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            cacheKey = String.valueOf(key.hashCode());
        } finally {
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    // [+] Task

    class BitmapTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapshot = null;

            try {
                final String key = hashKeyForDisk(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }


    }

    // [-] Task
}
