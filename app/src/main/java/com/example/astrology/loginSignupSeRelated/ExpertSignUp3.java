package com.example.astrology.loginSignupSeRelated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.astrology.R;
import com.example.astrology.models.expertModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpertSignUp3 extends AppCompatActivity {
    public static  final int REQUEST_CODEcerti= 101;
    public static  final int REQUEST_CODEadh= 105;
    public static  final int REQUEST_CODEpan= 110;
    public static ImageView certi,adh,pancard;

    public static Uri imageUricertificate,imageUriadhar,imageUripan;
    StorageReference postImageRef;
    String confirmpasss,exnames,exmobile, exemails,userId ,gender ,selection ,exadress ,expincode ,exbirthdate,stamt,stpass,exabtyrslf;
    EditText amt,password,experience,confirmpass;
    int iexperience;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FloatingActionButton signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_sign_up3);


        exnames   =getIntent().getStringExtra("exname2").toString();
        exmobile  =getIntent().getStringExtra("exmobile2").toString();
        exemails  =getIntent().getStringExtra("exemail2").toString().trim();
        gender    =getIntent().getStringExtra("gender");
        selection =getIntent().getStringExtra("selection").toString();
        exadress  =getIntent().getStringExtra("exadress").toString();
        expincode =getIntent().getStringExtra("expincode").toString();
        exbirthdate =getIntent().getStringExtra("exbirthdate").toString();
        confirmpasss= getIntent().getStringExtra("confirmpass").toString();
        exabtyrslf =getIntent().getStringExtra("exabtyrslf").toString();
        postImageRef = FirebaseStorage.getInstance().getReference().child("postImages");

        amt = findViewById(R.id.amount);
        experience = findViewById(R.id.experience);
        password = findViewById(R.id.expassword);
        signup = findViewById(R.id.Exsignuppage3);
        certi = findViewById(R.id.certi);
        adh = findViewById(R.id.adh);
        pancard = findViewById(R.id.pancard);
        confirmpass = findViewById(R.id.exconfirmpassword);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , REQUEST_CODEcerti);
            }
        });


        adh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , REQUEST_CODEadh);
            }
        });


        pancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , REQUEST_CODEpan);
            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          registerExpert();
                                      }
                                  }
        );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (requestCode == REQUEST_CODEcerti || requestCode == RESULT_OK && data != null) {

            imageUricertificate = data.getData();
            certi.setImageURI(imageUricertificate);
        }
       else {
           Toast.makeText(ExpertSignUp3.this, "please select your certificate", Toast.LENGTH_SHORT).show();
       }

        if(requestCode==REQUEST_CODEadh || requestCode==RESULT_OK && data!=null)
        {
            imageUriadhar = data.getData();
            adh.setImageURI(imageUriadhar);
        }
        else {
            Toast.makeText(ExpertSignUp3.this, "please select your adhar card", Toast.LENGTH_SHORT).show();
        }

        if(requestCode==REQUEST_CODEpan || requestCode==RESULT_OK && data!=null)
        {
            imageUripan = data.getData();
            pancard.setImageURI(imageUripan);
        }
        else {
            Toast.makeText(ExpertSignUp3.this, "please select your pan card", Toast.LENGTH_SHORT).show();
        }





    }


    private void registerExpert() {

        stamt = amt.getText().toString();
        stpass= password.getText().toString();
        String stexperience = experience.getText().toString();

        if (iexperience < 18) {
            experience.setError("Experience more than 3 is allowed only");
            experience.requestFocus();
        }
        else if (stpass != confirmpasss) {
            password.setError("Password and confirm password do not match. Try Again!");
        }
        else {


            mAuth = FirebaseAuth.getInstance();

            Date date = new Date();
            @SuppressLint({"NewApi", "LocalSuppress"}) SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
            String strDate = formatter.format(date);

            DatabaseReference expert = FirebaseDatabase.getInstance().getReference("Experts");


            mAuth.createUserWithEmailAndPassword(exemails.trim(), stpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        expertModel em = new expertModel(exnames, exmobile, exemails, userId, selection, exadress, expincode, exbirthdate, stamt, gender, imageUriadhar.toString(), imageUripan.toString(), imageUricertificate.toString(), stexperience.toString(), exabtyrslf);

                        expert.child(selection).child(mAuth.getUid()).setValue(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ExpertSignUp3.this, "Expert Registered succesfully, Please Login", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(ExpertSignUp3.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            postImageRef.child("certificates").child(exnames + user.getUid()).putFile(imageUricertificate).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        postImageRef.child("certificates").child(exnames + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(ExpertSignUp3.this, "certificate uploaded successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });


            postImageRef.child("Adhar card").child(exnames + user.getUid()).putFile(imageUriadhar).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        postImageRef.child("Adhar card").child(exnames + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(ExpertSignUp3.this, "Adhar card uploaded successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });

            postImageRef.child("pan card").child(exnames + user.getUid()).putFile(imageUripan).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        postImageRef.child("pan card").child(exnames + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(ExpertSignUp3.this, "pan card uploaded successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });

        }




    }
}