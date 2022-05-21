package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.astrology.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;


public class ExpertSignUp2 extends AppCompatActivity {
   Spinner spinner2;
   FloatingActionButton Exsignuppage3;

    EditText dateofbirth,pincode,exadress,exabtyr;
    RadioGroup radioGroup;
    String gender,exnames,exphones,exemails,selection;
    FloatingActionButton signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_sign_up2);


        dateofbirth =findViewById(R.id.exBirthdate);

        exadress =findViewById(R.id.exadress);
        signup = findViewById(R.id.Exsignuppage3);
        radioGroup = findViewById(R.id.exradio);
        pincode = findViewById(R.id.exabtyr);
        exabtyr = findViewById(R.id.exabtyrslf);

        exnames = getIntent().getStringExtra("exname").toString();
        exphones =getIntent().getStringExtra("exmobile").toString();
        exemails =getIntent().getStringExtra("exemail").toString().trim();

        addItemsOnSpinner2();
        addListenerOnButton();

        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                                  switch (i){
                                                      case R.id.male:
                                                          gender = "male";
                                                          break;
                                                      case  R.id.female:
                                                          gender = "female";
                                                          break;


                                                  }
                                              }
                                          });
                                          selection = String.valueOf(spinner2.getSelectedItem());
                                          Intent intent =  new Intent(ExpertSignUp2.this,ExpertSignUp3.class);
                                          intent.putExtra("exname2",exnames);
                                          intent.putExtra("exemail2",exemails);
                                          intent.putExtra("exmobile2",exphones);
                                          intent.putExtra("gender",gender);
                                          intent.putExtra("selection",selection);
                                          intent.putExtra("exadress",exadress.getText().toString());
                                          intent.putExtra("expincode",pincode.getText().toString());
                                          intent.putExtra("exabtyrslf",exabtyr.getText().toString());
                                          intent.putExtra("exbirthdate",dateofbirth.getText().toString());
                                          startActivity(intent);
                                      }
                                  }
        );


    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.exspinner2);
        List<String> list = new ArrayList<String>();
        list.add("Astrologer");
        list.add("Numerologist");
        list.add("Vastu Expert");
        list.add("Tarot Card Reader");
        list.add("Lal Kitab Expert");
        list.add("Mobile Numerologist");
        list.add("Palmist");
        list.add("Hindu Rituals");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        selection = String.valueOf(spinner2.getSelectedItem());
    }


    // get the selected dropdown list value
    public void addListenerOnButton() {


        spinner2 = (Spinner) findViewById(R.id.exspinner2);
        Exsignuppage3 = (FloatingActionButton) findViewById(R.id.Exsignuppage3);

        Exsignuppage3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
selection = String.valueOf(spinner2.getSelectedItem());

            }

        });
    }


}