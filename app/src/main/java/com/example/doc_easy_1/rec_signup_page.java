package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class rec_signup_page extends AppCompatActivity {
    EditText rec_signup_name, rec_signup_username, rec_signup_email, rec_signup_password;
    TextView rec_loginRedirectText;
    Button rec_signup_button;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_signup_page);
        rec_signup_name = findViewById(R.id.rec_signup_name);
        rec_signup_email = findViewById(R.id.rec_signup_email);
        rec_signup_username = findViewById(R.id.rec_signup_username);
        rec_signup_password = findViewById(R.id.rec_signup_password);
        rec_loginRedirectText = findViewById(R.id.rec_loginRedirectText);
        rec_signup_button = findViewById(R.id.rec_signup_button);
        fAuth=FirebaseAuth.getInstance();
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

                if(rec_name.isEmpty() || rec_email.isEmpty() || rec_password.isEmpty() || rec_username.isEmpty()){
                    Toast.makeText( rec_signup_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(rec_email, rec_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(rec_signup_page.this, "user creation successful", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(rec_signup_page.this,rec_home_page.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(rec_signup_page.this, "user creation unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });



    }

}