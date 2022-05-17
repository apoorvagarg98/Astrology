package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrology.Adapters.astrologerAdapter;
import com.example.astrology.Adapters.selectClientAdapter;
import com.example.astrology.Notifications.Token;
import com.example.astrology.R;
import com.example.astrology.models.expertModel;
import com.example.astrology.models.requestModel;
import com.example.astrology.models.userModel;
import com.example.astrology.viewHollders.item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class selectClientRequests extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles = new String[]{"pending","Accepted","Paid","Completed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client_requests);
        tabLayout =findViewById(R.id.tl);
        viewPager2 =findViewById(R.id.vp);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final selectClientAdapter adapter= new selectClientAdapter( this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();



    }

}
