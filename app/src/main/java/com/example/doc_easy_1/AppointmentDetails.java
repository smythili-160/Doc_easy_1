package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentDetails extends AppCompatActivity {

    int age;
    FirebaseFirestore appointments;
    Button cancel_appointment;
    TextView textView_age;
    EditText name, gender, phoneNumber, address, type, doctorName, inOrOut, date, time, remarks, documentID;
    String p_name, p_gender, p_phoneNumber, p_address, p_type, p_doctorName, p_inOrOut, p_date, p_time, p_remarks, p_documentID, p_age;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        type = findViewById(R.id.type);
        doctorName = findViewById(R.id.doctorName);
        inOrOut = findViewById(R.id.inOrOut);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        remarks = findViewById(R.id.remarks);
        documentID = findViewById(R.id.documentID);
        textView_age = findViewById(R.id.textView_age);
        cancel_appointment = findViewById(R.id.cancel_appointment);
        appointments = FirebaseFirestore.getInstance();
        p_documentID = getIntent().getStringExtra("documentId");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            p_name = extras.getString("name");
            age = Integer.parseInt(extras.getString("age"));
            p_gender = extras.getString("gender");
            p_phoneNumber = extras.getString("phoneNumber");
            p_address = extras.getString("address");
            p_type = extras.getString("type");
            p_doctorName = extras.getString("doctor");
            p_inOrOut = extras.getString("inOrOut");
            p_date = extras.getString("date");
            p_time = extras.getString("time");
            p_remarks = extras.getString("remarks");
        }
        name.setText(p_name);
        gender.setText(p_gender);
        phoneNumber.setText(p_phoneNumber);
        address.setText(p_address);
        type.setText(p_type);
        doctorName.setText(p_doctorName);
        inOrOut.setText(p_inOrOut);
        date.setText(p_date);
        time.setText(p_time);
        remarks.setText(p_remarks);
        p_age = Integer.toString(age);
        textView_age.setText(p_age);
        documentID.setText(p_documentID);
        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appointments.collection("appointments").document(p_documentID).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {Toast.makeText(AppointmentDetails.this, p_name+" Appointment successfully deleted!\"", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
            }

        });
    }
}