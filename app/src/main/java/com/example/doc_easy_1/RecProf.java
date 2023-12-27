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

public class RecProf extends AppCompatActivity {
    FirebaseAuth fAuth;
    String userID;
    ImageView back_button;
    FirebaseFirestore rec_user;
    EditText rec_name,rec_email,rec_username,rec_phone_number;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_prof);
        rec_name = findViewById(R.id.rec_name);
        rec_email = findViewById(R.id.rec_email);
        rec_phone_number = findViewById(R.id.rec_phone_number);
        rec_username = findViewById(R.id.rec_username);
        back_button=findViewById(R.id.back_button);
        fAuth= FirebaseAuth.getInstance();
        rec_user=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=rec_user.collection("rec_user").document(userID);
        documentReference.addSnapshotListener(RecProf.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                rec_name.setText(documentSnapshot.getString("rec_name"));
                rec_email.setText(documentSnapshot.getString("rec_email_address"));
                rec_username.setText(documentSnapshot.getString("rec_username"));
                rec_phone_number.setText(documentSnapshot.getString("rec_phone_no"));
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecProf.this, RecHomePage.class);
                startActivity(intent);
                finish();
            }
        });


    }
}