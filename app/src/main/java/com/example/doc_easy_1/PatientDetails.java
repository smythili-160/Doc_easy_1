package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class PatientDetails extends AppCompatActivity {
    int age;
    FirebaseFirestore patients;

    TextView textView_age;
    EditText name, gender, phoneNumber, address,inOrOut, date,documentID;
    String p_name, p_gender, p_phoneNumber, p_address,p_inOrOut, p_date,p_documentID,p_age;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        name=findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        phoneNumber=findViewById(R.id.phoneNumber);
        address=findViewById(R.id.address);

        inOrOut=findViewById(R.id.inOrOut);
        date=findViewById(R.id.date);

        documentID=findViewById(R.id.documentID);
        textView_age=findViewById(R.id.textView_age);
        patients= FirebaseFirestore.getInstance();
        p_documentID = getIntent().getStringExtra("documentId");
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            p_name = extras.getString("name");
            age = Integer.parseInt(extras.getString("age"));
            p_gender = extras.getString("gender");
            p_phoneNumber = extras.getString("phoneNumber");
            p_address = extras.getString("address");

            p_inOrOut = extras.getString("inOrOut");
            p_date = extras.getString("date");

        }
        name.setText(p_name);
        gender.setText(p_gender);
        phoneNumber.setText(p_phoneNumber);
        address.setText(p_address);

        inOrOut.setText(p_inOrOut);
        date.setText(p_date);

        documentID.setText(p_documentID);
        p_age=Integer.toString(age);
        textView_age.setText(p_age);

    }

}