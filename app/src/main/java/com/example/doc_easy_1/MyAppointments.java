package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MyAppointments extends AppCompatActivity {
    FirebaseFirestore appointments;
    ListView listViewForAppointments;
    ArrayList<Appointment> mAppointments;
    MyAppointmentsAdapter adapter1;
    FirebaseAuth dAuth;
    FirebaseFirestore doc_user;
    Toolbar tb;
    String doctor,apptDate;
    TextView selectDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    ArrayList<String> filterChips = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments_list);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        selectDate=findViewById(R.id.selectDate);
        listViewForAppointments = findViewById(R.id.listViewForAppointments);
        appointments = FirebaseFirestore.getInstance();
        mAppointments = new ArrayList<>();
        dAuth= FirebaseAuth.getInstance();
        adapter1 = new MyAppointmentsAdapter(MyAppointments.this, mAppointments);
        listViewForAppointments.setAdapter((ListAdapter) adapter1);
        doc_user=FirebaseFirestore.getInstance();
        //Set Today's Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(System.currentTimeMillis());
        selectDate.setText(formattedDate);
        apptDate = formattedDate;
        filterAppointmentByDate();
        initDatePicker();
    }
    private void initDatePicker() {
        dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(selectedDate);

        };


        selectDate.setOnClickListener(v -> {
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

    protected void updateDate(Calendar selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime());
        selectDate.setText(formattedDate);
        apptDate = formattedDate;
        Toast.makeText(this, formattedDate.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
        filterAppointmentByDate();
    }
    private void filterAppointmentByDate(){
        String userID = dAuth.getCurrentUser().getUid();

        DocumentReference documentReference = doc_user.collection("doc_user").document(userID);
        mAppointments.clear();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                doctor = documentSnapshot.getString("username");
                appointments.collection("appointments").whereEqualTo("doctor", doctor).whereEqualTo("date", apptDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Appointment appointment = document.toObject(Appointment.class);
                            appointment.setMid(document.getId());
                            mAppointments.add(appointment);
                        }
                        adapter1.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyAppointments.this, "Error Fetching appointments", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        if (searchView != null) {
            searchView.setQueryHint("Search Here...");
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAppointmentsBySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void filterAppointmentsBySearch(String query) {
        ArrayList<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : mAppointments) {
            if (appointment.getName().toLowerCase().contains(query.toLowerCase())
                    || String.valueOf(appointment.getAge()).contains(query)
                    || appointment.getType().toLowerCase().contains(query.toLowerCase())) {
                filteredAppointments.add(appointment);
            }
        }
        adapter1 = new MyAppointmentsAdapter(MyAppointments.this, filteredAppointments);
        listViewForAppointments.setAdapter(adapter1);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tb.setTitle("My Appointments");
    }

}