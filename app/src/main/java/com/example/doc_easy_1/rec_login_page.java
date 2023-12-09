package com.example.doc_easy_1;

import static com.example.doc_easy_1.R.id.backbutton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class rec_login_page extends AppCompatActivity {
    EditText rec_login_email,rec_login_password;
    Button rec_login_button,rec_signupRedirectText;
    ImageView backbutton;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_login_page);
        rec_login_email = findViewById(R.id.rec_login_email);
        rec_login_password = findViewById(R.id.rec_login_password);
        rec_login_button = findViewById(R.id.rec_login_button);
        rec_signupRedirectText = findViewById(R.id.rec_signupRedirectText);
        backbutton=findViewById(R.id.backbutton);
        fAuth=FirebaseAuth.getInstance();

        rec_signupRedirectText.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          Intent intent = new Intent(rec_login_page.this, rec_signup_page.class);
                                                          startActivity(intent);
                                                          finish();
                                                      }
                                                  });
            rec_login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String rec_email = rec_login_email.getText().toString().trim();
                    String rec_password = rec_login_password.getText().toString().trim();

                    if(rec_password.length()<6){
                        rec_login_password.setError("Password must have more than 6 characters");
                    }

                    if(rec_email.isEmpty() || rec_password.isEmpty()){
                        Toast.makeText( rec_login_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                }
                fAuth.signInWithEmailAndPassword(rec_email, rec_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),rec_home_page.class));
                                    Toast.makeText(rec_login_page.this, "login successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(rec_login_page.this, "login unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rec_login_page.this, user_type.class);
                startActivity(intent);
                finish();
            }
        });

    }
}