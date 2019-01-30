package com.sinya.draglauncher.luancherwidget.bean;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;

public class ItemInfo {

    /**
     * Item是图标
     */
    public static final int TYPE_APP = 1;

    /**
     * Item是文件夹
     */
    public static final int TYPE_FOLDER = 2;

    /**
     * Item 的id
     */
    private String id;

    /**
     * Item的 bitmap
     */
    protected Bitmap icon;

    /**
     * Item的名字
     */
    protected String title;

    /**
     * Item的text
     */
    protected String text;

    /**
     * Item 的order
     */
    protected int order;

    /**
     * Item 的类型（1表示图标， 2表示文件夹）
     */
    protected int type;

    /**
     * 图片缓存的软引用 将bitmap的对象存放到集合中
     */
    protected SoftReference<Bitmap> iconRef;

    public Bitmap notification;
    public Bitmap buffer;

    /**
     * 是否是编辑状态（右上角弹出删除按钮）
     */
    private boolean jiggle = false;

    /**
     * 是否测量过Title
     */
    protected boolean titleMeasured = false;

    /**
     * Item 的地址连接URL
     */
    private String imgUrl;

    /**
     * 清空Item的bitmap资源
     */
    public void clearIcon() {
        this.icon = null;
        this.iconRef = null;
    }

    public void destroy() {
        this.icon = null;
        this.notification = null;
        this.buffer = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
        iconRef = new SoftReference<Bitmap>(icon);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.text = title;
        titleMeasured = false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SoftReference<Bitmap> getIconRef() {
        return iconRef;
    }

    public void setIconRef(SoftReference<Bitmap> iconRef) {
        this.iconRef = iconRef;
    }

    public void jiggle() {
        setJiggle(true);
    }

    public void unJiggle() {
        setJiggle(false);
    }

    public boolean isJiggle() {
        return jiggle;
    }

    public void setJiggle(boolean jiggle) {
        this.jiggle = jiggle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
