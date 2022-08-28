package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.R;
import com.example.astrology.models.APITask;
import com.example.astrology.models.IAPITaskCallBack;
import com.example.astrology.models.requestModel;
import com.example.astrology.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class acceptordeclinepage extends AppCompatActivity  {


TextView name,gender,dob,txtAstroDetails,duration,date,birthplace,totalAmount,birthTime;
Button accept ,decline;
String expertuid,userid;
String durationoftimer,nameofuser;



public FirebaseAuth mAuth;
    public FirebaseUser user;
    public DatabaseReference request,usersdb,Acceptedrequest,Declinedrequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptordeclinepage);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        expertuid = user.getUid();
        userid = getIntent().getStringExtra("userid");
        name = findViewById(R.id.nameinreq);
        txtAstroDetails = (TextView) findViewById(R.id.txtAstroDetails);
        gender = findViewById(R.id.genderinreq);
        dob = findViewById(R.id.dobinreq);
        duration = findViewById(R.id.durationinreq);

        date = findViewById(R.id.dateinreq);
        birthplace = findViewById(R.id.birthplaceinreq);
        totalAmount = findViewById(R.id.totalAmountinreq);
        birthTime = findViewById(R.id.birthtimeinreq);
        accept = findViewById(R.id.Accept);
        decline = findViewById(R.id.Decline);


        request = FirebaseDatabase.getInstance().getReference().child("request").child(expertuid).child(userid);
        Acceptedrequest = FirebaseDatabase.getInstance().getReference().child("Acceptedrequest").child(expertuid).child(userid);
        Declinedrequest = FirebaseDatabase.getInstance().getReference().child("Declinedrequest").child(expertuid).child(userid);
        usersdb = FirebaseDatabase.getInstance().getReference().child("users").child(userid);



//for retreiving the details
        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    requestModel rm =  snapshot.getValue(requestModel.class);
                    durationoftimer = rm.getDurationInMin();
                    duration.setText("Duration - "+rm.getDurationInMin()+"min");

                    date.setText("Date - "+rm.getDateOfBooking());
                    totalAmount.setText("Toatl Amount - "+rm.getTotalAmount());
                    usersdb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                userModel um = snapshot.getValue(userModel.class);
                                nameofuser = um.getName();
                                name.setText("Name - "+ nameofuser);
                                dob.setText("Date Of Birth - " + um.getDateofbirth());
                                gender.setText("Gender - " +um.getGender());
                                birthplace.setText("birthPlace - " + um.getPlaceofbirth());
                                birthTime.setText("birthTime - " + um.getBirthtime());
                                txtAstroDetails.setText(um.getLink());


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


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setText("Accepted");
                accept.setEnabled(false);
                decline.setEnabled(false);
                request.child("status").setValue("Accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

request.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        requestModel rm =  snapshot.getValue(requestModel.class);
        Acceptedrequest.setValue(rm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
Intent intent = new Intent(acceptordeclinepage.this,chatActivity.class);
intent.putExtra("Duration of Timer",durationoftimer);
intent.putExtra("userid",userid);
intent.putExtra("recieverId",userid);
intent.putExtra("expertid",user.getUid());
intent.putExtra("senderId",user.getUid());
intent.putExtra("name",nameofuser);
                              startActivity(intent);
                        }}
                });
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                decline.setText("Declined");
                decline.setEnabled(false);
                accept.setEnabled(false);
                request.child("status").setValue("Declined").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        request.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                requestModel rm =  snapshot.getValue(requestModel.class);
                                Declinedrequest.setValue(rm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });



            }
        });
    }
}

