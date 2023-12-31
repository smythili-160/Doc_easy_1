package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
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

public class RecHomePage extends AppCompatActivity {
    CardView addNewAppointment,handovers,AppointmentsList,All_doctors,All_patients;
    ImageView rec_profile;
    Button rec_logout_button;
    FirebaseFirestore rec_user;
    TextView welcome_username;
    FirebaseAuth fAuth;

    String userID;
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPreferences = RecHomePage.this.getPreferences( MODE_PRIVATE );
        sharedPreferences.edit().remove("role").commit();
        startActivity(new Intent(getApplicationContext(), RecLoginPage.class));
        finish();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_home_page);
        All_doctors=findViewById(R.id.All_doctors);
        addNewAppointment = findViewById(R.id.addNewAppointment);
        handovers = findViewById(R.id.handovers);
        rec_profile=findViewById(R.id.rec_profile);
        rec_logout_button = findViewById(R.id.rec_logout_button);
        welcome_username = findViewById(R.id.welcome_username);
        All_patients=findViewById(R.id.All_patients);
        fAuth= FirebaseAuth.getInstance();
        rec_user=FirebaseFirestore.getInstance();
        addNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(RecHomePage.this, NewAppointmentPage.class);
                startActivity(AddNewAppt);
            }
        });

        rec_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecHomePage.this, RecProf.class);
                startActivity(i);
            }
        }
        );
        userID=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=rec_user.collection("rec_user").document(userID);
        documentReference.addSnapshotListener(RecHomePage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                welcome_username.setText(documentSnapshot.getString("rec_username"));
            }
        });
        AppointmentsList = findViewById(R.id.AppointmentsList);
        AppointmentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(RecHomePage.this, AllAppointments.class);
                startActivity(AddNewAppt);
            }
        });


        handovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddNewAppt = new Intent(RecHomePage.this, AllHandovers.class);
                startActivity(AddNewAppt);
            }
        });
        All_doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddNewAppt = new Intent(RecHomePage.this, AllDoctors.class);
                startActivity(AddNewAppt);

            }
        });
        All_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddNewAppt = new Intent(RecHomePage.this, AllPatients.class);
                startActivity(AddNewAppt);

            }
        });

    }

}