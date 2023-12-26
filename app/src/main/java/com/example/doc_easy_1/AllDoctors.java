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

public class AllDoctors extends AppCompatActivity {
    ListView listViewForDoctors;
    ArrayList<Doctor> doctorArrayList;
    DoctorAdapter doctorAdapter;
    FirebaseFirestore doc_user;
    Toolbar tb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctors);
        listViewForDoctors = findViewById(R.id.listViewForDoctors);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        doc_user = FirebaseFirestore.getInstance();
        doctorArrayList = new ArrayList<Doctor>();
        doctorAdapter = new DoctorAdapter(AllDoctors.this, doctorArrayList);
        EventChangeListener();
        listViewForDoctors.setAdapter(doctorAdapter);
    }

    private void EventChangeListener() {
        doc_user.collection("doc_user").orderBy("username", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Doctor doctor = dc.getDocument().toObject(Doctor.class);
                        doctor.setMid(dc.getDocument().getId());
                        doctorArrayList.add(doctor);
                    }
                    doctorAdapter.notifyDataSetChanged();
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
                filterdoctorsBySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterdoctorsBySearch(String query) {
        ArrayList<Doctor> filterdoctors = new ArrayList<>();

        for (Doctor doctor : doctorArrayList) {
            if (doctor.getUsername().toLowerCase().contains(query.toLowerCase())
                    || doctor.getPhoneNumber().toLowerCase().contains(query.toLowerCase())
                    || doctor.getSpeciality().toLowerCase().contains(query.toLowerCase())) {
                filterdoctors.add(doctor);
            }
        }

        doctorAdapter = new DoctorAdapter(AllDoctors.this, filterdoctors);
        listViewForDoctors.setAdapter(doctorAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tb.setTitle("Doctors List");
    }

}
