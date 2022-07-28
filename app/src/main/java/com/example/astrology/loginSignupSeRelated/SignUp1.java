package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.astrology.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUp1 extends AppCompatActivity {
   FloatingActionButton signup2;
    EditText name,mobile,email;
    String gender;
    RadioGroup radioGroup;
   TextView alreadyHaveacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        radioGroup = findViewById(R.id.radio);

        name =findViewById(R.id.fullname);
        email =findViewById(R.id.emailid);
        mobile =findViewById(R.id.mobs);
        signup2= findViewById(R.id.signup2);

        alreadyHaveacc= findViewById(R.id.alreadyHaveacc);
        alreadyHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp1.this, Login.class));
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male:
                        gender = "male";
                        break;
                    case  R.id.female:
                        gender =  "female";
                        break;


                }
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent =  new Intent(SignUp1.this, SignUp2.class);
              intent.putExtra("name",name.getText().toString());
              intent.putExtra("email",email.getText().toString());
              intent.putExtra("mobile",mobile.getText().toString());
              intent.putExtra("gender", gender);
              startActivity(intent);
            }
        });

    }
}

