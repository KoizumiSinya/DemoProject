/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 bboyfeiyu@gmail.com ( Mr.Simple )
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.simple.imageloader.org.simple.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.simple.imageloader.org.simple.imageloader.cache.BitmapCache;
import com.simple.imageloader.org.simple.imageloader.config.DisplayConfig;
import com.simple.imageloader.org.simple.imageloader.core.SimpleImageLoader;
import com.simple.imageloader.org.simple.imageloader.request.BitmapRequest;

import jp.sinya.utils.LogUtils;


/**
 * @author mrsimple
 */
public abstract class AbsLoader implements Loader {

    /**
     * 图片缓存
     */
    private static BitmapCache mCache = SimpleImageLoader.getInstance().getConfig().bitmapCache;

    @Override
    public final void loadImage(BitmapRequest request) {

        //先从缓存中判断是否有 bitmap对象
        Bitmap resultBitmap = mCache.get(request);
        LogUtils.Sinya("判断是否有缓存:" + (resultBitmap != null) + ", uri = " + request.imageUri);

        if (resultBitmap == null) {
            //显示加载中的图片
            showLoading(request);
            //网络请求获取bitmap
            resultBitmap = onLoadImage(request);
            //将获取的网络请求结果保存到缓存中
            cacheBitmap(request, resultBitmap);
        } else {
            request.justCacheInMemory = true;
        }

        deliveryToUIThread(request, resultBitmap);
    }

    /**
     * @param result
     * @return
     */
    protected abstract Bitmap onLoadImage(BitmapRequest result);

    /**
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        // 缓存新的图片
        if (bitmap != null && mCache != null) {
            synchronized (mCache) {
                mCache.put(request, bitmap);
            }
        }
    }

    /**
     * 显示加载中的视图,注意这里也要判断imageview的tag与image uri的相等性,否则逆序加载时出现问题
     *
     * @param request
     */
    protected void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid() && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {

                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }
    }

    /**
     * 将结果投递到UI,更新ImageView
     *
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            LogUtils.Sinya("ImageView控件为null");
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    /**
     * 更新ImageView
     *
     * @param request
     * @param bitmap
     */
    private void updateImageView(BitmapRequest request, Bitmap bitmap) {

        final ImageView imageView = request.getImageView();
        final String uri = request.imageUri;

        if (bitmap != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(bitmap);
        }

        // 加载失败
        if (bitmap == null && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.failedResId);
        }

        // 回调接口
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, uri);
        }
    }

    /**
     * 判断是否设置了加载中的图片资源
     *
     * @param displayConfig
     * @return
     */
    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    /**
     * 判断是否设置了加载失败的图片资源
     *
     * @param displayConfig
     * @return
     */
    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }

}
