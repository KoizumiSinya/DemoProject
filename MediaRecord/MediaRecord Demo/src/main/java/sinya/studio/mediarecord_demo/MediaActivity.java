package sinya.studio.mediarecord_demo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends ListActivity {

    private Context context;

    private Button mAudioStartBtn;
    private Button mAudioStopBtn;
    private File mRecAudioFile;        // 录制的音频文件
    private File mRecAudioPath;        // 录制的音频文件路徑
    private MediaRecorder mMediaRecorder;// MediaRecorder对象
    private List<String> mMusicList = new ArrayList<String>();// 录音文件列表
    private String strTempFile = "recaudio_";// 零时文件的前缀

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        context = this;
        
        mAudioStartBtn = (Button) findViewById(R.id.mediarecorder1_AudioStartBtn);
        mAudioStopBtn = (Button) findViewById(R.id.mediarecorder1_AudioStopBtn);

        /*按钮状态*/
        mAudioStartBtn.setEnabled(true);
        mAudioStopBtn.setEnabled(false);

        /* 检测是否存在SD卡 */
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            mRecAudioPath = Environment.getExternalStorageDirectory();// 得到SD卡得路径
            musicList();// 更新所有录音文件到List中
        } else {
            Toast.makeText(context, "没有SD卡", Toast.LENGTH_LONG).show();
        }

        /* 开始按钮事件监听 */
        mAudioStartBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    /* ①Initial：实例化MediaRecorder对象 */
                    mMediaRecorder = new MediaRecorder();
                    /* ②setAudioSource/setVedioSource*/
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
                    /* ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
                     * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
                     * */
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    /* ②设置输出文件的路径 */
                    try {
                        mRecAudioFile = File.createTempFile(strTempFile, ".amr", mRecAudioPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
                    /* ③准备 */
                    mMediaRecorder.prepare();
                    /* ④开始 */
                    mMediaRecorder.start();
                    /*按钮状态*/
                    mAudioStartBtn.setEnabled(false);
                    mAudioStopBtn.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /* 停止按钮事件监听 */
        mAudioStopBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mRecAudioFile != null) {
                    /* ⑤停止录音 */
                    mMediaRecorder.stop();
                    /* 将录音文件添加到List中 */
                    mMusicList.add(mRecAudioFile.getName());
                    ArrayAdapter<String> musicList = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mMusicList);
                    setListAdapter(musicList);
                    /* ⑥释放MediaRecorder */
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    /* 按钮状态 */
                    mAudioStartBtn.setEnabled(true);
                    mAudioStopBtn.setEnabled(false);
                }
            }
        });
    }

    /* 播放录音文件 */
    private void playMusic(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        /* 设置文件类型 */
        intent.setDataAndType(Uri.fromFile(file), "audio");
        startActivity(intent);
    }

    @Override
    /* 当我们点击列表时，播放被点击的音乐 */ protected void onListItemClick(ListView l, View v, int position, long id) {
        /* 得到被点击的文件 */
        File playfile = new File(mRecAudioPath.getAbsolutePath() + File.separator + mMusicList.get(position));
        /* 播放 */
        playMusic(playfile);
    }

    /* 播放列表 */
    public void musicList() {
        // 取得指定位置的文件设置显示到播放列表
        File home = mRecAudioPath;
        if (home.listFiles(new MusicFilter()).length > 0) {
            for (File file : home.listFiles(new MusicFilter())) {
                mMusicList.add(file.getName());
            }
            ArrayAdapter<String> musicList = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mMusicList);
            setListAdapter(musicList);
        }
    }
}

/* 过滤文件类型 */
class MusicFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".amr"));
    }
}