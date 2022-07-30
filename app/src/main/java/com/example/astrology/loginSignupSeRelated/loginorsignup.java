package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astrology.R;

public class loginorsignup extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=10000;

    Animation topAnimation,bottomAnimation,middleAnimation;
    Button signupbutton,signinbutton;
    TextView topline1,hindiname,englishname;
    ImageView topicon,swagat,mainlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginorsignup);


        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);


        signupbutton= findViewById(R.id.signupbutton);
        signinbutton= findViewById(R.id.signinbutton);
        mainlogo= findViewById(R.id.astro_img);
        topline1= findViewById(R.id.topline1);
        englishname= findViewById(R.id.englishname);
        hindiname= findViewById(R.id.hindiname);
        topicon=findViewById(R.id.topicon);
        swagat=findViewById(R.id.swagat);


//        topicon.setAnimation(topAnimation);
//        mainlogo.setAnimation(middleAnimation);
        englishname.setAnimation(topAnimation);
        hindiname.setAnimation(topAnimation);
        swagat.setAnimation(middleAnimation);
        topline1.setAnimation(middleAnimation);
        signupbutton.setAnimation(bottomAnimation);
        signinbutton.setAnimation(bottomAnimation);

        signinbutton= findViewById(R.id.signinbutton);

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginorsignup.this, Login.class));
            }
        });


        signupbutton= findViewById(R.id.signupbutton);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginorsignup.this, SignUp1.class));
            }
        });



    }
}