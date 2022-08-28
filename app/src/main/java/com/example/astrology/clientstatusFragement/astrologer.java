package com.example.astrology.clientstatusFragement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.astrology.Activities.acceptordeclinepage;
import com.example.astrology.Notifications.Token;
import com.example.astrology.R;
import com.example.astrology.models.requestModel;
import com.example.astrology.viewHollders.item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


public class astrologer extends Fragment {
    //all the fragements in the expert login will show in this directory
    public RecyclerView recyclerView;
    public FirebaseUser expertuser;
    public DatabaseReference client;
    FirebaseRecyclerAdapter<requestModel, item> adapter;
    FirebaseRecyclerOptions<requestModel> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_acceptedreq, container, false);
        recyclerView = view.findViewById(R.id.acpreq);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expertuser = FirebaseAuth.getInstance().getCurrentUser();
        client = FirebaseDatabase.getInstance().getReference("request").child(expertuser.getUid());
        updateToken(FirebaseInstanceId.getInstance().getToken());
        loadParticipant();
        return view;
    }
    private void updateToken(String token)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        ref.child(expertuser.getUid()).setValue(token1);
    }

    private void loadParticipant() {



        options = new FirebaseRecyclerOptions.Builder<requestModel>().setQuery(client,requestModel.class).build();
        adapter =  new FirebaseRecyclerAdapter<requestModel,item>(options){
            @NonNull
            @Override
            public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);

                return new item(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull item holder, @SuppressLint("RecyclerView") int position, @NonNull requestModel model) {
                if(model.getBookedYouFor().equals("Astrologer")) {
                    holder.expertname.setText(model.getName());
                    holder.ratepermin.setText(model.getDateOfBooking());
                    holder.experience.setText(model.getTotalAmount() + " rs");
                    holder.bla.setText("view Request");

                    holder.book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), acceptordeclinepage.class);
                            intent.putExtra("userid", getRef(position).getKey().toString());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }


            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }


}