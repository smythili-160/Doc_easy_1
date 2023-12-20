package com.example.doc_easy_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class doc_signup_front_page extends AppCompatActivity {
    EditText doc_signup_name, doc_signup_username,doc_phone_number,doc_speciality,doc_experience,doc_Time_Slots;
    String doc_name,doc_username,doc_phno,doc_spec,doc_exp,doc_ts;
    Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_signup_front_page);
        //Finding IDs
        doc_signup_name= findViewById(R.id.doc_signup_name);
        doc_signup_username = findViewById(R.id.doc_signup_username);
        doc_phone_number = findViewById(R.id.doc_phone_number);
        doc_speciality = findViewById(R.id.doc_speciality);
        doc_experience = findViewById(R.id.doc_experience);
        doc_Time_Slots = findViewById(R.id.doc_Time_Slots);
        continueBtn=findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(view -> {
            doc_name = doc_signup_name.getText().toString().trim().toLowerCase();
            doc_username = doc_signup_username.getText().toString().trim().toLowerCase();
            doc_phno = doc_phone_number.getText().toString().trim();
            doc_spec = doc_speciality.getText().toString().trim();
            doc_exp = doc_experience.getText().toString().trim();
            doc_ts = doc_Time_Slots.getText().toString().trim();




            if(doc_name.equals("")||doc_phno.equals("")||(doc_spec.equals(""))||(doc_username.equals(""))||(doc_exp.equals(""))||(doc_ts.equals(""))){
                Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(doc_signup_front_page.this, doc_name+doc_username+doc_exp+doc_phno+doc_spec+doc_ts, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("name", doc_name);
                bundle.putString("username",doc_username);
                bundle.putString("speciality",doc_spec );
                bundle.putString("phoneNumber", doc_phno);
                bundle.putString("Experience", doc_exp);
                bundle.putString("TimeSlots", doc_ts);

                Intent i = new Intent(doc_signup_front_page.this, doc_signup_page.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });



    }
}