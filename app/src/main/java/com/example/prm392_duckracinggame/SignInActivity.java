package com.example.prm392_duckracinggame;

import android.accounts.Account;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText password;
    private TextView notAccYet;
    private Button signIn;

    private List<UserAccount> listAcc = new ArrayList<>();

    private final String REQUIRE = "require";
    private final String guestAcc = "guest";
    private final String guestPass = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        notAccYet = findViewById(R.id.txtNotAccountYet);
        signIn = findViewById(R.id.btnLogin);

        Intent myIntent = getIntent();
        if (myIntent != null && myIntent.hasExtra("tmp")) {
            Bundle myBundle = myIntent.getBundleExtra("tmp");
            String tmpAcc = myBundle.getString("username");
            String tmpPass = myBundle.getString("password");

            for(UserAccount user : listAcc) {
                if(TextUtils.equals(user.getUserName().toString(), tmpAcc)) {
                    Toast.makeText(this, "Add new user successfully!", Toast.LENGTH_LONG).show();
                    break;
                }
            }

            int newId = listAcc.size() + 1;
            UserAccount newUser = new UserAccount(newId, tmpAcc, tmpPass);
            listAcc.add(newUser);
            Toast.makeText(this, "!", Toast.LENGTH_LONG).show();
        }

        notAccYet.setOnClickListener(this);
        signIn.setOnClickListener(this);
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

        if(!listAcc.isEmpty()) {
            for(UserAccount user : listAcc) {
                if((TextUtils.equals(userName.getText().toString(), user.username)) && (TextUtils.equals(password.getText().toString(), user.password)))  {
                        return true;
                }
            }
        }

        if(!TextUtils.equals(userName.getText().toString(), guestAcc)) {
            Toast.makeText(this, "Ivalid access!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!TextUtils.equals(password.getText().toString(), guestPass)) {
            Toast.makeText(this, "Ivalid access!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void SignIn() {
        if(!checkInput()) {
            return;
        }

        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void SignUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnLogin) {
            SignIn();
        } else if (id == R.id.txtNotAccountYet) {
            SignUpForm();
        } else {
            throw new IllegalStateException("Unexpected value: " + id);
        }
    }

    public static class UserAccount {
        private int id;
        private String username;
        private String password;

        public UserAccount(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public int getId() {
            return id;
        }
        public String getUserName() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}


