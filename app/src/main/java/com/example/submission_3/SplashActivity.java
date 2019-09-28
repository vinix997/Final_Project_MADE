package com.example.submission_3;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tv = findViewById(R.id.tv_Movie);
        ImageView iv = findViewById(R.id.iv_Movie);
        getSupportActionBar().hide();
        Animation splashTransition = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        tv.startAnimation(splashTransition);
        iv.startAnimation(splashTransition);
        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    // Lama SplashScreen
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
