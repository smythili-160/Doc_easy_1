package com.example.doc_easy_1 ;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class NewAppointmentPageContinued extends AppCompatActivity {
    FirebaseFirestore firestore;
    TextView textViewDate, textPatientName;
    EditText editTextMedicalRemarks;
    Spinner spinnerSpecialities, spinnerDoctors, spinnerTimeSlots;
    ArrayAdapter<CharSequence> SpecialitiesAdapter, DoctorsAdapter, TimeslotsAdapter;

    Button confirmAppointmentBtn;
    RadioButton radioButtonOutPatient, radioButtonInPatient;
    String selectedspecialities, selectedDoctors, selectedTimeslots,apptName, apptGender, apptPhoneNumber, apptAddress, apptType, apptDoctorName, apptInOrOut, apptDate, apptTimeSlot, apptMedicalRemarks, apptID;
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

        radioButtonOutPatient = findViewById(R.id.radioButtonOutPatient);
        radioButtonInPatient = findViewById(R.id.radioButtonInPatient);
        spinnerTimeSlots = findViewById(R.id.spinnerTimeSlots);
        editTextMedicalRemarks = findViewById(R.id.editTextMedicalRemarks);
        confirmAppointmentBtn = findViewById(R.id.confirmAppointmentBtn);
        firestore = FirebaseFirestore.getInstance();
        spinnerSpecialities = findViewById(R.id.spinnerSpecialities);    //Finds a view that was identified by the android:id attribute

        //Country Spinner Initialisation
        SpecialitiesAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_specialities, R.layout.spinner_layout);

        // Specify the layout to use when the list of choices appear
        SpecialitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSpecialities.setAdapter(SpecialitiesAdapter);            //Set the adapter to the spinner to populate the Country Spinner

        spinnerSpecialities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //State Spinner Initialisation
                spinnerDoctors = findViewById(R.id.spinnerDoctors);    //Finds a view that was identified by the android:id attribute in xml
                selectedspecialities = spinnerSpecialities.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.spinnerSpecialities) {
                    switch (selectedspecialities) {
                        case "Select Your Specialities":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_default_Doctors, R.layout.spinner_layout);
                            break;

                        case "General Physician":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_General_Physician, R.layout.spinner_layout);
                            break;
                        case "Paediatrician":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Paediatrician, R.layout.spinner_layout);
                            break;
                        case "ENT Specialist":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_ENT_Specialist, R.layout.spinner_layout);
                            break;
                        case "Dermatologist":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Dermatologist, R.layout.spinner_layout);
                            break;
                        case "Gastroenterologist":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Gastroenterologist, R.layout.spinner_layout);
                            break;
                        case "Gynecologist":
                            DoctorsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Gynecologist, R.layout.spinner_layout);
                            break;
                        default:
                            break;
                    }

                    // Specify the layout to use when the list of choices appear
                    DoctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerDoctors.setAdapter(DoctorsAdapter);            //Set the adapter to the spinner to populate the State Spinner

                    //When any item of the spinnerDoctors is selected
                    spinnerDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            //Define City Spinner but we will populate the options through the selected state
                            spinnerTimeSlots = findViewById(R.id.spinnerTimeSlots);
                            selectedDoctors = spinnerDoctors.getSelectedItem().toString();      //Obtain the selected State

                            int parentID = parent.getId();
                            if (parentID == R.id.spinnerDoctors) {
                                switch (selectedDoctors) {

                                    case "Select Your Doctor":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_default_Time_slots, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Mishra":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Mishra, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Komali":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Komali, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Shwetha":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Shwetha, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Rohan":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Rohan, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Vijaya":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Vijaya, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Abdul":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Abdul, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Swapna":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Swapna, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Suresh":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Suresh, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Kumar":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Kumar, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Bhagya":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Bhagya, R.layout.spinner_layout);
                                        break;
                                    case "Dr.Geetha":
                                        TimeslotsAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Geetha, R.layout.spinner_layout);
                                        break;
                                    default:
                                        break;
                                }

                                TimeslotsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                                spinnerTimeSlots.setAdapter(TimeslotsAdapter);        //Populate the list of Districts in respect of the State selected

                                //To obtain the selected District from the spinner
                                spinnerTimeSlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedTimeslots = spinnerTimeSlots.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Initializing date picker dialog
        initDatePicker();

        Bundle b = getIntent().getExtras();
        apptName = b != null ? b.getString("name") : null;
        apptAge = b != null ? b.getInt("age", 23) : 23;
        apptGender = b != null ? b.getString("gender") : null;
        apptPhoneNumber = b != null ? b.getString("phoneNumber") : null;
        apptAddress = b != null ? b.getString("address","Yelahanka") : "Yelahanaka";

        showName();

        confirmAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apptType = spinnerSpecialities.getSelectedItem().toString();
                apptDoctorName = spinnerDoctors.getSelectedItem().toString();
                apptTimeSlot = spinnerTimeSlots.getSelectedItem().toString();
                apptMedicalRemarks = editTextMedicalRemarks.getText().toString().trim();
                if (radioButtonOutPatient.isChecked()) {
                    apptInOrOut = radioButtonOutPatient.getText().toString();
                } else if (radioButtonInPatient.isChecked()) {
                    apptInOrOut = radioButtonInPatient.getText().toString();
                } else {
                    Toast.makeText(NewAppointmentPageContinued.this, "In or Out cannot be Empty", Toast.LENGTH_SHORT).show();
                }


                saveData();
            }
        });
    }

    private void bookAppointment(String appointmentId) {
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("name", apptName);
        appointment.put("age", apptAge);
        appointment.put("gender", apptGender);
        appointment.put("inOrOut", apptInOrOut);
        appointment.put("remarks", apptMedicalRemarks);
        appointment.put("phoneNumber", apptPhoneNumber);
        appointment.put("address",apptAddress);
        appointment.put("type", apptType);
        appointment.put("doctor", apptDoctorName);
        appointment.put("date", apptDate);
        appointment.put("time", apptTimeSlot);
        appointment.put("appointmentId", appointmentId);
        CollectionReference allAppointmentsCollection = firestore.collection("appointments");
        allAppointmentsCollection.whereEqualTo("appointmentId", appointmentId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    AlertDialog.Builder success = new AlertDialog.Builder(NewAppointmentPageContinued.this);
                    success.setMessage("Appointment already booked").setPositiveButton("OK", (dialogInterface, i) -> {dialogInterface.dismiss(); finish();});
                    success.show();
                } else {
                    allAppointmentsCollection.add(appointment)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    AlertDialog.Builder success = new AlertDialog.Builder(NewAppointmentPageContinued.this);
                                    success.setMessage("Appointment Confirmed").setPositiveButton("OK", (dialogInterface, i) -> {dialogInterface.dismiss(); finish();});
                                    success.show();
                                }else{
                                    Exception e = task.getException();
                                    if(e!=null){
                                        e.printStackTrace();
                                    }
                                }
                            }).addOnFailureListener(e -> Toast.makeText(NewAppointmentPageContinued.this, "BAD", Toast.LENGTH_LONG).show());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewAppointmentPageContinued.this, "Appointment creation unsuccessful", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void saveToPatientsCollection() {
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("name", apptName);
        appointment.put("age", apptAge);
        appointment.put("gender", apptGender);
        appointment.put("inOrOut", apptInOrOut);
        appointment.put("phoneNumber", apptPhoneNumber);
        appointment.put("date", apptDate);
        appointment.put("address", apptAddress);
        //Save the Patient if not exists
        CollectionReference allPatientsCollection = firestore.collection("patients");
        allPatientsCollection.whereEqualTo("phoneNumber", apptPhoneNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    Log.d("NewAppointment", "Patient Already exists!");
                } else {
                    allPatientsCollection.add(appointment)
                            .addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    Toast.makeText(NewAppointmentPageContinued.this, "New Patient Added!", Toast.LENGTH_LONG).show();
                                }else{
                                    Exception e = task1.getException();
                                    if(e!=null){
                                        e.printStackTrace();
                                    }
                                }
                            }).addOnFailureListener(e -> Toast.makeText(NewAppointmentPageContinued.this, "New Patient Addition Failed!", Toast.LENGTH_LONG).show());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewAppointmentPageContinued.this, "patient creation unsuccessful", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void saveData() {
        String appointmentId = generateAppointmentID(apptDate);
        sendFinalID(appointmentId);
    }

    private String generateAppointmentID(String apptDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date;
        try {
            date = dateFormat.parse(apptDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String formattedDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(date);
        String appointmentId = formattedDate + apptTimeSlot + apptDoctorName;
        return appointmentId;
    }
    private void sendFinalID(String appointmentId) {
        apptID = appointmentId;
        saveToPatientsCollection();
        bookAppointment(appointmentId);
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

}