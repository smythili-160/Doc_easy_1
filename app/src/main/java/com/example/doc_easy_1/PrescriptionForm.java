package com.example.doc_easy_1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionForm extends AppCompatActivity {

    int age;
    private FirebaseFirestore firestore;
    private EditText name, gender, doctorName, inOrOut, date, time, pres_details,textView_age;
    private String p_name, p_gender, p_doctorName, p_inOrOut, p_date, p_time, p_age, p_documentID,p_details,appointmentId;
    private Button save_btn;

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






        firestore = FirebaseFirestore.getInstance();
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move the following lines inside the onCreate method
                DocumentReference documentReference = firestore.collection("appointments").document(p_documentID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Map<String, Object> prescriptionDetailsMap = new HashMap<>();
                        prescriptionDetailsMap.put("DoctorName", p_doctorName);
                        prescriptionDetailsMap.put("PatientName", p_name);
                        prescriptionDetailsMap.put("Gender", p_gender);
                        prescriptionDetailsMap.put("Age", p_age);
                        prescriptionDetailsMap.put("InOrOut", p_inOrOut);
                        prescriptionDetailsMap.put("PrescriptionDetails", pres_details.getText().toString());
                        prescriptionDetailsMap.put("Date", p_date);
                        prescriptionDetailsMap.put("TimeSlot", p_time);
                        firestore.collection("prescription_details").add(prescriptionDetailsMap)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // Document added successfully
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(PrescriptionForm.this, "Consultation details entered", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG","Error in adding Consultation details",e);
                                        Toast.makeText(PrescriptionForm.this,"Error in storing Consultation Details",Toast.LENGTH_LONG).show();

                                    }
                                });

                    }
                });


            }
        });

    }

}
