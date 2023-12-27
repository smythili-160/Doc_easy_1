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

public class DocLoginPage extends AppCompatActivity {
    EditText doc_login_email,doc_login_password;
    Button doc_login_button,doc_signupRedirectText;
    ImageView backbutton;
    FirebaseAuth dAuth;
    ProgressBar progressBar4;
    private void startMainActivity(FirebaseUser user) {
        Intent intent = new Intent(DocLoginPage.this, DocHomePage.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login_page);
        doc_login_email = findViewById(R.id.doc_login_email);
        doc_login_password = findViewById(R.id.doc_login_password);
        doc_login_button = findViewById(R.id.doc_login_button);
        doc_signupRedirectText = findViewById(R.id.doc_signupRedirectText);
        progressBar4=findViewById(R.id.progressBar4);
        backbutton=findViewById(R.id.backbutton);
        dAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in, open MainActivity
            startMainActivity(user);
            return; // Finish the current activity to prevent going back to it
        }

        doc_signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocLoginPage.this, DocSignupFrontPage.class);
                startActivity(intent);
                finish();
            }
        });
        doc_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc_email = doc_login_email.getText().toString().trim();
                String doc_password = doc_login_password.getText().toString().trim();

                if(doc_password.length()<6){
                    doc_login_password.setError("Password must have more than 6 characters");
                }

                if(doc_email.isEmpty() || doc_password.isEmpty()){
                    Toast.makeText( DocLoginPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar4.setVisibility(View.VISIBLE);
                dAuth.signInWithEmailAndPassword(doc_email, doc_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar4.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(), DocHomePage.class));
                            Toast.makeText(DocLoginPage.this, "login successful", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = DocLoginPage.this.getSharedPreferences("app", MODE_PRIVATE );
                            sharedPreferences.edit().putString("role", "doctor").commit();
                            finish();
                        } else {
                            progressBar4.setVisibility(View.INVISIBLE);
                            Toast.makeText(DocLoginPage.this, "login unsuccessful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocLoginPage.this, UserType.class);
                startActivity(intent);
                finish();
            }
        });

    }
}