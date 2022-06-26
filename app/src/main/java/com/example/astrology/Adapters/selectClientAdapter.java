package com.example.astrology.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.astrology.clientstatusFragement.hinduritualsaexpert;


public class selectClientAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"pending","Accepted","Paid","Completed"};

    public selectClientAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                pendingRequests pr = new pendingRequests();
                return pr;
            case 1:
                hinduritualsaexpert ar= new hinduritualsaexpert();
                return ar;
            case 2:
                 paidreq paidr= new paidreq();
                return paidr;
            case 3:
                completedreq cr= new completedreq();
                return cr;


            default:
                pendingRequests p= new pendingRequests();
                return p;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
