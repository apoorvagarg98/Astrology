package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.astrology.ExpertSignUp2;
import com.example.astrology.ExpertSignUp3;
import com.example.astrology.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUp1 extends AppCompatActivity {
   FloatingActionButton signup2;
    EditText name,mobile,email;
   TextView alreadyHaveacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        name =findViewById(R.id.fullname);
        email =findViewById(R.id.emailid);
        mobile =findViewById(R.id.mobs);
        signup2= findViewById(R.id.signup2);

        alreadyHaveacc= findViewById(R.id.alreadyHaveacc);
        alreadyHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp1.this, ExpertSignUp3.class));
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent =  new Intent(SignUp1.this, SignUp2.class);
              intent.putExtra("name",name.getText().toString());
              intent.putExtra("email",email.getText().toString());
              intent.putExtra("mobile",mobile.getText().toString());
              startActivity(intent);
            }
        });

    }
}

