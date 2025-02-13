package com.example.prm392_duckracinggame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MediaPlayer soundEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        ImageView backgroundView = findViewById(R.id.backgroundView);


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);


        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true); // Lặp lại nhạc
        mediaPlayer.setVolume(1.0f, 1.0f); // Âm lượng tối đa
        mediaPlayer.start();



        soundEffect = MediaPlayer.create(this,R.raw.soundeffect);
        Button button = findViewById(R.id.btnPlay);
        Button button2 = findViewById(R.id.btnGameRules);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Phát sound effect
                soundEffect.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Phát sound effect
                soundEffect.start();
            }
        });
        ObjectAnimator animator = ObjectAnimator.ofFloat(backgroundView, "translationY", -1000f, 0f);
        animator.setDuration(3000);
        animator.setRepeatCount(0);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();

        ImageView duckView = findViewById(R.id.duck);


        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                duckView.setVisibility(View.VISIBLE);


                ObjectAnimator duckSwim = ObjectAnimator.ofFloat(duckView, "translationX", 0f, 800f);
                duckSwim.setDuration(4000);
                duckSwim.setRepeatCount(ObjectAnimator.INFINITE);
                duckSwim.setRepeatMode(ObjectAnimator.REVERSE);
                duckSwim.start();
            }
        });


        animator.start();
    }
}