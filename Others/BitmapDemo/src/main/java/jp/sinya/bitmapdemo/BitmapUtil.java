package jp.sinya.bitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * @author Koizumi Sinya
 * @date 2018/01/03. 17:53
 * @edithor
 * @date
 */
public class BitmapUtil {
    /**
     * 裁剪取样策略
     *
     * @param filePath
     * @param pixelW   真正需要显示的宽
     * @param pixelH   真正需要显示的高
     * @return
     */
    public static Bitmap ratio(String filePath, int pixelW, int pixelH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只加载图片信息，并非获取图片资源
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;//默认是这个属性：表示每个像素点要占8个字节，这个是最大的图片显示
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置成比较小的RGB565 减少内存消耗
        //预加载
        BitmapFactory.decodeFile(filePath, options);
        //获取到图片的原始 宽、高
        int originalW = options.outWidth;
        int originalH = options.outHeight;
        //采样
        options.inSampleSize = getSimpleSize(originalW, originalH, pixelW, pixelH);

        //真正加载，需要置为false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap ratio(InputStream inputStream, int pixelW, int pixelH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只加载图片信息，并非获取图片资源
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;//默认是这个属性：表示每个像素点要占8个字节，这个是最大的图片显示
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置成比较小的RGB565 减少内存消耗
        //预加载
        BitmapFactory.decodeStream(inputStream, null, options);
        //获取到图片的原始 宽、高
        int originalW = options.outWidth;
        int originalH = options.outHeight;
        //采样
        options.inSampleSize = getSimpleSize(originalW, originalH, pixelW, pixelH);

        //真正加载，需要置为false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }


    public static Bitmap ratio(byte[] bytes, int pixelW, int pixelH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只加载图片信息，并非获取图片资源
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;//默认是这个属性：表示每个像素点要占8个字节，这个是最大的图片显示
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置成比较小的RGB565 减少内存消耗
        //预加载
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        //获取到图片的原始 宽、高
        int originalW = options.outWidth;
        int originalH = options.outHeight;
        //采样
        options.inSampleSize = getSimpleSize(originalW, originalH, pixelW, pixelH);

        //真正加载，需要置为false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 通过 图片原本的宽高 和 实际需要显示的宽高，进行采样
     *
     * @param originalW
     * @param originalH
     * @param pixelW
     * @param pixelH
     * @return
     */
    private static int getSimpleSize(int originalW, int originalH, int pixelW, int pixelH) {
        int simpleSize = 1;
        //如果原始的宽度>原始的高度 并且 原始的宽度>实际需要显示的宽度，就以原始的宽度为采样值
        if (originalW > originalH && originalW > pixelW) {
            simpleSize = originalW / pixelW;

        } else if (originalH < originalW && originalH > pixelH) {
            simpleSize = originalH / pixelH;
        }

        if (simpleSize <= 0) {
            simpleSize = 1;
        }

        return simpleSize;
    }
}
