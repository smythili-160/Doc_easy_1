package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class PrescriptionDetails extends AppCompatActivity {
    int age;
    private FirebaseFirestore prescription_details;
    private TextView textView_age,name, gender, doctorName, inOrOut, date, time, pres_details;
    private String p_name, p_gender, p_doctorName, p_inOrOut, p_date, p_time, p_age,p_documentID,p_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        doctorName = findViewById(R.id.doctorName);
        inOrOut = findViewById(R.id.inOrOut);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        textView_age = findViewById(R.id.textView_age);
        pres_details = findViewById(R.id.pres_details);
        p_documentID = getIntent().getStringExtra("documentId");
        prescription_details=FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            p_name = extras.getString("PatientName");
            age = Integer.parseInt(extras.getString("Age"));
            p_gender = extras.getString("Gender");
            p_doctorName = extras.getString("DoctorName");
            p_inOrOut = extras.getString("InOrOut");
            p_date = extras.getString("Date");
            p_time=extras.getString("TimeSlot");
            p_details=extras.getString("PrescriptionDetails");

        }
        name.setText(p_name);
        p_age = Integer.toString(age);
        gender.setText(p_gender);
        doctorName.setText(p_doctorName);
        inOrOut.setText(p_inOrOut);
        date.setText(p_date);
        time.setText(p_time);
        textView_age.setText(p_age);
        pres_details.setText(p_details);
    }
}