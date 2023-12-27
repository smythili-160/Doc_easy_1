package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class UserType extends AppCompatActivity {

    Button doctor,receptionist;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        doctor = findViewById(R.id.doctor);
        receptionist= findViewById(R.id.receptionist);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserType.this, DocLoginPage.class);
                startActivity(i);
                finish();


            }
        });
                receptionist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(UserType.this, RecLoginPage.class);
                        startActivity(i);
                        finish();


                    }
                }
        );

    }
}