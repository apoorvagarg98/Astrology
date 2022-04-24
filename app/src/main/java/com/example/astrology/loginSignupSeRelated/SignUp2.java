package com.example.astrology.loginSignupSeRelated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.astrology.R;
import com.example.astrology.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp2 extends AppCompatActivity {
    EditText dateofbirth,placeofbirth,birthtime,passs;
    RadioGroup radioGroup;
    FirebaseUser user1;
    String gender;
    FloatingActionButton signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        dateofbirth =findViewById(R.id.Birthdate);
        passs =findViewById(R.id.passsignup);
        birthtime =findViewById(R.id.birthtime);
        signup = findViewById(R.id.signuppage3);
        radioGroup = findViewById(R.id.radio);
        placeofbirth = findViewById(R.id.birthplace);
        mAuth = FirebaseAuth.getInstance();
        user1 = mAuth.getCurrentUser();

        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                                  switch (i){
                                                      case R.id.male:
                                                          gender = "male";
                                                          break;
                                                      case  R.id.female:
                                                          gender = "female";
                                                          break;


                                                  }
                                              }
                                          });
                                          preregisterUser();
                                      }
                                  }
        );

    }

    private void preregisterUser( ) {

        mAuth = FirebaseAuth.getInstance();
        String namespre,phonespre,emailspre,passwordspre,dateofbirthst,placeofbirthst,birthtimest;
        namespre = getIntent().getStringExtra("name").toString();
        phonespre =getIntent().getStringExtra("mobile").toString();
        emailspre =getIntent().getStringExtra("email").toString().trim();
        passwordspre = passs.getText().toString();
        dateofbirthst = dateofbirth.getText().toString();
        placeofbirthst = placeofbirth.getText().toString();
        birthtimest = birthtime.getText().toString();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");

        mAuth.createUserWithEmailAndPassword(emailspre,passwordspre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    userModel pr = new userModel(namespre,dateofbirthst,placeofbirthst,birthtimest,emailspre,phonespre,gender);

                    user.child(mAuth.getUid()).setValue(pr).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    Toast.makeText(SignUp2.this, "User Registered succesfully, Please Login", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUp2.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
