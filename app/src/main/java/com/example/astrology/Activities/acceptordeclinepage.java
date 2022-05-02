package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.R;
import com.example.astrology.models.JSONPlaceholder;
import com.example.astrology.models.Post;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class acceptordeclinepage extends AppCompatActivity {
TextView name,gender,dob,duration,time,date,birthplace,totalAmount,birthTime;
Button accept ,decline;
String expertuid,userid;
    JSONPlaceholder jsonPlaceholder;
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
        Acceptedrequest = FirebaseDatabase.getInstance().getReference().child("Acceptedrequest").child(expertuid).child(userid);
        Declinedrequest = FirebaseDatabase.getInstance().getReference().child("Declinedrequest").child(expertuid).child(userid);
        usersdb = FirebaseDatabase.getInstance().getReference().child("users").child(userid);

        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    requestModel rm =  snapshot.getValue(requestModel.class);

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
                                name.setText("Name - "+um.getName());
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


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept.setText("Accepted");
                accept.setEnabled(false);
                decline.setEnabled(false);
                request.child("status").setValue("Accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
                    }
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://json.astrologyapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();

     jsonPlaceholder= retrofit.create(JSONPlaceholder.class);
        createPost();



    }

    private void createPost() {

        Post post = new Post(2022f,3f,2f,12f,23f);
        Call<Post> call = jsonPlaceholder.createPost(post,"b4312492c2db498dfb4cb96f23e122be");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(acceptordeclinepage.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }
                List<Post> postList = new ArrayList<>();
                postList.add(response.body());
                Toast.makeText(acceptordeclinepage.this, response.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(acceptordeclinepage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}

