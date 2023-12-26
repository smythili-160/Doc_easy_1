package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class doc_home_page extends AppCompatActivity {
    ImageView doc_profile;
    Button doc_logout_button;
    FirebaseAuth dAuth;
    CardView myAppointments;
    FirebaseFirestore doc_user;
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),doc_login_page.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home_page);
        doc_profile=findViewById(R.id.doc_profile);
        doc_logout_button = findViewById(R.id.doc_logout_button);
        myAppointments = findViewById(R.id.myAppointments);
        dAuth= FirebaseAuth.getInstance();
        doc_user=FirebaseFirestore.getInstance();
        doc_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(doc_home_page.this, doc_prof.class);
                startActivity(i);
            }
        }
        );
        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(doc_home_page.this, MyAppointments.class);

                startActivity(AddNewAppt);
            }
        });



    }
}