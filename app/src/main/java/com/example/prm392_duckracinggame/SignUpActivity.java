package com.example.prm392_duckracinggame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer soundEffectPlayer;

    private EditText userName;
    private EditText password;
    private EditText confirmPass;
    private TextView alreadyAcc;
    private Button signUp;

    private final String REQUIRE = "Require!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Khởi tạo MediaPlayer với file sound effect
        soundEffectPlayer = MediaPlayer.create(this, R.raw.soundeffect);

        userName = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        confirmPass = findViewById(R.id.edtConfirmPassword);
        alreadyAcc = findViewById(R.id.txtAlreadyAccount);
        signUp = findViewById(R.id.btnSignUp);

        alreadyAcc.setOnClickListener(this);
        signUp.setOnClickListener(this);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEffectPlayer.start();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEffectPlayer.start();
            }
        });
        confirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEffectPlayer.start();
            }
        });
    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError(REQUIRE);
            return false;
        }

        if(TextUtils.isEmpty(password.getText().toString())) {
            password.setError(REQUIRE);
            return false;
        }

        if(TextUtils.isEmpty(confirmPass.getText().toString())) {
            confirmPass.setError(REQUIRE);
            return false;
        }

        if(!TextUtils.equals(password.getText().toString(), confirmPass.getText().toString())) {
            Toast.makeText(this, "Password are not match!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void SignUp() {
        soundEffectPlayer.start();
        if(!checkInput()) {
            return;
        }
        Intent intent = new Intent(this, SignInActivity.class);

        String tmpId = userName.getText().toString();
        String tmpPass = password.getText().toString();
        Bundle myBundle = new Bundle();
        myBundle.putString("username", tmpId);
        myBundle.putString("password", tmpPass);

        intent.putExtra("tmp", myBundle);

        startActivity(intent);
        finish();
    }

    public void SignInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSignUp) {
            SignUp();
        } else if (id == R.id.txtAlreadyAccount) {
            SignInForm();
        } else {
            throw new IllegalStateException("Unexpected value: " + id);
        }
    }

    @Override
    protected void onDestroy() {
        if (soundEffectPlayer != null) {
            soundEffectPlayer.release();
            soundEffectPlayer = null;
        }
        super.onDestroy();
    }
}
