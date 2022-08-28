package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.R;
import com.example.astrology.models.expertModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ExpertProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//exp = experience,dob = date of birth,amt = amount
    TextView name, email,pin,address,exp,dob,amt,expertise;


    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public DatabaseReference exprofile,dekhte;
    String expertuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);


        name = findViewById(R.id.exprname);
        email= findViewById(R.id.expremail);
        pin= findViewById(R.id.exprpincode);
        address=findViewById(R.id.expraddress);
        exp=findViewById(R.id.exprexperience);
        dob=findViewById(R.id.exprdob);
        amt=findViewById(R.id.expramt);
        expertise=findViewById(R.id.exprexperty);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        expertuid = user.getUid();
        String[] onesel = new String[0];

        final String[] finalselection = new String[1];
        Spinner spinner = findViewById(R.id.expryesno);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.request, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        dekhte = FirebaseDatabase.getInstance().getReference().child("Experts");
        dekhte.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i=0; i <= 10;i++){
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){


                onesel[i]=childSnapshot.getKey();
                if(childSnapshot.child(onesel[i]).child(expertuid).exists())
                {
                    finalselection[0] = onesel[i];
                }
            }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        exprofile = FirebaseDatabase.getInstance().getReference().child("Experts").child(finalselection[0]).child(expertuid);

        exprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    expertModel gandu = snapshot.getValue(expertModel.class);
                    name.setText("Name - " +gandu.getExnames());
                    dob.setText("Date Of Birth -"+ gandu.getExbirthdate());
                    email.setText("Expert Email id -"+gandu.exemails);
                    address.setText("Expert address - "+gandu.exadress);
                    pin.setText("Pincode - "+gandu.expincode);
                    expertise.setText("Expertize - "+gandu.selection);
                    amt.setText("Amount to be Paid - "+ gandu.stamt);
                    exp.setText("Experience (in years) - " +gandu.stexperience);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String text = adapterView.getItemAtPosition(i).toString();
        HashMap hashMap = new HashMap();
        hashMap.put("expertStatus",text);


        exprofile.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ExpertProfile.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}