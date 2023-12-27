package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RecLoginPage extends AppCompatActivity {
    EditText rec_login_email,rec_login_password;
    Button rec_login_button,rec_signupRedirectText;
    ImageView backbutton;
    FirebaseAuth fAuth;
    ProgressBar progressBar2;
    private void startMainActivity(FirebaseUser user) {
        Intent intent = new Intent(RecLoginPage.this, RecHomePage.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_login_page);
        rec_login_email = findViewById(R.id.rec_login_email);
        rec_login_password = findViewById(R.id.rec_login_password);
        rec_login_button = findViewById(R.id.rec_login_button);
        rec_signupRedirectText = findViewById(R.id.rec_signupRedirectText);
        progressBar2=findViewById(R.id.progressBar2);
        backbutton=findViewById(R.id.backbutton);
        fAuth=FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in, open MainActivity
            startMainActivity(user);
            return; // Finish the current activity to prevent going back to it
        }

        rec_signupRedirectText.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          Intent intent = new Intent(RecLoginPage.this, RecSignupPage.class);
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
                        Toast.makeText( RecLoginPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                }
                    progressBar2.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(rec_email, rec_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), RecHomePage.class));
                                    Toast.makeText(RecLoginPage.this, "login successful", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = RecLoginPage.this.getSharedPreferences("app", MODE_PRIVATE );
                                    sharedPreferences.edit().putString("role", "receptionist").commit();
                                    finish();
                                } else {
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RecLoginPage.this, "login unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecLoginPage.this, UserType.class);
                startActivity(intent);
                finish();
            }
        });

    }
}