package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionForm extends AppCompatActivity {

    int age;
    FirebaseFirestore prescription_details;
    TextView textView_age;
    EditText name, gender, doctorName, inOrOut, date, time, pres_details;
    String p_name, p_gender, p_doctorName, p_inOrOut, p_date, p_time, p_age, p_documentID;
    Button save_btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_form);

        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        doctorName = findViewById(R.id.doctorName);
        inOrOut = findViewById(R.id.inOrOut);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        textView_age = findViewById(R.id.textView_age);
        pres_details = findViewById(R.id.pres_details);
        save_btn=findViewById(R.id.save_btn);

        p_documentID = getIntent().getStringExtra("documentId");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            p_name = extras.getString("name");
            age = Integer.parseInt(extras.getString("age"));
            p_gender = extras.getString("gender");
            p_doctorName = extras.getString("doctor");
            p_inOrOut = extras.getString("inOrOut");
            p_date = extras.getString("date");
            p_time = extras.getString("time");
        }

        name.setText(p_name);
        gender.setText(p_gender);
        doctorName.setText(p_doctorName);
        inOrOut.setText(p_inOrOut);
        date.setText(p_date);
        time.setText(p_time);
        p_age = Integer.toString(age);
        textView_age.setText(p_age);

        prescription_details = FirebaseFirestore.getInstance();
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move the following lines inside the onCreate method
                DocumentReference documentReference = prescription_details.collection("prescription_details").document(p_documentID);

                Map<String, Object> prescriptionDetailsMap = new HashMap<>();
                prescriptionDetailsMap.put("Doctor Name", p_doctorName);
                prescriptionDetailsMap.put("Prescription Name", p_name);
                prescriptionDetailsMap.put("Gender", p_gender);
                prescriptionDetailsMap.put("Age", p_age);
                prescriptionDetailsMap.put("In or Out", p_inOrOut);
                prescriptionDetailsMap.put("Prescription Details", pres_details.getText().

                        toString());
                prescriptionDetailsMap.put("Date", p_date);
                prescriptionDetailsMap.put("Time Slot", p_time);

                // Now, update the document with the prescription details
                documentReference.set(prescriptionDetailsMap);
            }
        });
    }
}
