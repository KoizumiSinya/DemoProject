package jp.sinya.videodemo1;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private static final String url = "rtmp://192.168.1.6:1935/live/stream";
    private VideoView videoView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = VideoActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.activity_video_view);
        //必须写这个，初始化加载库文件
        Vitamio.initialize(this);

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }

        play2();
    }

    public void onPause(View view) {

    }

    private void play1() {
        videoView.setVideoURI(Uri.parse(url));
        videoView.setMediaController(new MediaController(this));
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //videoView.start();
            }
        });

        //缓冲监听
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i("Sinya", "onBufferingUpdate: " + percent);
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (count > 5) {
                    new AlertDialog.Builder(VideoActivity.this) //
                            .setMessage("视频出错") //
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    VideoActivity.this.finish();
                                }
                            })//
                            .setCancelable(false)//
                            .show();
                } else {
                    videoView.stopPlayback();
                    videoView.setVideoURI(Uri.parse(url));
                }
                count++;
                return false;
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        Log.i("Sinya", "开始加载: ");
                        break;

                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        Log.i("Sinya", "开始错误: ");
                        break;

                    //音频先于视频出来，视频还在解码中
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        break;

                    //视频边下边播
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void play2() {
        videoView.setVideoPath(url);
        //mVideoView.setVideoURI(Uri.parse(path), options);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }
}
