package com.example.prm392_duckracinggame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnPlay;
    private Button btnGameRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);

        ImageView backgroundView = findViewById(R.id.backgroundView);
        btnPlay = findViewById(R.id.btnPlay);
        btnGameRules = findViewById(R.id.btnGameRules);

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

        btnGameRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameRulesActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("nameid", name);
                startActivity(intent);
            }
        });
    }
}