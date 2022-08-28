package com.example.astrology.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.example.astrology.Adapters.astrologerAdapter;
import com.example.astrology.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class astrologer extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
//tablayout at client side
    private String[] titles = new String[]{"astrology","laalKitaab","HinduRituals","MobileNumerology","Numerology","palmistory ","tarotCardReader","VastuExpert"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astrologer);
        tabLayout =findViewById(R.id.tabs);
        viewPager2 =findViewById(R.id.viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final astrologerAdapter adapter= new astrologerAdapter( this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();


    }
}

