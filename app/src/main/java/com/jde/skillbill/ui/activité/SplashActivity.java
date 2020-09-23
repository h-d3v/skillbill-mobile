package com.jde.skillbill.ui.activité;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jde.skillbill.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo, bgImg;
    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logo);
        bgImg=findViewById(R.id.bgImg);
        //animation de background et du logo pour creer un effet splash
        bgImg.animate().translationY(-1600).setDuration(1500).setStartDelay(3500);
        logo.animate().translationY(1400).setDuration(1500).setStartDelay(3500);
    }
}
