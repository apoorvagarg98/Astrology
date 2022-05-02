package com.example.astrology.loginSignupSeRelated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.Activities.selectClientRequests;
import com.example.astrology.Notifications.Token;
import com.example.astrology.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class ExpertLogin extends AppCompatActivity {

    Button login;
    TextView dontHaveacc,GoToExpert;
    FirebaseAuth mAuth;
    FirebaseUser user;

    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        email= findViewById(R.id.Exfullname);
        GoToExpert= findViewById(R.id.GoToExpert);
        pass = findViewById(R.id.Expass);
        login= findViewById(R.id.Exlogin);
        dontHaveacc= findViewById(R.id.ExdontHaveacc);

        dontHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpertLogin.this, ExpertSignUp1.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });



    }




    private void loginuser() {

        mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               
                Toast.makeText(ExpertLogin.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ExpertLogin.this, selectClientRequests.class);
                intent.putExtra("id",mAuth.getUid());
                startActivity(intent);

            }
        });
    }
}
