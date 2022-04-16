package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;

public class bookingPage extends AppCompatActivity {
    String expertid,selection;
    DatabaseReference dbr;
    TextView name,exp,rpm,expertise;
    Button pay;
    EditText timeOfBooking,dateOfBooking,duration;
    private FirebaseAuth mAuth;
    FirebaseUser user;

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
        expertise = findViewById(R.id.allselections);
        pay = findViewById(R.id.pay);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        dbr = FirebaseDatabase.getInstance().getReference().child("Experts").child(selection).child(expertid);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name.setText(snapshot.child("exnames").getValue().toString());
                   // exp.setText(snapshot.child("stexperience").getValue().toString());
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
                checkout.setImage(R.drawable.rzp_logo);
                JSONObject object = new JSONObject();
                try {
                    object.put("name","Aadishakti");
                    object.put("description","payment for"+selection);
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",200);
                    object.put("prefill.contact","9711445734");
                    object.put("prefill.email","apoorvagarg9148@gmail.com");
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
        builder.setMessage(s);
        builder.show();
    }
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "payment failed"+s, Toast.LENGTH_SHORT).show();
    }
}
