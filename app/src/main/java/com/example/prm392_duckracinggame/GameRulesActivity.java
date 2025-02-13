package com.example.prm392_duckracinggame;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameRulesActivity  extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gamerules);
        ImageView backgroundView = findViewById(R.id.backgroundView);


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);


        mediaPlayer = MediaPlayer.create(this, R.raw.chill);
//        mediaPlayer.setLooping(true); // Lặp lại nhạc
//        mediaPlayer.setVolume(1.0f, 1.0f); // Âm lượng tối đa
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAndReleaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAndReleaseMediaPlayer();
    }

    private void stopAndReleaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}