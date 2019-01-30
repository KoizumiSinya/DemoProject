package studio.sinya.jp.demo_downloadfile.download;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import studio.sinya.jp.demo_downloadfile.R;

/**
 * Created by SinyaKoizumi on 2015/10/21.
 */
public class Download1Activity extends Activity {

    /**
     * 下载地址（使用Tomcat部署）
     */
    private static final String downURL = "http://10.0.2.2:8080/QQJY/data/123.rar";

    /**
     * 规定一个下载任务分由几个线程去操作
     */
    private static int THREAD_COUNT = 3;

    private static int runningCount;

    /**
     * 三个进度条 直观的给用户显示下载进度
     */
    private ProgressBar pb1, pb2, pb3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down1_layout);

        pb1 = (ProgressBar) findViewById(R.id.progressBar1);
        pb2 = (ProgressBar) findViewById(R.id.progressBar2);
        pb3 = (ProgressBar) findViewById(R.id.progressBar3);
    }

    public void down(View v) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //访问服务器 先获取即将要下载的这个文件长度大小是多少
                    int len = con.getContentLength();
                    Log.i("Sinya", "文件大小：" + len);

                    //在SD卡路径下创建一个文件，预设大小（实际就是用null字符将文件设置成一定大小）
                    RandomAccessFile accessFile = new RandomAccessFile(new File(getFilePath(downURL)), "rws");
                    accessFile.setLength(len);

                    runningCount = THREAD_COUNT;
                    int blockSize = len / THREAD_COUNT;

                    //通过for循环 对这一个需要下载的文件，开辟3个线程去分别下载各自的部分（全部下载完之后会组合成一个完整的文件）
                    for (int threadID = 0; threadID < THREAD_COUNT; threadID++) {

                        //设置每个线程下载的起始 和 截止位置的大小
                        int startIndex = threadID * blockSize;
                        int endIndex = (threadID + 1) * blockSize - 1;

                        //如果是最后一个，将剩下的大小都给这个线程
                        if (threadID == THREAD_COUNT - 1) {
                            endIndex = len - 1;
                        }

                        Log.i("Sinya", "开启线程" + threadID + " - 下载的起始位置: " + startIndex + "; 下载的结束位置: " + endIndex);

                        new DownLoad1(startIndex, endIndex, threadID, downURL).start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }.start();
    }


    /**
     * 传入一个url地址 截取出来文件名，并重新在前面赋予一个SD卡路径
     * @param path 图片的URL地址。
     * @return 图片的本地存储路径。
     */
    private String getFilePath(String path) {
        int lastSlashIndex = path.lastIndexOf("/");
        String imageName = path.substring(lastSlashIndex + 1);
        String imageDir = Environment.getExternalStorageDirectory().getPath() + "/MyDownLoad/";

        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String newPath = imageDir + imageName;
        Log.i("Sinya", "返回path: " + newPath);
        return newPath;
    }
    // [+] Thread下载

    class DownLoad1 extends Thread {
        int startIndex, endIndex, threadID, currentPosition;
        String path;

        public DownLoad1(int startIndex, int endIndex, int threadID, String path) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadID = threadID;
            this.path = path;

            currentPosition = startIndex;
        }

        @Override
        public void run() {
            try {
                File file = new File(getFilePath("/" + threadID + ".cache"));

                //如果能读取到文件，说明这一部分文件之前已经下载过
                if (file.exists()) {
                    //取出文件缓存中之前写进去的下载进度值
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                    int position = Integer.parseInt(br.readLine());
                    currentPosition = position;

                    Log.i("Sinya", "线程" + threadID + "曾下载过该段文件。当前应下载的起始位置是: " + startIndex);
                    fis.close();
                } else {
                    Log.i("Sinya", "线程" + threadID + " 未下载过该段文件。从头开始下载");
                }

                URL url = new URL(path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //连接服务器 告知需要下载的文件需要从哪里开始到哪里截止
                con.setRequestProperty("Range", "bytes:" + currentPosition + "-" + endIndex);

                RandomAccessFile accessFile = new RandomAccessFile(getFilePath(path), "rws");
                //拨动指针 从哪里开始继续下载
                accessFile.seek(currentPosition);
                //分区下载的成功响应码是206
                if (con.getResponseCode() == 206) {
                    InputStream is = con.getInputStream();
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        accessFile.write(buf, 0, len);

                        //计算每次下载多少长度（下载到哪个位置）
                        currentPosition += len;
                        //保存到本地作为缓存 作为需要断点续传的依据
                        RandomAccessFile fos = new RandomAccessFile(getFilePath("/" + threadID + ".cache"), "rws");
                        fos.write((currentPosition + "").getBytes());
                        fos.close();

                        if (threadID == 0) {
                            pb1.setMax(endIndex - startIndex);
                            pb1.setProgress(currentPosition - startIndex);
                        } else if (threadID == 1) {
                            pb2.setMax(endIndex - startIndex);
                            pb2.setProgress(currentPosition - startIndex);
                        } else if (threadID == 2) {
                            pb3.setMax(endIndex - startIndex);
                            pb3.setProgress(currentPosition - startIndex);
                        }

                        Log.i("Sinya", "线程" + threadID + " - 已经下载了" + currentPosition + "的大小");
                    }
                    accessFile.close();

                    runningCount--;

                    synchronized (DownLoad1.class) {
                        if (runningCount <= 0) {
                            for (int i = 0; i < THREAD_COUNT; i++) {
                                File positionCache = new File(getFilePath("/" + i + ".cache"));
                                positionCache.delete();
                                Log.i("Sinya", "清空缓存文件" + getFilePath("/" + i + ".cache"));
                            }
                        }
                    }

                    Log.i("Sinya", "线程" + threadID + "下载完毕");

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    // [-] Thread下载
}
