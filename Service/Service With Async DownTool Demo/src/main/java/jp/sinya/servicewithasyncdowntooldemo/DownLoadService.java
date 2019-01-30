package jp.sinya.servicewithasyncdowntooldemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

/**
 * Created by koizumisinya on 2017/02/15.
 */
public class DownLoadService extends Service {
    private DownLoadTask loadTask;

    private String downUrl;
    private DownLoadBinder binder = new DownLoadBinder();
    private NotificationCompat.Builder notifBuild;

    private DownLoadListener downloadListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("正在下载...", progress));
        }

        @Override
        public void onSuccess() {
            loadTask = null;

            //关闭前台服务通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载完成", -1));
            Toast.makeText(DownLoadService.this, "下载完成", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            loadTask = null;

            //关闭前台服务通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载失败", -1));
            Toast.makeText(DownLoadService.this, "下载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            loadTask = null;
            Toast.makeText(DownLoadService.this, "已暂停", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            loadTask = null;
            stopForeground(true);
            Toast.makeText(DownLoadService.this, "已经取消本次下载", Toast.LENGTH_SHORT).show();
        }
    };

    class DownLoadBinder extends Binder {

        public void startDownLoad(String url) {
            if (loadTask == null) {
                downUrl = url;
                loadTask = new DownLoadTask();
                loadTask.setListener(downloadListener);
                loadTask.execute(downUrl);

                startForeground(1, getNotification("正在下载...", 0));
                Toast.makeText(DownLoadService.this, "正在下载", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownLoad() {
            if (loadTask != null) {
                loadTask.pauseDownLoad();
            }
        }

        public void cancelDownLoad() {
            if (loadTask != null) {
                loadTask.cancelDownLoad();

            } else {
                if (!TextUtils.isEmpty(downUrl)) {
                    String fileName = downUrl.substring(downUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);

                    if (file.exists()) {
                        file.delete();
                    }

                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownLoadService.this, "已经取消本次下载 并删除缓存文件", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);

        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }

        return builder.build();
    }

    private Notification getNotification2(String title, int progress) {
        if (notifBuild == null) {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            notifBuild = new NotificationCompat.Builder(this);
            notifBuild.setSmallIcon(R.mipmap.ic_launcher);
            notifBuild.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            notifBuild.setContentIntent(pendingIntent);
            notifBuild.setContentTitle(title);
            notifBuild.build();
        }

        if (progress > 0) {
            notifBuild.setContentText(progress + "%");
            notifBuild.setProgress(100, progress, false);
        }

        return notifBuild.build();
    }

    private void setNotifNull() {
        notifBuild = null;
    }
}
