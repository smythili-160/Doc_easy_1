package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class rec_signup_page extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText rec_signup_name, rec_signup_username, rec_signup_email, rec_signup_password, rec_phone_number;
    TextView rec_loginRedirectText;
    Button rec_signup_button;
    FirebaseAuth fAuth;
    String userID;
    ProgressBar progressBar;
    FirebaseFirestore rec_user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_signup_page);
        rec_signup_name = findViewById(R.id.rec_signup_name);
        rec_signup_email = findViewById(R.id.rec_signup_email);
        rec_phone_number = findViewById(R.id.rec_phone_number);
        rec_signup_username = findViewById(R.id.rec_signup_username);
        rec_signup_password = findViewById(R.id.rec_signup_password);
        rec_loginRedirectText = findViewById(R.id.rec_loginRedirectText);
        rec_signup_button = findViewById(R.id.rec_signup_button);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        rec_user=FirebaseFirestore.getInstance();
        rec_loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rec_signup_page.this, rec_login_page.class);
                startActivity(intent);
                finish();
            }
        });

        rec_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec_name = rec_signup_name.getText().toString();
                String rec_phoneno = rec_phone_number.getText().toString();
                String rec_email = rec_signup_email.getText().toString().trim();
                String rec_password = rec_signup_password.getText().toString().trim();
                String rec_username = rec_signup_username.getText().toString();

                if(fAuth.getCurrentUser() != null){
                    Intent intent=new Intent(new Intent(getApplicationContext(),rec_home_page.class));
                    startActivity(intent);
                    finish();

                }
                if(rec_password.length()<6){
                    rec_signup_password.setError("Password must have more than 6 characters");
                }
                if(rec_phoneno.length()<10){
                    rec_phone_number.setError("Phone Number must have more than 10 characters");
                }

                if(rec_name.isEmpty() || rec_email.isEmpty() || rec_password.isEmpty() || rec_username.isEmpty() || rec_phoneno.isEmpty() ){
                    Toast.makeText( rec_signup_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(rec_email, rec_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(rec_signup_page.this, "user creation successful", Toast.LENGTH_SHORT).show();
                                    userID=fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=rec_user.collection("rec_user").document(userID);
                                    Map<String,Object> rec_user=new HashMap<>();
                                    rec_user.put("rec_name",rec_name);
                                    rec_user.put("rec_username",rec_username);
                                    rec_user.put("rec_email_address",rec_email);
                                    rec_user.put("rec_phone_no",rec_phoneno);
                                    documentReference.set(rec_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG,"user is created"+userID);

                                        }
                                    });
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent=new Intent(rec_signup_page.this,rec_home_page.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(rec_signup_page.this, "user creation unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });



    }

}