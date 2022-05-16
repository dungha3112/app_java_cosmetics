package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.app_cosmetics.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3500);
                } catch (Exception e) {

                } finally {
                    startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
                    finish();
                }
            }
        };
        thread.start();

    }

}