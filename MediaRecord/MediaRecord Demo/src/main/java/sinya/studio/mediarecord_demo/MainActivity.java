package sinya.studio.mediarecord_demo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Context context;

    //语音文件保存路径
    private String FileName = null;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";

    }

    private void startVoice() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(FileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
        }
        mRecorder.start();
    }

    private void stopVoice() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void startPlay() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(FileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
        }
    }

    private void stopPlay() {
        mPlayer.release();
        mPlayer = null;
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_start_voice:
                startVoice();
                break;

            case R.id.btn_stop_voice:
                stopVoice();
                break;

            case R.id.btn_start:
                startPlay();
                break;

            case R.id.btn_stop:
                stopPlay();
                break;

            case R.id.btn_audio:
                Intent intent = new Intent(context, AudioActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_media:
                Intent intent2 = new Intent(context, MediaActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
