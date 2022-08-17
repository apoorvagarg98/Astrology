package com.example.astrology.astrologerFragementDirectory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.astrology.Notifications.Token;
import com.example.astrology.Activities.hinduritualsbookingpage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astrology.R;
import com.example.astrology.viewHollders.item;
import com.example.astrology.models.expertModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


public class hinduRituals extends Fragment {
    public RecyclerView recyclerView;
    public FirebaseUser user;
    public DatabaseReference expert;
    FirebaseRecyclerAdapter<expertModel, item> adapter;
    FirebaseRecyclerOptions<expertModel> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hindu_rituals, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.userhindurituals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        expert = FirebaseDatabase.getInstance().getReference("Experts").child("Hindu Rituals");

        loadParticipant();

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        ref.child(user.getUid()).setValue(token1);
    }

    private void loadParticipant() {



        options = new FirebaseRecyclerOptions.Builder<expertModel>().setQuery(expert,expertModel.class).build();
        adapter =  new FirebaseRecyclerAdapter<expertModel,item>(options){
            @NonNull
            @Override
            public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);

                return new item(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull item holder, @SuppressLint("RecyclerView") int position, @NonNull expertModel model) {

                holder.expertname.setText(model.getExnames());
                holder.ratepermin.setText(model.getStamt()+ " rs/min");
                holder.experience.setText(model.getExperience()+ " yrs");
                holder.bla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), hinduritualsbookingpage.class);
                        intent.putExtra("expertid",getRef(position).getKey().toString());
                        intent.putExtra("selection","Hindu Rituals");
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}