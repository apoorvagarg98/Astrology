package com.example.astrology;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStateManagerControl;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class LoginAdapter extends FragmentStateAdapter {
    private Context context;
    private String[] titles = new String[]{"Login","Sign Up"};
    int totalTabs;
    public LoginAdapter(FragmentActivity fm){
        super(fm);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment= new LoginTabFragment();
                return loginTabFragment;
                case 1:
                SignupTabFragment signupTabFragment= new SignupTabFragment();
                return signupTabFragment;

            default:
                LoginTabFragment loginTabFragmen= new LoginTabFragment();
                return loginTabFragmen;
        }
    }

    @Override
    public int getItemCount() {
       return titles.length;
    }
}
