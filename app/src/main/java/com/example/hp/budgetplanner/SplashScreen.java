package com.example.hp.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends AppCompatActivity {
        public final static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView img=(ImageView)findViewById(R.id.imageloader);
        final Animation animation= AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setDuration(4000);
        img.setAnimation(animation);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, ImageSlider.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
