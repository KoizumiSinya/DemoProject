package test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * 单例的图片管理器
 * Created by SinyaKoizumi on 2015/10/20.
 */
public class ImageLoader2 {
    private static LruCache<String, Bitmap> memoryCache;
    private static ImageLoader2 imageLoader2;

    /**
     * 私有化的构造函数
     */
    private ImageLoader2() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static ImageLoader2 getInstance() {
        if (imageLoader2 == null) {
            imageLoader2 = new ImageLoader2();
        }
        return imageLoader2;
    }

    /**
     * 往缓存器中添加图片缓存bitmap
     *
     * @param key 该图片的url下载地址
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    /**
     * 从缓存器中获取bitmap
     * @param key 该图片的url下载地址
     * @return
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    /**
     * 丈量图片尺寸
     *
     * @param options
     * @param reqWidth
     * @return 返回一个宽高比
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        //源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;

        //如果源宽度 大于 目标宽度
        if (width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 返回一个已经修改宽高比的图片
     *
     * @param pathName
     * @param reqWidth
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //获取路径下的图片
        BitmapFactory.decodeFile(pathName, options);

        //传入options和目标宽度
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(pathName, options);
    }
}
