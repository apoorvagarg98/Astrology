package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.astrology.R;
import com.example.astrology.models.expertModel;
import com.example.astrology.viewHollders.item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//when client logins this page will be shown to hi
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    public RecyclerView recyclerView;
    public FirebaseUser user;
    public DatabaseReference expert;
    FirebaseRecyclerAdapter<expertModel, item> adapter;
    FirebaseRecyclerOptions<expertModel> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.popularastro);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        expert = FirebaseDatabase.getInstance().getReference("Experts").child("Astrologer");


        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this
                ,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        loadParticipant();

        Button astro = findViewById(R.id.astro);
        astro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, astrologer.class));
            }
        });

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
//for image slider
        slideModels.add(new SlideModel(R.drawable.backgroundd));
        slideModels.add(new SlideModel("https://5.imimg.com/data5/ANDROID/Default/2021/2/CL/AS/MJ/48693369/product-jpeg-500x500.jpg", "Astrology"));
        slideModels.add(new SlideModel("https://www.haribhoomi.com/cms/gall_content/2018/3/zodiac_2018032812431854.jpg", "Palmology"));
        slideModels.add(new SlideModel("https://thehindutimes.in/static/c1e/client/89706/migrated/f13b42b0689041873d096a5b3d447a7d.jpg", "Vaastu"));
        slideModels.add(new SlideModel("https://www.pavitrajyotish.com/wp-content/uploads/2015/12/Indian-Vedic-Astrology-Hindi.jpg", "Neumerology"));
        slideModels.add(new SlideModel("https://m.facebook.com/619462814750696/photos/a.619469904749987/1264677913562513/?type=3&source=44", "Tarot Card"));
        imageSlider.setImageList(slideModels, true);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu:
                startActivity(new Intent(MainActivity.this, aboutus.class));
                break;
            case R.id.dashboard_menu:
                startActivity(new Intent(MainActivity.this, faqs.class));
                break;
            case R.id.course_menu:
                startActivity(new Intent(MainActivity.this, legalPolicy.class));
                break;
            case R.id.developers:
                startActivity(new Intent(MainActivity.this, aboutteam.class));
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void loadParticipant() {


        options = new FirebaseRecyclerOptions.Builder<expertModel>().setQuery(expert, expertModel.class).build();
        adapter = new FirebaseRecyclerAdapter<expertModel, item>(options) {
            @NonNull
            @Override
            public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);

                return new item(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull item holder, @SuppressLint("RecyclerView") int position, @NonNull expertModel model) {

                holder.expertname.setText(model.getExnames());
                holder.ratepermin.setText(model.getStamt() + "rs/min");
                holder.experience.setText(model.getExperience() + " yrs");
                holder.bla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, bookingPage.class);
                        intent.putExtra("expertuid", getRef(position).getKey().toString());
                        intent.putExtra("selection", "Astrologer");
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Astrology,Numerologist.....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }*/


}









