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
public class DownLoadActivity extends Activity {

    private static final String downURL = "http://10.0.2.2:8080/QQJY/data/123.apk";
    public static int THREAD_COUNT = 3; //定义有3个线程来执行下载
    static int runningCount; //当前运行的线程有3个
    ProgressBar pb1, pb2, pb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down1_layout);


        pb1 = (ProgressBar) findViewById(R.id.progressBar1);
        pb2 = (ProgressBar) findViewById(R.id.progressBar2);
        pb3 = (ProgressBar) findViewById(R.id.progressBar3);
    }

    public void down(View v) {
        final String path = downURL;
        new Thread() {
            public void run() {

                try {
                    //执行多线程下载

                    //1.得到服务器的数据长度
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    int length = conn.getContentLength();
                    Log.i("Sinya", "將要下載的文件的length=" + length);

                    //2. 新建一个本地文件，与服务器的文件大小一模一样。
                    //随机访问读取类
                    Log.i("Sinya", "URL地址：" + path);
                    RandomAccessFile raf = new RandomAccessFile(new File(getFileName(path)), "rw");
                    //指定当前的这个文件长度是多少。
                    raf.setLength(length);

                    runningCount = THREAD_COUNT;
                    //3 .划分每一个线程下载的大小以及区间
                    //得到每个线程下载的大小。
                    int blockSize = length / THREAD_COUNT;

                    for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {

                        int startIndex = threadId * blockSize; //每个线程下载的开始位置
                        int endIndex = (threadId + 1) * blockSize - 1;  //得到线程的下载结束位置

                        if (threadId == THREAD_COUNT - 1) { //如果是最后一个线程
                            //它要下载到文件的末尾
                            endIndex = length - 1;
                        }

                        Log.i("Sinya", "线程" + threadId + "--它的下载区间是：" + startIndex + " ~~~~  " + endIndex);

                        //下载的时候，必须要把开始的位置以及结束的位置传给线程类。
                        new MyDownLoad(startIndex, endIndex, threadId, path).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    class MyDownLoad extends Thread {

        int startIndex, endIndex, threadId, currentPosition; //后面的变量用于记录当前的下载位置
        String path;

        public MyDownLoad(int startIndex, int endIndex, int threadId, String path) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
            this.path = path;

            currentPosition = startIndex; //当前的下载位置
        }

        @Override
        public void run() {

            try {

                //在下载的时候就应该去获取到已经保存的文件内容。
                //emulator/0/storage/laery
                File file = new File("/mnt/sdcard/" + threadId + ".position");

                //如果读取到文件，说明这段文件以前已经下载过
                if (file.exists()) {

                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    //  500
                    //获取到文件的内容然后设置给startIndex   0  --- 1000
                    int position = Integer.parseInt(br.readLine());
                    currentPosition = position;

                    Log.i("Sinya", threadId + "---以前有下载过文件，当前的下载位置是：" + startIndex);
                    fis.close();

                } else { //已经没有下载过
                    Log.i("Sinya", threadId + "---以前没有下载过文件，从头开始下载");
                }


                //1.定位资源
                URL url = new URL(path);
                //2. 打开连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //50000
                //告诉服务器，客户端想获取，从什么位置到什么位置的数据 。  bytes : 0 - 200
                conn.setRequestProperty("Range", "bytes:" + currentPosition + "-" + endIndex);

                Log.i("Sinya", "响应码：" + conn.getResponseCode());

                //定位写入的文件对象
                RandomAccessFile raf = new RandomAccessFile(getFileName(path), "rw");
                //让文件的指针跳过前面的多少个字节，从当前的位置开始写入数据
                raf.seek(currentPosition);

                //分区间的下载数据返回的状态码是206 ，而不是200
                if (conn.getResponseCode() == 206) {

                    //获取到服务器返回的输入流
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        //往文件里面开始写入数据了。
                        raf.write(buffer, 0, len);

                        // 计算已经下载了多少个长度，下载到了什么位置
                        currentPosition += len;

                        //用于保存已经下载了多少的文件长度
                        RandomAccessFile fos = new RandomAccessFile("/mnt/sdcard/" + threadId + ".position", "rws");
                        fos.write((currentPosition + "").getBytes());
                        fos.close();

                        if (threadId == 0) { // 更新第一个进度条
                            pb1.setMax(endIndex - startIndex);
                            pb1.setProgress(currentPosition - startIndex);
                        } else if (threadId == 1) { // 更新第2个进度条

                            pb2.setMax(endIndex - startIndex);
                            pb2.setProgress(currentPosition - startIndex);
                        } else if (threadId == 2) { // 更新第3个进度条

                            pb3.setMax(endIndex - startIndex);
                            pb3.setProgress(currentPosition - startIndex);
                        }

//						sleep(10);
                        Log.i("Sinya", "线程--" + threadId + "--已经下载了" + currentPosition);
                    }
                    raf.close();

                    runningCount--;
                    synchronized (MyDownLoad.class) {
                        if (runningCount <= 0) { //没有任何一个线程在运行了。
                            for (int i = 0; i < THREAD_COUNT; i++) {
                                File positionFile = new File("/mnt/sdcard/" + i + ".position");
                                positionFile.delete();
                            }
                        }
                    }

                    Log.i("Sinya", "线程--" + threadId + "--已经下载完毕");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取文件的名字
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
//		String path = "http://localhost:8080/xxx.txt";
        int index = path.lastIndexOf("/") + 1;
        Log.i("Sinya", /*"文件保存路徑：/mnt/sdcard/"+path.substring(index)*/ Environment.getExternalStorageDirectory() + path.substring(index));
        return Environment.getExternalStorageDirectory() + path.substring(index);
    }

}