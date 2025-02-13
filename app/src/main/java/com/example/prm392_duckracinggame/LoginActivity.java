package com.example.prm392_duckracinggame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private MediaPlayer soundEffectPlayer;
    private MediaPlayer soundEffectPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo MediaPlayer với file sound effect
        soundEffectPlayer = MediaPlayer.create(this, R.raw.soundeffect);
        soundEffectPlayer2 = MediaPlayer.create(this, R.raw.tienggophim);
        Button loginButton = findViewById(R.id.btnLogin);
        EditText edUser = findViewById(R.id.edtUsername);
        EditText edPassword = findViewById(R.id.edtPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundEffectPlayer.start();

            }
        });
        edUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundEffectPlayer.start();

            }
        });
        edPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundEffectPlayer.start();

            }
        });
    }



}

