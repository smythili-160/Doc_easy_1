package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class user_type extends AppCompatActivity {

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
                Intent i = new Intent(user_type.this, doc_login_page.class);
                startActivity(i);
                finish();


            }
        });
                receptionist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(user_type.this, rec_login_page.class);
                        startActivity(i);
                        finish();


                    }
                }
        );

    }
}