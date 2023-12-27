package com.example.doc_easy_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewAppointmentPage extends AppCompatActivity {
    ImageView imageViewIcon,backbutton;
    TextView NewAppointmentTitle;
    EditText editTextName, editTextAge, editTextPhoneNumber, editTextAddress;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    Button continueBtn;
    String apptName, apptPhoneNumber, apptAddress, apptGender;
    int apptAge;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment_page);

        //Finding IDs
        imageViewIcon = findViewById(R.id.imageViewIcon);
        NewAppointmentTitle = findViewById(R.id.NewAppointmentTitle);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        backbutton=findViewById(R.id.backbutton);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(view -> {
            apptName = editTextName.getText().toString().trim().toLowerCase();
            apptAge = Integer.parseInt(editTextAge.getText().toString().trim());
            apptPhoneNumber = editTextPhoneNumber.getText().toString().trim();
            apptAddress = editTextAddress.getText().toString().trim();
            if (radioButtonMale.isChecked()) {
                apptGender = radioButtonMale.getText().toString().trim();
            } else if (radioButtonFemale.isChecked()) {
                apptGender = radioButtonFemale.getText().toString().trim();
            } else {
                radioGroupGender.requestFocus();
                Toast.makeText(NewAppointmentPage.this, "Gender cannot be Empty", Toast.LENGTH_SHORT).show();
            }

            if(apptName.equals("")||apptPhoneNumber.equals("")||(apptAddress.equals(""))||(editTextAge.equals(""))){
                Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(NewAppointmentPage.this, apptName+apptAge+apptGender+apptPhoneNumber+apptAddress, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("name", apptName);
                bundle.putInt("age", apptAge);
                bundle.putString("gender", apptGender);
                bundle.putString("phoneNumber", apptPhoneNumber);
                bundle.putString("address", apptAddress);

                Intent i = new Intent(NewAppointmentPage.this, NewAppointmentPageContinued.class);
                i.putExtras(bundle);
                startActivity(i);
                //test
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewAppointmentPage.this, rec_home_page.class);
                startActivity(intent);
                finish();
            }

        });


    }


}