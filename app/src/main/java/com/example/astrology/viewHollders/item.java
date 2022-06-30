package com.example.astrology.viewHollders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.astrology.R;

public class item extends RecyclerView.ViewHolder {
    public TextView expertname,ratepermin,experience,bla;
    public ImageButton book;
    public item(@NonNull View itemView) {
        super(itemView);
        expertname = itemView.findViewById(R.id.expertname);
        ratepermin = itemView.findViewById(R.id.expertratePerminute);
        experience = itemView.findViewById(R.id.exp);
        book = itemView.findViewById(R.id.book);
        bla = itemView.findViewById(R.id.bla);

    }
}