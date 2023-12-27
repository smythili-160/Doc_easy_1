package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class doc_home_page extends AppCompatActivity {
    ImageView doc_profile;
    Button doc_logout_button;
    FirebaseAuth dAuth;
    CardView myAppointments;
    TextView welcome_username;
    FirebaseFirestore doc_user;
    String userID;
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
        welcome_username = findViewById(R.id.welcome_username);
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
        userID=dAuth.getCurrentUser().getUid();

        DocumentReference documentReference=doc_user.collection("doc_user").document(userID);
        documentReference.addSnapshotListener(doc_home_page.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome_username.setText(documentSnapshot.getString("username"));
            }
        });



    }
}