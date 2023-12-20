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

public class doc_login_page extends AppCompatActivity {
    EditText doc_login_email,doc_login_password;
    Button doc_login_button,doc_signupRedirectText;
    ImageView backbutton;
    FirebaseAuth dAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login_page);
        doc_login_email = findViewById(R.id.doc_login_email);
        doc_login_password = findViewById(R.id.doc_login_password);
        doc_login_button = findViewById(R.id.doc_login_button);
        doc_signupRedirectText = findViewById(R.id.doc_signupRedirectText);
        backbutton=findViewById(R.id.backbutton);
        dAuth=FirebaseAuth.getInstance();

        doc_signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doc_login_page.this, doc_signup_front_page.class);
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
                    Toast.makeText( doc_login_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                dAuth.signInWithEmailAndPassword(doc_email, doc_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),doc_home_page.class));
                            Toast.makeText(doc_login_page.this, "login successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(doc_login_page.this, "login unsuccessful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doc_login_page.this, user_type.class);
                startActivity(intent);
                finish();
            }
        });

    }
}