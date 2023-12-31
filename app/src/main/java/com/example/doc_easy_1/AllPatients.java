package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllPatients extends AppCompatActivity {
    ListView listViewForPatients;
    ArrayList<Patient> patientArrayList;
    PatientAdapter patientAdapter;
    FirebaseFirestore patients;
    Toolbar tb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_patients);
        listViewForPatients = findViewById(R.id.listViewForPatients);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        patients = FirebaseFirestore.getInstance();
        patientArrayList = new ArrayList<Patient>();
        patientAdapter = new PatientAdapter(AllPatients.this, patientArrayList);
        EventChangeListener();
        listViewForPatients.setAdapter(patientAdapter);
    }

    private void EventChangeListener() {
        patients.collection("patients").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Patient patient = dc.getDocument().toObject(Patient.class);
                        patient.setMid(dc.getDocument().getId());
                        patientArrayList.add(patient);
                    }
                    patientAdapter.notifyDataSetChanged();
                }
            }
        });
    }

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
//            if(adapter1!=null){
//                adapter1.getFilter().filter(newText);
//            }
                filterpatientsBySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterpatientsBySearch(String query) {
        ArrayList<Patient> filterpatients = new ArrayList<>();

        for (Patient patient : patientArrayList) {
            if (patient.getName().toLowerCase().contains(query.toLowerCase())
                    || patient.getPhoneNumber().toLowerCase().contains(query.toLowerCase())
                    ) {
                filterpatients.add(patient);
            }
        }

        patientAdapter = new PatientAdapter(AllPatients.this, filterpatients);
        listViewForPatients.setAdapter(patientAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tb.setTitle("Patients List");
    }
}