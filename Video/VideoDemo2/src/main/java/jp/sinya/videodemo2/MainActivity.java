package jp.sinya.videodemo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video_view);

        //本地路径
        videoView.setVideoPath("http://192.168.1.3:8080/Sinya/kickflip_boardslide.flv");
        //网络资源
        //videoView.setVideoURI(Uri.parse(""));

        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        controller.setMediaPlayer(videoView);
    }
}
