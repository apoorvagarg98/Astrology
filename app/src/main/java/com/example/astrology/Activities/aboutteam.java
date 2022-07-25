package com.example.astrology.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.astrology.R;

public class aboutteam extends AppCompatActivity {
    ImageButton acharyabtn,atuldevbtn,apoorvabtn,durjoybtn,shivambtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutteam);
        acharyabtn=findViewById(R.id.acharyalnk);
        atuldevbtn=findViewById(R.id.atuldevlnk);
        apoorvabtn=findViewById(R.id.apoorvalnk);
        durjoybtn=findViewById(R.id.durjoylnk);
        shivambtn=findViewById(R.id.shivamlnk);
        acharyabtn.setOnClickListener(v->openacharyaln());
        atuldevbtn.setOnClickListener(v->openatullnk());
        apoorvabtn.setOnClickListener(v->openapoorvalnk());
        durjoybtn.setOnClickListener(v->opendurjoylnk());
        shivambtn.setOnClickListener(v->openshivamlnk());

    }



    private void opendurjoylnk() {
        String theurl = "https://www.linkedin.com/in/durjoy-barua-8240671ab";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openapoorvalnk() {
        String theurl = "https://www.linkedin.com/in/apoorva-garg-23a9bb205";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openatullnk() {
        String theurl = "https://www.linkedin.com/in/atuldev-arora-541070183";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openacharyaln() {
        String theurl = "https://www.linkedin.com/company/rudraksha-welfare-foundation/";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openshivamlnk() {
        String theurl = "https://www.linkedin.com/in/shivam-kumar-singh-a87189208";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }
}