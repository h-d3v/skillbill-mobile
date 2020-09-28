package com.jde.skillbill.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jde.skillbill.R;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    ImageView logo, bgImg;
    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logo);
        bgImg=findViewById(R.id.bgImg);
        //animation de background et du logo pour creer un effet splash
        bgImg.animate().translationY(-1600).setDuration(1800).setStartDelay(1500);
        logo.animate().translationY(1400).setDuration(1800).setStartDelay(1500);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(SplashActivity.this,ActivityConnexion.class);
                startActivity(dsp);
                finish();
            }
        },2100);
    }
}
