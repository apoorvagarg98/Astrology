package com.example.astrology;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText name,dateofbirth,placeofbirth,birthtime,emaill,passs,mobilee,confirmpass;
    Button signup;
    RadioGroup radioGroup;
    float v=0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container,  false);
        emaill =root.findViewById(R.id.emaill);
        name =root.findViewById(R.id.name);
        dateofbirth =root.findViewById(R.id.dateofbirth);
        birthtime =root.findViewById(R.id.birthtime);

        mobilee =root.findViewById(R.id.mobilee);

        signup = root.findViewById(R.id.signup);
        radioGroup = root.findViewById(R.id.radio);
        placeofbirth = root.findViewById(R.id.placeofbirth);

        emaill.setTranslationY(300);

        signup.setTranslationY(300);
        radioGroup.setTranslationY(300);
        name.setTranslationY(300);
        dateofbirth.setTranslationY(300);
        birthtime.setTranslationY(300);
        mobilee.setTranslationY(300);
        placeofbirth.setTranslationY(300);
        radioGroup.setTranslationY(300);

        emaill.setAlpha(v);


        signup.setAlpha(v);
        radioGroup.setAlpha(v);
        dateofbirth.setAlpha(v);
        birthtime.setAlpha(v);
        name.setAlpha(v);
        mobilee.setAlpha(v);
        placeofbirth.setAlpha(v);
        radioGroup.setAlpha(v);



        emaill.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        mobilee.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        placeofbirth.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        dateofbirth.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        birthtime.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        radioGroup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        name.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        return root;
    }
}


