package com.example.astrology;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class aboutteam extends Fragment {
    ImageButton acharyabtn,atuldevbtn,prashantbtn,apoorvabtn,durjoybtn,shivambtn;
    public aboutteam() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.activity_aboutteam, container, false);
        acharyabtn=rootview.findViewById(R.id.acharyalnk);
        atuldevbtn=rootview.findViewById(R.id.atuldevlnk);
        prashantbtn=rootview.findViewById(R.id.prashantlnk);
        apoorvabtn=rootview.findViewById(R.id.apoorvalnk);
        durjoybtn=rootview.findViewById(R.id.durjoylnk);
        shivambtn=rootview.findViewById(R.id.shivamlnk);
        acharyabtn.setOnClickListener(v->openacharyaln());
        atuldevbtn.setOnClickListener(v->openatullnk());
        prashantbtn.setOnClickListener(v->openprashantlnk());
        apoorvabtn.setOnClickListener(v->openradhikalnk());
        durjoybtn.setOnClickListener(v->openchetnlnk());
        shivambtn.setOnClickListener(v->openhasrahlnk());
        return rootview;
    }

    private void openprashantlnk() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openchetnlnk() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openradhikalnk() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openatullnk() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openacharyaln() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

    private void openhasrahlnk() {
        String theurl = "http://google.com";
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }
}