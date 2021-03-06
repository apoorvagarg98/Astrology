package com.example.astrology.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.example.astrology.clientstatusFragement.Numerologistexpert;
import com.example.astrology.clientstatusFragement.astrologer;

import com.example.astrology.clientstatusFragement.hinduritualsaexpert;
import com.example.astrology.clientstatusFragement.lalkitabexpert;
import com.example.astrology.clientstatusFragement.mobilenumerologist;
import com.example.astrology.clientstatusFragement.palmist;
import com.example.astrology.clientstatusFragement.tarotcardreader;
import com.example.astrology.clientstatusFragement.vastuexpert;


public class selectClientAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"Astrologer","Numerologist","Vastu Expert","Tarot Card Reader","Lal Kitab Exper","Mobile Numerologist","Palmist","Hindu Rituals"};

    public selectClientAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                astrologer ar = new astrologer();
                return ar;
            case 1:
                Numerologistexpert ne= new Numerologistexpert();
                return ne;

            case 2:
                vastuexpert ve= new vastuexpert();
                return ve;
            case 3:
                tarotcardreader tce= new tarotcardreader();
                return tce;

            case 4:
                lalkitabexpert lke= new lalkitabexpert();
                return lke;
            case 5:
                mobilenumerologist mn= new mobilenumerologist();
                return mn;


            case 6:
                palmist pm= new palmist();
                return pm;

            case 7:
                hinduritualsaexpert hr= new hinduritualsaexpert();
                return hr;








            default:
                astrologer a= new astrologer();
                return a;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
