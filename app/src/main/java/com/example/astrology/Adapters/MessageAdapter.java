package com.example.astrology.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astrology.R;
import com.example.astrology.models.Chat;
import com.example.astrology.models.expertModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    int ITEM_SEND = 1;
    int ITEM_RECIVE = 2;
    private List<Chat> mChat;

    private Context mContext;
    private FirebaseAuth firebaseAuth;
    public MessageAdapter(Context mContext, List<Chat> mChat)
    {
        this.mChat = mChat;
        this.mContext = mContext;
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_itemleft, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }*/

        if(viewType == ITEM_SEND) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_right, parent, false);
            return new MessageAdapter.SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_itemleft, parent, false);
            return new MessageAdapter.ReciverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

Chat chat = mChat.get(position);
if(holder.getClass()==SenderViewHolder.class)
{
SenderViewHolder viewHolder = (SenderViewHolder) holder;
viewHolder.show_message.setText(chat.getMessage());
}
else {
     ReciverViewHolder viewHolder = (ReciverViewHolder) holder;
    viewHolder.show_message.setText(chat.getMessage());
}




    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message ;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.tvSentMessage);

        }
    }
    public class ReciverViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message ;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {

        Chat chat = mChat.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chat.getSenderId()))
        {
            return ITEM_SEND;
        }
        else {
            return ITEM_RECIVE;
        }

     /*  if(mChat.get(position).getSender().equals(firebaseAuth.getUid())){
           return MSG_TYPE_RIGHT;
       }
       else
           return MSG_TYPE_LEFT;
}*/
}}








