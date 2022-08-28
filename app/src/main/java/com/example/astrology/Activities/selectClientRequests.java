package com.example.astrology.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.astrology.Adapters.selectClientAdapter;
import com.example.astrology.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
public class selectClientRequests extends AppCompatActivity {
    //when experts login they will see this page
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ImageButton profile;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public DatabaseReference exprofile;
    String expertuid;
    private String[] titles = new String[]{"Astrologer","Numerologist","Vastu Expert","Tarot Card Reader","Lal Kitab Expert","Mobile Numerologist","Palmist","Hindu Rituals"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client_requests);
        tabLayout =findViewById(R.id.tl);
        viewPager2=findViewById(R.id.vp);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final selectClientAdapter adapter= new selectClientAdapter( this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
        profile = findViewById(R.id.openProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(selectClientRequests.this, ExpertProfile.class)); }});}}
