package com.example.astrology.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.Adapters.MessageAdapter;
import com.example.astrology.R;
import com.example.astrology.login.LoginActivity;
import com.example.astrology.models.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class chatActivity extends AppCompatActivity  {
TextView timer,nametv;
ImageButton btn_send;
ImageView callbutton;
EditText text_send;
FirebaseUser fuser;
String recieverId, senderId,name,clientId;
MessageAdapter messageAdapter;

List<Chat> mChat;
RecyclerView recyclerView;
String senderRoom,reciverRoom;

private boolean timerRunning = false;
private int duration = 120;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn_send = findViewById(R.id.btn_send);
        callbutton = findViewById(R.id.call);
        text_send = findViewById(R.id.text_send);
        recyclerView = findViewById(R.id.rvChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        timer = findViewById(R.id.Timer);
        nametv = findViewById(R.id.username);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        clientId=getIntent().getStringExtra("userid");
        //recieverId = getIntent().getStringExtra("expertid");
        recieverId = getIntent().getStringExtra("recieverId");
       // String durationOfTimer = getIntent().getStringExtra("Duration of Timer");
        //senderId =getIntent().getStringExtra("senderId");
          senderId =fuser.getUid();

name = getIntent().getStringExtra("name");
nametv.setText(name);


        senderRoom = senderId+recieverId;
        reciverRoom= recieverId+senderId;

        //Toast.makeText(chatActivity.this, "uid-"+ senderId, Toast.LENGTH_LONG).show();
btn_send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String msg = text_send.getText().toString();
        if(!msg.equals(""))
        {
            sendMessage(senderId, recieverId,msg);
        }
        else {
            Toast.makeText(chatActivity.this, "Pls Type a msg to send", Toast.LENGTH_SHORT).show();
        }
        text_send.setText("");
    }
});


/*reference.child(uid).child(clientid).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.child("status").getValue().equals("Accepted"))
        {
            startTimer();
        }
    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
*/
callbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(chatActivity.this, LoginActivity.class);
        intent.putExtra("roomid",clientId);
        intent.putExtra("name",name);
        startActivity(intent);
    }
});

        readMessages(senderId, recieverId);

    }
  private void sendMessage(String sender,String reciever,String message)
  {
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
      /*HashMap hashMap = new HashMap();
      hashMap.put("sender",sender);
      hashMap.put("reciever",reciever);
      hashMap.put("message",message);
      ref.child("Chats").push().setValue(hashMap);*/
      Chat messages = new Chat(sender,reciever,message);

      ref.child("Chats").child(senderRoom).child("message").push()
              .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  ref.child("Chats").child(reciverRoom).child("message").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                              Toast.makeText(chatActivity.this, "message half sent", Toast.LENGTH_SHORT).show();

                          }

                      }
                  });

              }
              else {
                  Toast.makeText(chatActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
              }

          }
      });

  }

  private void readMessages(String myid,String userid)
  {
     /* mChat = new ArrayList<>();
      reference = FirebaseDatabase.getInstance().getReference("Chats");
      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot datasnapshot) {
              if(datasnapshot.exists()){
              mChat.clear();
              for (DataSnapshot snapshot: datasnapshot.getChildren()){
                  Chat chat = snapshot.getValue(Chat.class);

                  if(chat.getReciever().equals(myid) && chat.getSender().equals(userid) || chat.getReciever().equals(userid) && chat.getSender().equals(myid)){
                      mChat.add(chat);
                  }
                  messageAdapter = new MessageAdapter(chatActivity.this,mChat);
                  recyclerView.setAdapter(messageAdapter);


              }}
              else{
                  Toast.makeText(chatActivity.this, "not found", Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

      */


      mChat = new ArrayList<>();
      messageAdapter = new MessageAdapter(chatActivity.this, mChat);

      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).child("message");
      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()) {
                  for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                      Chat chat = dataSnapshot.getValue(Chat.class);
                      mChat.add(chat);
                  }
                  messageAdapter.notifyDataSetChanged();


                  recyclerView.setAdapter(messageAdapter);

              }
          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });



  }

    private void startTimer() {


            CountDownTimer cd =new CountDownTimer(duration*1000 , 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String time =  String.format(Locale.getDefault(),"%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                            final String[] hourMinSec = time.split(":");

                            timer.setText(time);
                        }
                    });

                }
                @Override
                public void onFinish() {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("request");
                    reference.child("status").setValue("completed").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(chatActivity.this, "your time period has ended", Toast.LENGTH_SHORT).show();

                              }
                          }
                     });
                    timerRunning=false;
                 }
            }.start();
       }
}




