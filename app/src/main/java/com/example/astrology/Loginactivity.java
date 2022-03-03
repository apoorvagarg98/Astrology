package com.example.astrology;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Loginactivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private String[] titles = new String[]{"Login","Sign up"};
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        tabLayout =findViewById(R.id.tab_layout);
        viewPager =findViewById(R.id.view_pager);




        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final LoginAdapter adapter= new LoginAdapter( this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager,((tab, position) -> tab.setText(titles[position]))).attach();


        tabLayout.setTranslationY(300);



        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
    }
}