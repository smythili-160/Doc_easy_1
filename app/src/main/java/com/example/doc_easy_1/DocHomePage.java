package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class DocHomePage extends AppCompatActivity {
    ImageView doc_profile;
    Button doc_logout_button;
    FirebaseAuth dAuth;
    CardView myAppointments,apply_leave;
    TextView welcome_username;
    FirebaseFirestore doc_user;
    String userID;
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPreferences = DocHomePage.this.getPreferences( MODE_PRIVATE);
        sharedPreferences.edit().remove("role").commit();
        startActivity(new Intent(getApplicationContext(), DocLoginPage.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home_page);
        doc_profile=findViewById(R.id.doc_profile);
        doc_logout_button = findViewById(R.id.doc_logout_button);
        myAppointments = findViewById(R.id.myAppointments);
        apply_leave=findViewById(R.id.apply_leave);
        dAuth= FirebaseAuth.getInstance();
        doc_user=FirebaseFirestore.getInstance();
        welcome_username = findViewById(R.id.welcome_username);
        doc_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocHomePage.this, DocProf.class);
                startActivity(i);
            }
        }
        );
        myAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(DocHomePage.this, MyAppointments.class);

                startActivity(AddNewAppt);
            }
        });
        userID=dAuth.getCurrentUser().getUid();

        DocumentReference documentReference=doc_user.collection("doc_user").document(userID);
        documentReference.addSnapshotListener(DocHomePage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome_username.setText(documentSnapshot.getString("username"));
            }
        });
        apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(DocHomePage.this, ApplyLeave.class);

                startActivity(AddNewAppt);
            }
        });


    }
}