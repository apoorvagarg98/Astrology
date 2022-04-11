package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.astrology.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bookingPage extends AppCompatActivity {
    String expertid,selection;
    DatabaseReference dbr;
    TextView name,exp,rpm,expertise;
    EditText timeOfBooking,dateOfBooking,duration;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        selection = getIntent().getStringExtra("exname2").toString();
        expertid = getIntent().getStringExtra("selection").toString();


        name = findViewById(R.id.NAmeofExpert);
        exp = findViewById(R.id.experiencebig);
        rpm = findViewById(R.id.ratepmin);
        expertise = findViewById(R.id.allselections);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name.setText(snapshot.child("exname").getValue().toString());
                    exp.setText(snapshot.child("stexperience").getValue().toString());
                    rpm.setText(snapshot.child("stamt").getValue().toString());
                    expertise.setText(snapshot.child("selection").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});

    }
}
