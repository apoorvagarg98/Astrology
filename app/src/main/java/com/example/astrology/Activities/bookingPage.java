package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.astrology.Notifications.APIService;
import com.example.astrology.Notifications.Client;
import com.example.astrology.Notifications.Data;
import com.example.astrology.Notifications.MyResponse;
import com.example.astrology.Notifications.Sender;
import com.example.astrology.Notifications.Token;
import com.example.astrology.models.userModel;
import com.google.firebase.database.Query;
import com.razorpay.Checkout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.astrology.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bookingPage extends AppCompatActivity implements View.OnClickListener {
    String expertid,selection,r,email,userid,nameofuser,date,time,phone,exabtyrslf;
    DatabaseReference dbr,requestdb;
    TextView name,exp,rpm,expertise,dateOfBooking,timeOfBooking,duration,totalAmountToBePaid,showstatus,exabtyrslftxtvw;
    Button pay,pickdate,picktime,pickDuration,request,newBooking,chat;
    LinearLayout cly1,cly2;
    ImageView show;

    APIService apiService;

    boolean notify = false;

    private int mYear, mMonth, mDay, mHour, mMinute,hour,minutes,rs,totalmin;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    TimePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_booking_page);
        selection = getIntent().getStringExtra("selection").toString();
        expertid = getIntent().getStringExtra("expertuid").toString();

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


exabtyrslftxtvw = findViewById(R.id.aboutme);
        name = findViewById(R.id.NAmeofExpert);
        exp = findViewById(R.id.experiencebig);
        rpm = findViewById(R.id.ratepmin);
        totalAmountToBePaid = findViewById(R.id.totalAmountToBePaid);
        expertise = findViewById(R.id.allselections);
        pay = findViewById(R.id.pay);
        pickdate = findViewById(R.id.pickdate);
        picktime = findViewById(R.id.picktime);
        pickDuration = findViewById(R.id.pickduratiion);
        request = findViewById(R.id.request);
        cly1 = findViewById(R.id.cly1);
        cly2 = findViewById(R.id.cly2);
        showstatus = findViewById(R.id.showstatus);
        newBooking = findViewById(R.id.newBooking);
        show = findViewById(R.id.imageView3);

        chat = findViewById(R.id.chat);





        dateOfBooking = findViewById(R.id.dateOfBooking);
        timeOfBooking = findViewById(R.id.timeOfBooking);
        duration = findViewById(R.id.duraOfBook);

        picktime.setOnClickListener(this);
        pickdate.setOnClickListener( this);
        pickDuration.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userid =  user.getUid().toString();
        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);
        requestdb = FirebaseDatabase.getInstance().getReference().child("request").child(expertid).child(userid);

        requestdb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("status").getValue().toString().equals("Accepted")) {
                        startActivity(new Intent(bookingPage.this,chatActivity.class));

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

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                     r = snapshot.child("stexperience").getValue().toString();
                     email = snapshot.child("exemails").getValue().toString();
                     nameofuser = snapshot.child("exnames").getValue().toString();
                     phone = snapshot.child("exmobile").getValue().toString();
                    exabtyrslf = snapshot.child("exabtyrslf").getValue().toString();
                    name.setText("Name - "+nameofuser);
                    exp.setText("Experience - "+r+"yrs");
                    exabtyrslftxtvw.setText("About expert - " +exabtyrslf);
                    rpm.setText("Rate Per Min - "+snapshot.child("stamt").getValue().toString()+"rs/min");
                    expertise.setText("Booking for - "+snapshot.child("selection").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});

        newBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cly1.setVisibility(View.VISIBLE);
                cly2.setVisibility(View.GONE);
            }
        });

       /* request.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                notify = true;
            HashMap hashMap = new HashMap();
            hashMap.put("name",nameofuser);
            hashMap.put("email",email);
            hashMap.put("phone",phone);
            hashMap.put("bookedYouFor",selection);
            hashMap.put("durationInMin",String.valueOf(totalmin));
            hashMap.put("DateOfBooking",date);
            hashMap.put("timeOfBooking",time);
            hashMap.put("totalAmount",rs);
            hashMap.put("status","pending");
            hashMap.put("paymentStatus","pending");


                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
              //  LocalDateTime now = LocalDateTime.now();
              //  System.out.println(dtf.format(now));

                requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                       if(task.isSuccessful())

                       {
                           Toast.makeText(bookingPage.this, "request sent sucesfully kindly wait for acceptance", Toast.LENGTH_SHORT).show();
                           request.setVisibility(View.GONE);


                       }
                    }
                });

                requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                requestdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userModel user = dataSnapshot.getValue(userModel.class);
                        if (notify) {
                            sendNotifiaction(expertid, user.getName(), "new request ");
                        }
                        notify = false;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });*/

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_rfooZLYQbv7p5h");
                checkout.setImage(R.drawable.mainlogo);
                JSONObject object = new JSONObject();
                try {
                    object.put("name","Aadishakti");
                    object.put("description","payment for"+selection);
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",rs);
                    object.put("prefill.contact","9711445734");
                    object.put("prefill.email",email);
                    checkout.open(bookingPage.this,object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




    }

    private void sendNotifiaction(String receiver,final String username, final String message) {

        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(user.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            expertid);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(bookingPage.this, "Failed!", Toast.LENGTH_SHORT).show();
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void onPaymentSuccess(String s) {
        notify = true;
        HashMap hashMap = new HashMap();
        hashMap.put("name",nameofuser);
        hashMap.put("email",email);
        hashMap.put("phone",phone);
        hashMap.put("bookedYouFor",selection);
        hashMap.put("durationInMin",String.valueOf(totalmin));
        hashMap.put("DateOfBooking",date);
        hashMap.put("timeOfBooking",time);
        hashMap.put("totalAmount",rs);
        hashMap.put("status","pending");
        hashMap.put("paymentStatus","completed");

        requestdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())

                {
                    Toast.makeText(bookingPage.this, "request sent sucesfully kindly wait for acceptance", Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestdb = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        requestdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel user = dataSnapshot.getValue(userModel.class);
                if (notify) {
                    sendNotifiaction(expertid, user.getName(), "new request ");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

startActivity(new Intent(bookingPage.this,chatActivity.class));
     //   AlertDialog.Builder builder = new AlertDialog.Builder(this);
     //   builder.setTitle("paymment ID");
     //   builder.setMessage(s);
     //   builder.show();



    }
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "payment failed please try again after some time"+s, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View v) {


        if (v == pickdate) {

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

                            dateOfBooking.setText(date);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == picktime) {

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

                            timeOfBooking.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v==pickDuration)
        {
            final Calendar cldr = Calendar.getInstance();
             hour = cldr.get(Calendar.HOUR_OF_DAY);
             minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            picker = new TimePickerDialog(bookingPage.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                            duration.setText(sHour + ":" + sMinute);

                            getamounttobepaid(sHour,sMinute);
                        }
                    }, hour, minutes, true);
            picker.show();
        }
        }

    private void getamounttobepaid(int sHour, int sMinute) {
        int hrintomin =0;
         hrintomin = sHour*60;
         totalmin = hrintomin + sMinute;
         rs =  Integer.valueOf(r)*10 * totalmin;
         totalAmountToBePaid.setText(String.valueOf(rs));


    }
}



