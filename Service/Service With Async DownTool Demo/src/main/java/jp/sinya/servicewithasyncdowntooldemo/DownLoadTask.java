package jp.sinya.servicewithasyncdowntooldemo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by koizumisinya on 2017/02/15.
 */

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownLoadListener listener;

    private boolean isCanceled;
    private boolean isPaused;
    private int lastProgress;

    public void setListener(DownLoadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        RandomAccessFile saveFile = null;
        File file = null;

        try {
            long downLoadedFileLength = 0;
            String downUrl = params[0];
            String fileName = downUrl.substring(downUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            Log.i("Sinya", "directory path = " + directory);

            file = new File(directory + fileName);
            if (file.exists()) {
                downLoadedFileLength = file.length();
            }

            //获取一次服务器上的源文件长度
            long contentLength = getContentLength(downUrl);

            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downLoadedFileLength) {
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()//
                    .addHeader("RANGE", "bytes=" + downLoadedFileLength + "-")//断点下载 指定从哪个位置开始下载
                    .url(downUrl)//
                    .build();

            Response response = client.newCall(request).execute();

            if (response != null) {
                inputStream = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                //跳过已经下载的字节长度
                saveFile.seek(downLoadedFileLength);

                byte[] b = new byte[1024];
                int total = 0;
                int length;

                while ((length = inputStream.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += length;
                        saveFile.write(b, 0, length);

                        int loadedProgress = (int) ((total + downLoadedFileLength) * 100 / contentLength);
                        publishProgress(loadedProgress);
                    }
                }

                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return TYPE_FAILED;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            if (listener != null) {
                listener.onProgress(progress);
                lastProgress = progress;
            }
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_CANCELED:
                if (listener != null) {
                    listener.onCanceled();
                }
                break;
            case TYPE_SUCCESS:
                if (listener != null) {
                    listener.onSuccess();
                }
                break;
            case TYPE_PAUSED:
                if (listener != null) {
                    listener.onPaused();
                }
                break;
            case TYPE_FAILED:
                if (listener != null) {
                    listener.onFailed();
                }
                break;
            default:
                break;
        }
    }

    public void pauseDownLoad() {
        isPaused = true;
    }

    public void cancelDownLoad() {
        isCanceled = true;
    }

    private long getContentLength(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.body().close();
                Log.i("Sinya", "源文件长度 = " + contentLength);
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
