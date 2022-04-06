package com.example.astrology;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import android.widget.Toast;


public class ExpertSignUp2 extends AppCompatActivity {
   Spinner spinner2;
    FloatingActionButton Exsignuppage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_sign_up2);
        addItemsOnSpinner2();
        addListenerOnButton();

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
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
    }


    // get the selected dropdown list value
    public void addListenerOnButton() {


        spinner2 = (Spinner) findViewById(R.id.spinner2);
        Exsignuppage3 = (FloatingActionButton) findViewById(R.id.Exsignuppage3);

        Exsignuppage3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(ExpertSignUp2.this,
                        "OnClickListener : " +

                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}