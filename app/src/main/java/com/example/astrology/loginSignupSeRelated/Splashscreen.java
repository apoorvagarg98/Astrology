package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astrology.R;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=4000;


    Animation topAnimation,bottomAnimation,middleAnimation;
    TextView tagLine,headline,headline2,topline1,englishname,hindiname;
    ImageView mainlogo,swagat,topicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);


        mainlogo=findViewById(R.id.mainlogo);
        swagat=findViewById(R.id.swagat);

        tagLine= findViewById(R.id.tagLine);
        headline= findViewById(R.id.headline);
        headline2= findViewById(R.id.headline2);
        topline1= findViewById(R.id.topline1);
        englishname= findViewById(R.id.englishname);
        hindiname= findViewById(R.id.hindiname);
        topicon=findViewById(R.id.topicon);

        topicon.setAnimation(topAnimation);
        englishname.setAnimation(topAnimation);
        hindiname.setAnimation(topAnimation);


        mainlogo.setAnimation(middleAnimation);
        swagat.setAnimation(middleAnimation);

        tagLine.setAnimation(bottomAnimation);
        headline.setAnimation(bottomAnimation);
        headline2.setAnimation(bottomAnimation);
        topline1.setAnimation(middleAnimation);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent( Splashscreen.this, loginorsignup.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}