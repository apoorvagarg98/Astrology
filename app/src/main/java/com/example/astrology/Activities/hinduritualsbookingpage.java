package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.astrology.Notifications.APIService;
import com.example.astrology.R;
import com.example.astrology.models.userModel;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class hinduritualsbookingpage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Spinner typeofpuja;
    // exabtyrslf is expert about yourself where description is written of expert, r is used to show experience
    String userid,selection,expertid,r,email,nameofuser,exabtyrslf,totalmin,date,time;
    Button selecttime,selectdate,request;
    TextView hrratepmin,hrallselections,hraboutme,hrexperiencebig,hrnameofExpert,name,exp,rpm,expertise,exabtyrslftxtvw;
    TextView timeforritual,dateforritual,dateofevent,eventtime;
    FirebaseUser user;
    TimePickerDialog picker;
    private FirebaseAuth mAuth;
    private int mYear,mMonth,mDay,mHour,mMinute,price;
    DatabaseReference dbr,requestdb;
    APIService apiService;
    Boolean notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinduritualsbookingpage);


        hrratepmin = findViewById(R.id.hrratepmin);
        dateofevent = findViewById(R.id.dateforritual);
        eventtime = findViewById(R.id.timeforritual);
        hrallselections = findViewById(R.id.hrallselections);
        hraboutme = findViewById(R.id.hraboutme);
        hrexperiencebig = findViewById(R.id.hrexperiencebig);
        hrnameofExpert = findViewById(R.id.hrnameofExpert);
        typeofpuja= findViewById(R.id.typeofpuja);
        selecttime = findViewById(R.id.selecttime);
        selectdate = findViewById(R.id.selectdate);
        timeforritual = findViewById(R.id.timeforritual);
        dateforritual = findViewById(R.id.dateforritual);
        request = findViewById(R.id.request);
        expertise = findViewById(R.id.hrallselections);
                rpm = findViewById(R.id.hrratepmin);
name = findViewById(R.id.hrnameofExpert);
exp = findViewById(R.id.hrexperiencebig);
        exabtyrslftxtvw = findViewById(R.id.hraboutme);
        selecttime.setOnClickListener(this);
        selection = getIntent().getStringExtra("selection");
        expertid = getIntent().getStringExtra("expertid");
        selectdate.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userid = user.getUid().toString();
        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);
        requestdb = FirebaseDatabase.getInstance().getReference().child("request").child(expertid).child(userid);

        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("status").getValue().toString().equals("Accepted")){
                        startActivity(new Intent(hinduritualsbookingpage.this, chatActivity.class));
                    }
                    else if(snapshot.child("status").getValue().toString().equals("Declined"))
                    {

                    }
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Spinner spinner = findViewById(R.id.typeofpuja);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hrpujatype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    r = snapshot.child("experience").getValue().toString();
                    email = snapshot.child("exemails").getValue().toString();
                    nameofuser = snapshot.child("exnames").getValue().toString();
                    exabtyrslf = snapshot.child("exabtyrslf").getValue().toString();
                    name.setText("- " + nameofuser);
                    exp.setText("ExperNameience- "+ r+ "yrs");
                    exabtyrslftxtvw.setText("About expert- "+exabtyrslf);
                    rpm.setText("Rate- "+snapshot.child("stamt").getValue().toString()+"rs");
                    expertise.setText("Booking for- "+ snapshot.child("selection").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//redirecting to payment
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_rfooZLYQbv7p5h");
                checkout.setImage(R.drawable.mainlogo);
                JSONObject object = new JSONObject();

                try {
                    object.put("name","Aadishakti");
                    object.put("description","payment for " + selection );
                    object.put("theme.color", "#0093DD");
                    object.put("Currency","INR");
                    object.put("amount",price*100);
                    object.put("prefill.contact","9484658468");
                    object.put("prefill.email",email);
                    checkout.open(hinduritualsbookingpage.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    /*private void sendNotification(String receiver, final String username, final String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(user.getUid(), R.mipmap.ic_launcher,username+ ": "+message,"New message",expertid);

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>(){
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response){
                                   if (response.code()==200){
                                       if (response.body().success!=1){
                                           Toast.makeText(hinduritualsbookingpage.this,"Failed!",Toast.LENGTH_SHORT).show();
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
    }*/

    public void onPaymentSuccess(String s) {
        notify = true;
        HashMap hashMap = new HashMap();
        hashMap.put("name", nameofuser);
        hashMap.put("email",email);
        hashMap.put("phone","7437587856");
        hashMap.put("bookedyoufor",selection);
        hashMap.put("durationinmin",String.valueOf(totalmin));
        hashMap.put("dateofbooking",date);
        hashMap.put("timeofbooking",time);
        hashMap.put("totalamount",price);
        hashMap.put("status","pending");
        hashMap.put("paymentstatus","completed");
        requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                {
                    Intent intent = new Intent(hinduritualsbookingpage.this,chatActivity.class);
                    intent.putExtra("userid",user.getUid());
                    intent.putExtra("expertid",expertid);
                    intent.putExtra("Duration of Timer",String.valueOf(totalmin));

                    startActivity(intent);
                }
            }
        });

        requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel user = snapshot.getValue(userModel.class);
                if (notify){
                  //  sendNotification(expertid, user.getName(),"new request");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void onPaymentError(int i, Strings s) {
        notify = true;
        HashMap hashMap = new HashMap();
        hashMap.put("name",nameofuser);
        hashMap.put("email",email);
        hashMap.put("phone","365352775");
        hashMap.put("bookedYouFor",selection);
        hashMap.put("durationInMin",String.valueOf(totalmin));
        hashMap.put("DateOfBooking",date);
        hashMap.put("timeOfBooking",time);
        hashMap.put("totalAmount",price);
        hashMap.put("status","pending");
        hashMap.put("paymentStatus","completed");

        requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
               if (task.isSuccessful())
               {
                   Toast.makeText(hinduritualsbookingpage.this, "request sent successfully kindly wait for acceptance",Toast.LENGTH_SHORT).show();
               }
            }
        });
        requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel user = snapshot.getValue(userModel.class);
                if (notify) {
                   // sendNotification(expertid,user.getName(),"new request");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(new Intent(hinduritualsbookingpage.this,chatActivity.class));
        Toast.makeText(getApplicationContext(),"payment failed please try again after some time"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == selectdate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            dateofevent.setText(date);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == selecttime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time = hourOfDay + ":" + minute;

                            eventtime.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        if(i==0)
        {
            price = 0;

        }
        else if(i==1){
            price = 1;
        }
        else if(i==2){
            price = 2;
        }
        else if(i==3){
            price = 3;
        }
        else if(i==4){
            price = 4;
        }
        else if(i==5){
            price = 5;
        }
        else if(i==6){
            price = 6;
        }
        else if(i==7){
            price = 7;
        }
        else if(i==8){
            price = 8;
        }
        else if(i==9){
            price = 9;
        }
        else if(i==10){
            price = 10;
        }
        else if(i==11){
            price = 11;
        }
        else if(i==12){
            price = 12;
        }
        else if(i==13){
            price = 13;
        }
        else if(i==14){
            price = 14;
        }
        else if(i==15){
            price = 15;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}