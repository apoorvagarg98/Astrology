package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class bookingPage extends AppCompatActivity implements View.OnClickListener {
    String expertid,selection,r,email;
    DatabaseReference dbr;
    TextView name,exp,rpm,expertise,dateOfBooking,timeOfBooking,duration,totalAmountToBePaid;
    Button pay,pickdate,picktime,pickDuration;

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


        name = findViewById(R.id.NAmeofExpert);
        exp = findViewById(R.id.experiencebig);
        rpm = findViewById(R.id.ratepmin);
        totalAmountToBePaid = findViewById(R.id.totalAmountToBePaid);
        expertise = findViewById(R.id.allselections);
        pay = findViewById(R.id.pay);
        pickdate = findViewById(R.id.pickdate);
        picktime = findViewById(R.id.picktime);
        pickDuration = findViewById(R.id.pickduratiion);

        dateOfBooking = findViewById(R.id.dateOfBooking);
        timeOfBooking = findViewById(R.id.timeOfBooking);
        duration = findViewById(R.id.duraOfBook);

        picktime.setOnClickListener(this);
        pickdate.setOnClickListener( this);
        pickDuration.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                     r = snapshot.child("stexperience").getValue().toString();
                     email = snapshot.child("exemails").getValue().toString();
                    name.setText(snapshot.child("exnames").getValue().toString());
                    exp.setText(r);
                    rpm.setText(snapshot.child("stamt").getValue().toString());
                    expertise.setText(snapshot.child("selection").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});


        pay.setOnClickListener(new View.OnClickListener() {
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
                    object.put("amount",rs*100);
                    object.put("prefill.contact","9711445734");
                    object.put("prefill.email",email);
                    checkout.open(bookingPage.this,object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




    }




    public void onPaymentSuccess(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("paymment ID");
        builder.setMessage(s    );
        builder.show();


    }
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "payment failed"+s, Toast.LENGTH_SHORT).show();
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

                            dateOfBooking.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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

                            timeOfBooking.setText(hourOfDay + ":" + minute);
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
         rs =  Integer.valueOf(r) * totalmin;
         totalAmountToBePaid.setText(String.valueOf(rs));


    }
}



