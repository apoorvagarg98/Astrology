package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.astrology.R;
import com.example.astrology.models.requestModel;
import com.example.astrology.models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class acceptordeclinepage extends AppCompatActivity {
TextView name,gender,dob,duration,time,date,birthplace,totalAmount,birthTime;
Button accept ,decline;
String expertuid,userid;
public FirebaseAuth mAuth;
    public FirebaseUser user;
    public DatabaseReference request,usersdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptordeclinepage);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        expertuid = user.getUid();
        userid = getIntent().getStringExtra("useruid");
        name = findViewById(R.id.nameinreq);
        gender = findViewById(R.id.genderinreq);
        dob = findViewById(R.id.dobinreq);
        duration = findViewById(R.id.durationinreq);
        time = findViewById(R.id.timeinreq);
        date = findViewById(R.id.dateinreq);
        birthplace = findViewById(R.id.birthplaceinreq);
        totalAmount = findViewById(R.id.totalAmountinreq);
        birthTime = findViewById(R.id.birthtimeinreq);
        accept = findViewById(R.id.Accept);
        decline = findViewById(R.id.Decline);


        request = FirebaseDatabase.getInstance().getReference().child("request").child(expertuid).child(userid);
        usersdb = FirebaseDatabase.getInstance().getReference().child("users").child(userid);

        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    requestModel rm =  snapshot.getValue(requestModel.class);
                    name.setText("Name - "+rm.getName());
                    duration.setText("Duration - "+rm.getDurationInMin()+"min");
                    time.setText("Time - "+rm.getTimeOfBooking());
                    date.setText("Date - "+rm.getDateOfBooking());
                    totalAmount.setText("Toatl Amount - "+rm.getTotalAmount());
                    usersdb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                userModel um = snapshot.getValue(userModel.class);
                                dob.setText("Date Of Birth - " + um.getDateofbirth());
                                gender.setText("Gender - " +um.getGender());
                                birthplace.setText("birthPlace - " + um.getPlaceofbirth());
                                birthTime.setText("birthTime - " + um.getBirthtime());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
