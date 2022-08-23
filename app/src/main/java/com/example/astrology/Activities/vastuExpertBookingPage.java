package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.Notifications.APIService;
import com.example.astrology.Notifications.Data;
import com.example.astrology.Notifications.MyResponse;
import com.example.astrology.Notifications.Sender;
import com.example.astrology.Notifications.Token;
import com.example.astrology.R;
import com.example.astrology.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class vastuExpertBookingPage extends AppCompatActivity {
    EditText areaofproperty,totalfloors,totalrooms,kitchendine,washrooms,basement,address,remarks;
    TextView totalAmountToBePaid,rpm;
    String propertyname,userid,selection,expertid,nameofuser,expe,exabtyrslf,sareaofproperty,stotalfloors,stotalrooms,skitchendine,swashrooms,sbasement,saddress,sremarks,srpm;
    DatabaseReference dbr,requestdb;
    Button verequest;
    APIService apiService;
    Boolean notify=false;
    private int rs,totalmin;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    TimePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vastu_expert_booking_page);


        selection = getIntent().getStringExtra("selection").toString();
        expertid = getIntent().getStringExtra("expertuid").toString();
        propertyname = getIntent().getStringExtra("propertyname").toString();

        areaofproperty = findViewById(R.id.areaofproperty);
        totalfloors = findViewById(R.id.totalfloors);
        totalrooms = findViewById(R.id.totalrooms);
        kitchendine = findViewById(R.id.kitchendine);
        washrooms = findViewById(R.id.washrooms);
        basement = findViewById(R.id.basement);
        address = findViewById(R.id.address);
        remarks = findViewById(R.id.remarks);
        verequest = findViewById(R.id.verequest);
        totalAmountToBePaid = findViewById(R.id.totalamountinvastu);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userid = user.getUid().toString();

        requestdb = FirebaseDatabase.getInstance().getReference().child("request").child(expertid).child(userid);
        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    nameofuser = snapshot.child("exnames").getValue().toString();
                    expe = snapshot.child("experience").getValue().toString();
                    exabtyrslf = snapshot.child("exabtyrslf").getValue().toString();
                    srpm = snapshot.child("stamt").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        verequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sareaofproperty=areaofproperty.getText().toString();
                stotalfloors = totalfloors.getText().toString();
                stotalrooms = totalrooms.getText().toString();
                skitchendine = kitchendine.getText().toString();
                swashrooms = washrooms.getText().toString();
                sbasement = basement.getText().toString();
                saddress = address.getText().toString();
                sremarks = remarks.getText().toString();

                rs = Integer.valueOf(srpm)*(Integer.valueOf(stotalrooms)+Integer.valueOf(skitchendine)+Integer.valueOf(sbasement)+Integer.valueOf(swashrooms));
                totalAmountToBePaid.setText(String.valueOf(rs));

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_rfooZLYQbv7p5h");
                checkout.setImage(R.drawable.mainlogo);
                JSONObject object = new JSONObject();
                try {
                    object.put("name","Aadishakti");
                    object.put("description","payment for"+selection);
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",rs*100);
                    object.put("prefill.contact","9711445734");
                    object.put("prefill.email","shivamkumar@gmail.com");
                    checkout.open(vastuExpertBookingPage.this,object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void sendNotification( String reciever, final String username,String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("tokens");
        Query query = tokens.orderByKey().equalTo(reciever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(user.getUid(),R.mipmap.ic_launcher, username +": "+message, "new message", vastuExpertBookingPage.this.expertid);
                    Sender sender= new Sender(data , token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success !=1){
                                            Toast.makeText(vastuExpertBookingPage.this, "failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });

            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            });
    }


    private void onPaymentSuccess(String s) {

        notify = true;
        HashMap hashMap = new HashMap();
        hashMap.put("name", nameofuser);
        hashMap.put("email","shivamtza1y@gmail.com");
        hashMap.put("phone","7691019045");
        hashMap.put("bookedyoufor", selection);
        hashMap.put("totalAmount",rs );
        hashMap.put("status","pending");
        hashMap.put("paymentStatus", "completed");
        hashMap.put("Totalareaofproperty",sareaofproperty);
        hashMap.put("totalfloors",stotalfloors);
        hashMap.put("totalrooms",stotalrooms);
        hashMap.put("totalkitchendine",skitchendine);
        hashMap.put("washrooms",swashrooms);
        hashMap.put("basements",sbasement);
        hashMap.put("address",saddress);
        hashMap.put("remarks",sremarks);
        requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())

                {
                    Intent intent = new Intent(vastuExpertBookingPage.this,chatActivity.class);
                    intent.putExtra("userid",user.getUid());
                    intent.putExtra("expertid",expertid);
                    intent.putExtra("name",nameofuser);
                    intent.putExtra("Duration of Timer",String.valueOf(totalmin));

                    startActivity(intent);

                }
            }
        });

        requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel user = dataSnapshot.getValue(userModel.class);
                if (notify) {
                    sendNotification(expertid, user.getName(), "new request ");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onPaymentError(int i, String s) {
        notify = true;
        HashMap hashMap = new HashMap();
        hashMap.put("name", nameofuser);
        hashMap.put("email","shivamtza1y@gmail.com");
        hashMap.put("phone","7691019045");
        hashMap.put("bookedyoufor", selection);
        hashMap.put("totalAmount",rs );
        hashMap.put("status","pending");
        hashMap.put("paymentStatus", "completed");
        hashMap.put("Totalareaofproperty",sareaofproperty);
        hashMap.put("totalfloors",stotalfloors);
        hashMap.put("totalrooms",stotalrooms);
        hashMap.put("totalkitchendine",skitchendine);
        hashMap.put("washrooms",swashrooms);
        hashMap.put("basements",sbasement);
        hashMap.put("address",saddress);
        hashMap.put("remarks",sremarks);

        requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())

                {
                    Intent intent = new Intent(vastuExpertBookingPage.this,chatActivity.class);
                    intent.putExtra("userid",user.getUid());
                    intent.putExtra("expertid",expertid);
                    intent.putExtra("name",nameofuser);
                    intent.putExtra("Duration of Timer",String.valueOf(totalmin));

                    startActivity(intent);

                }
            }
        });

        requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel user = dataSnapshot.getValue(userModel.class);
                if (notify) {
                    sendNotification(expertid, user.getName(), "new request ");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






}