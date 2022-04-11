package com.example.astrology.Adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.astrology.astrologerFragementDirectory.astrologerfragement;
import com.example.astrology.astrologerFragementDirectory.hinduRituals;
import com.example.astrology.astrologerFragementDirectory.lalkitabexpert;
import com.example.astrology.astrologerFragementDirectory.mobilenumerologist;
import com.example.astrology.astrologerFragementDirectory.numerologist;
import com.example.astrology.astrologerFragementDirectory.palmist;
import com.example.astrology.astrologerFragementDirectory.tarotcardreader;
import com.example.astrology.astrologerFragementDirectory.vastuexpert;

public class astrologerAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"astrology","laalKitaab","HinduRituals","MobileNumerology","Numerology","palmistory ","tarotCardReader","VastuExpert"};

    public astrologerAdapter (FragmentActivity fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                astrologerfragement as = new astrologerfragement();
                return as;
            case 1:
               lalkitabexpert le= new lalkitabexpert();
                return le;
            case 2:
              hinduRituals hr= new hinduRituals();
                return hr;
            case 3:
               mobilenumerologist mn= new mobilenumerologist();
                return mn;

            case 4:
                numerologist n= new numerologist();
                return n;

                case 5:
                palmist p= new palmist();
                return p;

            case 6:
                tarotcardreader tcr= new tarotcardreader();
                return tcr;

            case 7:
                vastuexpert ve= new vastuexpert();
                return ve;

                default:
                astrologerfragement a= new astrologerfragement();
                return a;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

}
