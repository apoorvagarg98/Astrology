package com.example.astrology;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class LoginTabFragment extends Fragment {
    EditText email,pass;
    Button login;
    TextView forgetpass;
    float v=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container,  false);
        email =root.findViewById(R.id.email);
        pass =root.findViewById(R.id.pass);
        forgetpass = root.findViewById(R.id.forgetpass);
        login = root.findViewById(R.id.login);

        email.setTranslationY(300);
        pass.setTranslationY(300);
        forgetpass.setTranslationY(300);
        login.setTranslationY(300);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetpass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        pass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        forgetpass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();



        return root;
    }

}
