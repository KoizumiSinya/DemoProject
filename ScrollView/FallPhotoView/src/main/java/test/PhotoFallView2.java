package test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.os.AsyncTask;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Handler;
import android.widget.Toast;

import studio.sinya.jp.demo_fallphotoview.R;
import studio.sinya.jp.demo_fallphotoview.bean.Images;
import studio.sinya.jp.demo_fallphotoview.widget.ImageDetailsActivity;

/**
 * Created by SinyaKoizumi on 2015/10/20.
 */
public class PhotoFallView2 extends ScrollView implements View.OnTouchListener {

    /**
     * 每页加载多少张图片
     */
    private static final int PAGE_SIZE = 15;

    /**
     * 当前加载到第几页
     */
    private int page;

    /**
     * 每一列的宽度
     */
    private int columnWidth;

    /**
     * 当前第一列的高度
     */
    private int firstColumnHeight;

    /**
     * 当前第二列的高度
     */
    private int secondColumnHeight;

    /**
     * 当前第三列的高度
     */
    private int thirdColumnHeight;

    /**
     * 是否加载过layout。这里的onLayout的初始化只需要加载一次
     */
    private boolean loadOnce;

    /**
     * 图片的管理者
     */
    private ImageLoader2 imageLoader2;

    /**
     * 第一列的布局
     */
    private LinearLayout firstColumn;

    /**
     * 第二列的布局
     */
    private LinearLayout secondColumn;

    /**
     * 第三列的布局
     */
    private LinearLayout thirdColumn;

    /**
     * 记录所有正在下载的或者等待的线程任务
     */
    private static Set<LoadImageTask> taskCollection;

    /**
     * PhotoFallView2 下的外层子布局
     */
    private static View scrollChildLayout;

    /**
     * PhotoFallView2自身的高度 <test.PhotoFallView2>...</test.PhotoFallView2>
     */
    private static int scrollLayoutHeight;

    /**
     * 记录垂直方向滚动的距离
     */
    private static int lastScrollY = -1;

    /**
     * 记录所有界面上的图片，方便随时控制对图片的释放
     */
    private List<ImageView> imageList = new ArrayList<>();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            PhotoFallView2 fallView = (PhotoFallView2) msg.obj;

            //获取滚动后的位置
            int scrollY = fallView.getScrollY();

            //如果滚动的位置和上次相同，表示停止滚动
            if (scrollY == lastScrollY) {
                //当滚动到最底部的时候，并且当前没有正在下载的任务时，开始加载下一页的图片
                if (scrollLayoutHeight + scrollY >= scrollChildLayout.getHeight() && taskCollection.isEmpty()) {
                    fallView.loadMoreImages();
                }

                fallView.checkVisibility();

            } else {
                lastScrollY = scrollY;
                Message newMsg = new Message();
                newMsg.obj = fallView;
                handler.sendMessageDelayed(newMsg, 5);
            }
            return false;
        }
    });

    public PhotoFallView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageLoader2 = ImageLoader2.getInstance();
        taskCollection = new HashSet<>();
        setOnTouchListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !loadOnce) {
            //把scrollLayoutHeight 设置为当前手机屏幕的高度
            scrollLayoutHeight = getHeight();
            scrollChildLayout = getChildAt(0);

            firstColumn = (LinearLayout) findViewById(R.id.first_column);
            secondColumn = (LinearLayout) findViewById(R.id.second_column);
            thirdColumn = (LinearLayout) findViewById(R.id.third_column);

            columnWidth = firstColumn.getWidth();

            //首次进入的时候会调用一次这个方法加载第一页
            loadMoreImages();
        }
    }


    /**
     * 监听触摸事件
     * ①如果手指滑动后离开屏幕，判断移动的距离
     * ②根据距离计算需要加载的图片
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Message message = new Message();
            message.obj = this;
            handler.sendMessageDelayed(message, 5);
        }
        return false;
    }

    // [+] Methods

    /**
     * 加载下一页的图片。每张图片都会开启一个各自的异步线程去下载
     */
    private void loadMoreImages() {
        if (hasSDCard()) {
            int startIndex = page * PAGE_SIZE;
            int endIndex = page * PAGE_SIZE + PAGE_SIZE;

            //如果起始下载的图片数量 小于 服务器图片总数
            if (startIndex < Images.imageUrls.length) {
                Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT).show();

                //如果结束位置的图片数量 大于 服务器图片总数
                if (endIndex > Images.imageUrls.length) {
                    endIndex = Images.imageUrls.length;
                }

                //使用for循环给每个图片开启一个task
                Log.i("Sinya", "startIndex = " + startIndex + "; endIndex = " + endIndex);
                for (int i = startIndex; i < endIndex; i++) {
                    LoadImageTask task = new LoadImageTask();
                    taskCollection.add(task);
                    Log.i("Sinya", "开启一个加载图片的线程任务，目标地址是:" + Images.imageUrls[i]);
                    task.execute(Images.imageUrls[i]);
                }

                //加载完一页的所有图片的task任务之后，page+1
                page++;
            } else {
                Toast.makeText(getContext(), "已经没有更多图片", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 遍历imagesList中的所有图片，对图片的可见性进行检查
     * 如果图片已经离开屏幕，则可以让它替换成一张空图
     */
    public void checkVisibility() {
        for (int i = 0; i < imageList.size(); i++) {
            ImageView image = imageList.get(i);
            int borderTop = (int) image.getTag(R.string.border_top);
            int borderButtom = (int) image.getTag(R.string.border_bottom);

            if (borderButtom > getScrollY() && borderTop < getScrollY() + scrollLayoutHeight) {
                String imageUrl = (String) image.getTag(R.string.image_url);
                Bitmap bitmap = imageLoader2.getBitmapFromMemoryCache(imageUrl);

                if (bitmap != null) {
                    Log.i("Sinya", "图片从缓存中提取：" + imageUrl);
                    image.setImageBitmap(bitmap);
                } else {
                    Log.i("Sinya", "缓存中已经没有资源，但是已知是哪个ImageView需要加载。传入该控件再次开启线程加载图片" + imageUrl);
                    LoadImageTask task = new LoadImageTask(image);
                    task.execute(imageUrl);
                }
            } else {
                //用一张.9的图片填充
                Log.i("Sinya", "图片" + (String) image.getTag(R.string.image_url) + "已不显示在界面，被暂时隐藏");
                image.setImageResource(R.drawable.empty_photo);
            }
        }
    }
    // [-] Methods
    // [+] Task

    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;
        private ImageView tempImage;

        public LoadImageTask() {

        }

        public LoadImageTask(ImageView imageView) {
            this.tempImage = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = imageLoader2.getBitmapFromMemoryCache(imageUrl);
            if (bitmap == null) {
                bitmap = loadImage(imageUrl);
            }

            Log.i("Sinya", "图片加载从LruCache缓存中获取" + imageUrl);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                //将bitmap图片的宽度除以列宽，获取一个比例
                double ratio = bitmap.getWidth() / (columnWidth * 1.0);
                //将这个比例设置到bitmap的高度上
                              int scaledHeight = (int) (bitmap.getHeight() / ratio);

                //把图片按照一定规则分别显示在三个列中。params参数的宽是列宽，高是更改比例后的高
                addImage(bitmap, columnWidth, scaledHeight);
            }

            taskCollection.remove(this);
        }

        /**
         * 第一级缓存
         * ①如果SD卡指定的目录下有这个图片，则直接从SD卡中获取
         * ②如果没有，则通过图片URL获取。获取之后并保存在缓存中
         *
         * @param imageUrl
         * @return
         */
        private Bitmap loadImage(String imageUrl) {
            File imageFile = new File(getImagePath(imageUrl));

            //如果文件不存在
            if (!imageFile.exists()) {
                downLoadImage(imageUrl);
            } else {
                Log.i("Sinya", "LruCache缓存中没有，但文件已下载过，从本地SD卡中获取目标图片：" + getImagePath(imageUrl));
            }

            if (imageUrl != null) {
                Bitmap bitmap = ImageLoader2.decodeSampledBitmapFromResource(imageFile.getPath(), columnWidth);
                if (bitmap != null) {
                    imageLoader2.addBitmapToMemoryCache(imageUrl, bitmap);
                    return bitmap;
                }
            }
            return null;
        }

        /**
         * 将图片下载到SD卡中
         *
         * @param imageUrl
         */
        private void downLoadImage(String imageUrl) {
            HttpURLConnection con = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File downImageFile = null;

            try {

                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5000);
                con.setReadTimeout(10000);
                con.setDoInput(true);
                con.setDoOutput(true);

                bis = new BufferedInputStream(con.getInputStream());
                downImageFile = new File(getImagePath(imageUrl));

                fos = new FileOutputStream(downImageFile);
                bos = new BufferedOutputStream(fos);

                byte[] buf = new byte[1024];
                int len;

                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) bis.close();
                    if (bos != null) bos.close();
                    if (fos != null) fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (downImageFile != null) {
                Log.i("Sinya", "LruCache和SD中没有该资源，请求网络连接获取图片。目标连接是：" + imageUrl + "; 已经下载到: " + downImageFile.getPath());
                //传入列的宽度作为目标宽度，对源图片进行宽高比的调整；并且传入该源图片路径作为覆盖修改
                Bitmap bitmap = ImageLoader2.decodeSampledBitmapFromResource(downImageFile.getPath(), columnWidth);
                if (bitmap != null) {
                    //保存到缓存中
                    imageLoader2.addBitmapToMemoryCache(imageUrl, bitmap);
                }
            }
        }

        /**
         * 把获取到的图片加载到ScrollView中
         *
         * @param bitmap
         * @param imageWidth
         * @param imageHeight
         */
        private void addImage(Bitmap bitmap, int imageWidth, int imageHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageHeight);
            if (tempImage != null) {
                tempImage.setImageBitmap(bitmap);
            } else {
                ImageView newImage = new ImageView(getContext());
                newImage.setLayoutParams(params);
                newImage.setImageBitmap(bitmap);
                newImage.setScaleType(ImageView.ScaleType.FIT_XY);
                newImage.setPadding(5, 5, 5, 5);
                //打上印记
                newImage.setTag(R.string.image_url, imageUrl);

                //绑上点击事件
                newImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                        intent.putExtra("image_path", getImagePath(imageUrl));
                        getContext().startActivity(intent);
                    }
                });

                findColumnToAdd(newImage, imageHeight).addView(newImage);

                //把已经加载过的图片存放到集合中
                imageList.add(newImage);
            }
        }

        /**
         * 找到当前高度最小的一列进行添加新的图片
         *
         * @param imageView
         * @param imageHeight
         * @return
         */
        private LinearLayout findColumnToAdd(ImageView imageView, int imageHeight) {

            if (firstColumnHeight <= secondColumnHeight) {

                //第一列的高度最小的情况（1<=2 && 1<=3）
                if (firstColumnHeight <= thirdColumnHeight) {
                    //给这个将要加载的图片打上印记:自身高度在第一列中的 顶部坐标
                    imageView.setTag(R.string.border_top, firstColumnHeight);
                    //第一列的高度 = 原高度+图片高度
                    firstColumnHeight += imageHeight;
                    //给这个将要加载的图片打上印记:自身高度在第一列中的 底部坐标
                    imageView.setTag(R.string.border_bottom, firstColumnHeight);
                    return firstColumn;
                }

                //第三列的高度最小的情况（1 <= 2 && 1 >= 3）
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom, thirdColumnHeight);
                return thirdColumn;

            } else {

                //第二列的高度最小的情况（1 > 2 && 2 <= 3）
                if (secondColumnHeight <= thirdColumnHeight) {
                    imageView.setTag(R.string.border_top, secondColumnHeight);
                    secondColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom, secondColumnHeight);
                    return secondColumn;
                }

                //第三列的高度最小的情况（1 > 2 && 2> 3）
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom, thirdColumnHeight);
                return thirdColumn;
            }
        }
    }

    // [-] Task
    // [+] SDCard

    /**
     * 获取图片的本地存储路径。
     *
     * @param imageUrl 图片的URL地址。
     * @return 图片的本地存储路径。
     */
    private String getImagePath(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        String imageName = imageUrl.substring(lastSlashIndex + 1);
        String imageDir = Environment.getExternalStorageDirectory().getPath() + "/PhotoWallFalls/";
        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = imageDir + imageName;
        return imagePath;
    }

    /**
     * 判断是否存在SD卡路径
     *
     * @return
     */
    private boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    // [-] SDCard
}
