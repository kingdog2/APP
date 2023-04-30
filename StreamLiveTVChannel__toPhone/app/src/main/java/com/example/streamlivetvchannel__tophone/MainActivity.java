package com.example.streamlivetvchannel__tophone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private String videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private ProgressDialog pd;///等待圖示的物件
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        pd = new ProgressDialog(this);
        pd.setMessage("Buffering(緩沖)...");
        pd.setCancelable(true);
        ///当设置为flase，点击返回键是无法返回的。默认为true
        playVideo();
    }

    private void playVideo() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        ///控制器(暫停之類的)
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri videoUri = Uri.parse(videoUrl);
        //給控制器
        videoView.setMediaController(mediaController);
        
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pd.dismiss();
                videoView.start();
            }
        });
    }
}