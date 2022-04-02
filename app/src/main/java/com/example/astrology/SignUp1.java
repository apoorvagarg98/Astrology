package com.example.astrology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUp1 extends AppCompatActivity {
   FloatingActionButton signup2;
   TextView alreadyHaveacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        signup2= findViewById(R.id.signup2);
        alreadyHaveacc= findViewById(R.id.alreadyHaveacc);
        alreadyHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp1.this,Login.class));
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp1.this,SignUp2.class));
            }
        });

    }
}