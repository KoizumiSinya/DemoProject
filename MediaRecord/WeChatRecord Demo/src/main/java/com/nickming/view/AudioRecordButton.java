package com.nickming.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.weixin_record.R;
import com.nickming.view.AudioManager.AudioStageListener;

/**
 * 长按触摸录音的按钮
 * 1\内置了录音管理类 和 录音播放
 * 2\并用对话框的
 */
public class AudioRecordButton extends Button implements AudioStageListener {

    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGE = 0X111;
    private static final int MSG_DIALOG_DIMISS = 0X112;

    /**
     * 正常状态下(未触摸)
     */
    private static final int STATE_NORMAL = 1;
    /**
     * 触摸状态下(正在录音)
     */
    private static final int STATE_RECORDING = 2;
    /**
     * 向上滑动(将要取消录音)
     */
    private static final int STATE_WANT_TO_CANCEL = 3;

    /**
     * 向上滑动阀值(达到多少)
     */
    private static final int DISTANCE_Y_CANCEL = 50;

    /**
     * 当前状态
     */
    private int mCurrentState = STATE_NORMAL;
    /**
     * 是否已经开始录音
     */
    private boolean isRecording = false;

    /**
     * 对话框管理器
     */
    private DialogManager mDialogManager;

    /**
     * 录音管理器
     */
    private AudioManager mAudioManager;

    /**
     * 录音时长
     */
    private float mTime = 0;

    /**
     * 是否触发了onlongclick，准备好了
     */
    private boolean mReady;

    // [+] Handler

    private Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    // 显示应该是在audio end prepare之后回调
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    new Thread(mGetVoiceLevelRunnable).start();

                    // 需要开启一个线程来变换音量
                    break;
                case MSG_VOICE_CHANGE:
                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));

                    break;
                case MSG_DIALOG_DIMISS:

                    break;

            }
        }
    };

    // [-] Handler

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDialogManager = new DialogManager(getContext());

        //TODO 这里可以增加 判断储存卡是否存在的判断
        String dir = Environment.getExternalStorageDirectory() + "/nickming_recorder_audios";

        mAudioManager = AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStageListener(this);

        setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManager.prepareAudio();
                return false;
            }
        });
    }

    // [+] Override

    @Override
    public void wellPrepared() {
        // 在这里面发送一个handler的消息
        mhandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    /**
     * 直接复写这个监听函数
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;

            case MotionEvent.ACTION_MOVE:

                if (isRecording) {
                    // 根据x，y来判断用户是否想要取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                // 首先判断是否有触发onlongclick事件，没有的话直接返回reset
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                // 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
                if (!isRecording || mTime < 0.6f) {
                    mDialogManager.tooShort();
                    mAudioManager.cancel();
                    mhandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);// 持续1.3s
                } else if (mCurrentState == STATE_RECORDING) {//正常录制结束

                    mDialogManager.dimissDialog();

                    mAudioManager.release();// release释放一个mediarecorder

                    if (mListener != null) {// 并且callbackActivity，保存录音
                        mListener.onFinished(mTime, mAudioManager.getCurrentFilePath());
                    }

                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
                    // cancel
                    mAudioManager.cancel();
                    mDialogManager.dimissDialog();
                }

                reset();// 恢复标志位
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }

    // [-] Override

    /**
     * 回复标志位以及状态
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
        mReady = false;
        mTime = 0;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {// 判断是否在左边，右边，上边，下边
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }

        return false;
    }

    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.button_recordnormal);
                    setText(R.string.normal);

                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.button_recording);
                    setText(R.string.recording);
                    if (isRecording) {
                        mDialogManager.recording();
                        // 复写dialog.recording();
                    }
                    break;

                case STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.button_recording);
                    setText(R.string.want_to_cancle);
                    // dialog want to cancel
                    mDialogManager.wantToCancel();
                    break;

            }
        }

    }

    // [+] Class

    /**
     * 录音完成后的回调，回调给activiy，可以获得mtime和文件的路径
     *
     * @author nickming
     */
    public interface AudioFinishRecorderListener {
        /**
         * @param seconds  录音时长
         * @param filePath 录音文件路径名称
         */
        void onFinished(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        mListener = listener;
    }

    // 获取音量大小的runnable
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mhandler.sendEmptyMessage(MSG_VOICE_CHANGE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    // [-] Class

}
