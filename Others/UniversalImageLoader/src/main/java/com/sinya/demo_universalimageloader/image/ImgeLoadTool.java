package com.sinya.demo_universalimageloader.image;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sinya.demo_universalimageloader.R;

/**
 * Created by Administrator on 2015/12/6.
 */
public class ImgeLoadTool {

    private static DisplayImageOptions options;

    public static void getImageLoaderOption() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()//
            .showStubImage(R.mipmap.edit_recording_area)//设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.inter_upcoming_close_checked)//设置图片URL或者是错误时 显示的图片
            .showImageOnFail(R.mipmap.inter_upcoming_close_checked)//设置图片在加载或者解码错误时 显示的图片
            .cacheInMemory(true)//设置下载的图片是否存放在内存中
            .cacheOnDisk(true)//设置下载的图片是否存在SD卡中
            .displayer(new RoundedBitmapDisplayer(360))//把图片设置成圆角
            .imageScaleType(ImageScaleType.EXACTLY)//设置图片配置
            .build();
        }
    }

}
