package com.example.astrology.Adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.astrology.astrologerFragementDirectory.Home;
import com.example.astrology.astrologerFragementDirectory.careerFragement;

public class astrologerAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"home","love","career","health","studies","foriegn travel"};

    public astrologerAdapter (FragmentActivity fm){
        super(fm);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                Home home= new Home();
                return home;
            case 1:
               careerFragement cf= new careerFragement();
                return cf;

            default:
                Home homee= new Home();
                return homee;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

}
