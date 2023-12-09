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

public class doc_signup_page extends AppCompatActivity {
    EditText doc_signup_name, doc_signup_username, doc_signup_email, doc_signup_password;
    Button doc_signup_button,doc_loginRedirectText;
    FirebaseAuth dAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_signup_page);
        doc_signup_name = findViewById(R.id.doc_signup_name);
        doc_signup_email = findViewById(R.id.doc_signup_email);
        doc_signup_username = findViewById(R.id.doc_signup_username);
        doc_signup_password = findViewById(R.id.doc_signup_password);
        doc_loginRedirectText = findViewById(R.id.doc_loginRedirectText);
        doc_signup_button = findViewById(R.id.doc_signup_button);
        dAuth=FirebaseAuth.getInstance();
        doc_loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doc_signup_page.this, doc_login_page.class);
                startActivity(intent);
                finish();
            }
        });

        doc_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc_name = doc_signup_name.getText().toString();
                String doc_email = doc_signup_email.getText().toString().trim();
                String doc_password = doc_signup_password.getText().toString().trim();
                String doc_username = doc_signup_username.getText().toString();

                if(dAuth.getCurrentUser() != null){
                    Intent intent=new Intent(new Intent(getApplicationContext(),doc_home_page.class));
                    startActivity(intent);
                    finish();

                }
                if(doc_password.length()<6){
                    doc_signup_password.setError("Password must have more than 6 characters");
                }

                if(doc_name.isEmpty() || doc_email.isEmpty() || doc_password.isEmpty() || doc_username.isEmpty()){
                    Toast.makeText( doc_signup_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                dAuth.createUserWithEmailAndPassword(doc_email, doc_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(doc_signup_page.this, "user creation successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(doc_signup_page.this,doc_home_page.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(doc_signup_page.this, "user creation unsuccessful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });



    }

}