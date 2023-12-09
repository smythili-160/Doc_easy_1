package com.example.doc_easy_1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doc_easy_1.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NewAppointmentPageContinued extends AppCompatActivity implements AppointmentIDCallback {
    FirebaseFirestore firestore;
    TextView textViewDate, textPatientName;
    EditText editTextType, editTextDoctorName, editTextMedicalRemarks;
    Spinner spinnerTimeSlots;
    Button confirmAppointmentBtn;
    RadioButton radioButtonOutPatient, radioButtonInPatient;
    String apptName, apptGender, apptPhoneNumber, apptAddress, apptType, apptDoctorName, apptInOrOut, apptDate, apptTimeSlot, apptMedicalRemarks, apptID;
    int apptAge;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment_page_continued);

        //Finding Views by IDs
        textViewDate = findViewById(R.id.textViewDate);
        textPatientName = findViewById(R.id.textPatientName);
        editTextType = findViewById(R.id.editTextType);
        editTextDoctorName = findViewById(R.id.editTextDoctorName);
        radioButtonOutPatient = findViewById(R.id.radioButtonOutPatient);
        radioButtonInPatient = findViewById(R.id.radioButtonInPatient);
        spinnerTimeSlots = findViewById(R.id.spinnerTimeSlots);
        editTextMedicalRemarks = findViewById(R.id.editTextMedicalRemarks);
        confirmAppointmentBtn = findViewById(R.id.confirmAppointmentBtn);
        firestore = FirebaseFirestore.getInstance();

        //Initializing date picker dialog
        initDatePicker();

        Bundle b = getIntent().getExtras();
        apptName = b != null ? b.getString("name") : null;
        apptAge = b != null ? b.getInt("age", 0) : 0;
        apptGender = b != null ? b.getString("gender") : null;
        apptPhoneNumber = b != null ? b.getString("phoneNumber") : null;
        apptAddress = b != null ? b.getString("address") : null;

        showName();

        confirmAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apptType = editTextType.getText().toString();
                apptDoctorName = editTextDoctorName.getText().toString();
                apptTimeSlot = spinnerTimeSlots.getSelectedItem().toString();
                apptMedicalRemarks = editTextMedicalRemarks.getText().toString().trim();
                if (radioButtonOutPatient.isChecked()) {
                    apptInOrOut = radioButtonOutPatient.getText().toString();
                } else if (radioButtonInPatient.isChecked()) {
                    apptInOrOut = radioButtonInPatient.getText().toString();
                } else {
                    Toast.makeText(NewAppointmentPageContinued.this, "In or Out cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                saveAppointmentToFirestore();
            }
        });
    }
    private void saveAppointmentToFirestore() {
        Toast.makeText(this, apptName+apptAge+apptGender+apptPhoneNumber+apptAddress+apptType+apptDoctorName+apptInOrOut+apptDate+apptTimeSlot+apptMedicalRemarks , Toast.LENGTH_LONG).show();
        saveToAppointmentsCollection();
        saveToPatientsCollection();

    }

    private void saveToPatientsCollection() {

    }

    private void saveToAppointmentsCollection() {
        generateAppointmentID(apptDate, this);
    }
    private void generateAppointmentID(String apptDate, AppointmentIDCallback callback) {
        CollectionReference allAppointmentsColleciton = firestore.collection("appointments");
        String uniqueCode = "A";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date;
        try{
            date = dateFormat.parse(apptDate);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        String formattedDate = new SimpleDateFormat("dd", Locale.getDefault()).format(date);
        DocumentReference dateDocumentref = allAppointmentsColleciton.document(formattedDate);
        dateDocumentref.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot dateDocument = task.getResult();
                        if(dateDocument.exists()){
//                            long patienCount = dateDocument.getData().size();
                            String appointmentId = formattedDate+uniqueCode+(apptName.charAt(0));
                            sendFinalID(appointmentId);
                            callback.onAppointmentIDGenerated(appointmentId);
                        }else{
                            String appointmentId = formattedDate+uniqueCode+"1";
                            sendFinalID(appointmentId);
                        }
                    }else{
                        Toast.makeText(NewAppointmentPageContinued.this, "Failed to Create Appt ID", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void sendFinalID(String appointmentId) {
        apptID = appointmentId;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        Date date;
//        try{
//            date = dateFormat.parse(apptDate);
//        }catch(Exception e){
//            e.printStackTrace();
//            return;
//        }
//        String formattedDate = new SimpleDateFormat("dd", Locale.getDefault()).format(date);
//        String appointmentIDCreated = "A"+formattedDate+apptName.charAt(0)+apptName.charAt(apptName.length()-1);
        //Mallesh on 16 = A16Mh
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("name", apptName);
        appointment.put("age", apptAge);
        appointment.put("gender", apptGender);
        appointment.put("inOrOut", apptInOrOut);
        appointment.put("remarks", apptMedicalRemarks);
        appointment.put("phoneNumber", apptPhoneNumber);
        appointment.put("type", apptType);
        appointment.put("doctor", apptDoctorName);
        appointment.put("date", apptDate);
        appointment.put("time", apptTimeSlot);
        appointment.put("address", apptAddress);

        CollectionReference allAppointmentsCollection = firestore.collection("appointments");
        allAppointmentsCollection.add(appointment)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        CollectionReference allPatientsCollection = firestore.collection("patients");
                        allPatientsCollection.add(appointment)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        AlertDialog.Builder success = new AlertDialog.Builder(NewAppointmentPageContinued.this);
                                        success.setMessage("Patients ListAdded Broo!!").setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                                        success.show();
                                    }else{
                                        Exception e = task1.getException();
                                        if(e!=null){
                                            e.printStackTrace();
                                        }
                                    }
                                }).addOnFailureListener(e -> Toast.makeText(NewAppointmentPageContinued.this, "BADDDDDDDDDDDDDDD", Toast.LENGTH_LONG).show());


                        AlertDialog.Builder success = new AlertDialog.Builder(NewAppointmentPageContinued.this);
                        success.setMessage("Appointment Confirmed Broo!!").setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                        success.show();
                        finish();
                    }else{
                        Exception e = task.getException();
                        if(e!=null){
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(e -> Toast.makeText(NewAppointmentPageContinued.this, "BADDDDDDDDDDDDDDD", Toast.LENGTH_LONG).show());


    }
    private void showName() {
        String heyUser = "Hey " + apptName + ",";
        textPatientName.setText(heyUser);
    }
    private void initDatePicker() {
        dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(selectedDate);
        };

        textViewDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }
    private void updateDate(Calendar selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime());
        textViewDate.setText(formattedDate);
        apptDate = formattedDate;
        Toast.makeText(this, formattedDate.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAppointmentIDGenerated(String appointmentID) {
        apptID = appointmentID;
        saveToAppointmentsCollection();
    }

    public void saveToPatientsCollections(){
        Map<String, Object> patient = new HashMap<>();
        patient.put("name", apptName);
        patient.put("age", apptAge);
        patient.put("gender", apptGender);
        patient.put("inOrOut", apptInOrOut);
        patient.put("remarks", apptMedicalRemarks);
        CollectionReference allPatientsCollection = firestore.collection("patients");

    }
}