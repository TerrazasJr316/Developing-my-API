package com.example.brainylib;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.index);
        videoView.setVideoURI(uri);
        videoView.setOnCompletionListener(mediaPlayer -> {
            videoView.start();
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                mediaPlayer.setScreenOnWhilePlaying(true);
                mediaPlayer.start();
            }
        });

        ImageView loadingGif = findViewById(R.id.loadingGif);

        // GIF usando Glide
        Glide.with(this).asGif().load(R.drawable.loading_index).into(loadingGif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent abrirHome = new Intent(getApplicationContext(), MainHome.class);
                startActivity(abrirHome);
                finish();
            }
        }, 9000); // 9 segundos de retraso

    }
}