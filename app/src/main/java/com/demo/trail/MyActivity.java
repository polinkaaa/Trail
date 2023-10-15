package com.demo.trail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewNameOfTourBase;
    private TextView textViewDescriptionOfTourBase;
    private String name;

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
        setContentView(R.layout.activity_my);
        initView();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
        }
        textViewName.setText(String.format(getString(R.string.hello_user), name));
        textViewNameOfTourBase.setText(getString(R.string.tour_base_name));
        textViewDescriptionOfTourBase.setText(getString(R.string.tour_base_description));
    }

    public void onClickOpenMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void initView() {
        textViewName = findViewById(R.id.textViewName);
        textViewNameOfTourBase = findViewById(R.id.textViewNameOfTourBase);
        textViewDescriptionOfTourBase = findViewById(R.id.textViewDescriptionOfTourBase);
    }
}