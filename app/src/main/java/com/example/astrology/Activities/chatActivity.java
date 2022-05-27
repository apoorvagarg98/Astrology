package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class chatActivity extends AppCompatActivity {
TextView timer;
FirebaseAuth mAuth;
FirebaseUser user;
String uid,clientid;
DatabaseReference req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        timer = findViewById(R.id.Timer);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = getIntent().getStringExtra("expertid");
        String durationOfTimer = getIntent().getStringExtra("Duration of Timer");
        clientid =getIntent().getStringExtra("userid");
        req = FirebaseDatabase.getInstance().getReference().child("request").child(uid).child(clientid);


        long dot = Integer.valueOf(durationOfTimer);
        CountDownTimer cd = new CountDownTimer(40000000, 1000) {
            @Override
            public void onTick(long l) {

                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)));
                timer.setText(sDuration);

            }

            @Override
            public void onFinish() {

                req.child("status").setValue("completed").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(chatActivity.this, "your time period has ended", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

              //  startActivity(new Intent(chatActivity.this,selectClientRequests.class));

            }
        }.start();

    }


}




