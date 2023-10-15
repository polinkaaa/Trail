package com.demo.trail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EnterActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Нажмите кнопку еще раз для выхода", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        initViews();
    }

    public void onClickEnter(View view) {
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (isFilled(login,password)){
            Intent intent = new Intent(this, MyActivity.class);
            intent.putExtra("name", login);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.is_filled), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled(String login, String password) {
        return !login.isEmpty() && !password.isEmpty();
    }

    private void initViews() {
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
}