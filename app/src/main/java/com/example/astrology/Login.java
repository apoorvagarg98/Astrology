package com.example.astrology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class  Login extends AppCompatActivity {
    Button yo;
    TextView dontHaveacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        yo= findViewById(R.id.yo);
        dontHaveacc= findViewById(R.id.dontHaveacc);



        dontHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp1.class));
            }
        });


    }
}