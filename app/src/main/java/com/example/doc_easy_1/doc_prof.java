package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class doc_prof extends AppCompatActivity {
    FirebaseAuth dAuth;
    String userID;

    FirebaseFirestore doc_user;
    EditText doc_name,doc_email,doc_username,doc_phone_number,doc_speciality,doc_exp,doc_Time_Slots;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prof);
        doc_name = findViewById(R.id.doc_name);
        doc_email = findViewById(R.id.doc_email);
        doc_phone_number = findViewById(R.id.doc_phone_number);
        doc_username = findViewById(R.id.doc_username);
        doc_speciality=findViewById(R.id.doc_speciality);
        doc_exp=findViewById(R.id.doc_exp);
        doc_Time_Slots=findViewById(R.id.doc_Time_Slots);

        dAuth= FirebaseAuth.getInstance();
        doc_user=FirebaseFirestore.getInstance();
        if(getIntent().getExtras() != null && getIntent().getExtras().getString("doctorID")!= null) {
           userID = getIntent().getExtras().getString("documentId");
        } else {
            userID=dAuth.getCurrentUser().getUid();
        }


        DocumentReference documentReference=doc_user.collection("doc_user").document(userID);
        documentReference.addSnapshotListener(doc_prof.this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                doc_name.setText(documentSnapshot.getString("name"));
                doc_email.setText(documentSnapshot.getString("doc_email_address"));
                doc_username.setText(documentSnapshot.getString("username"));
                doc_phone_number.setText(documentSnapshot.getString("phoneNumber"));
                doc_speciality.setText(documentSnapshot.getString("speciality"));
                doc_exp.setText(documentSnapshot.getString("Experience"));
                doc_Time_Slots.setText(documentSnapshot.getString("TimeSlots"));

            }
        });


    }
}