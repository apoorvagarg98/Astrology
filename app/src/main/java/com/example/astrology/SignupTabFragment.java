package com.example.astrology;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText emaill,passs,mobilee,confirmpass;
    Button signup;
    float v=0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container,  false);
        emaill =root.findViewById(R.id.emaill);
        passs =root.findViewById(R.id.passs);
        mobilee =root.findViewById(R.id.mobilee);
        confirmpass = root.findViewById(R.id.confirmpasss);
        signup = root.findViewById(R.id.signup);

        emaill.setTranslationY(300);
        passs.setTranslationY(300);
        confirmpass.setTranslationY(300);
        signup.setTranslationY(300);
        mobilee.setTranslationY(300);

        emaill.setAlpha(v);
        passs.setAlpha(v);
        confirmpass.setAlpha(v);
        signup.setAlpha(v);
        mobilee.setAlpha(v);

        emaill.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        passs.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        confirmpass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        mobilee.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        return root;
    }
}


