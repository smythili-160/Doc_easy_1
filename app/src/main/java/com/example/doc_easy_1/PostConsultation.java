package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
public class PostConsultation extends AppCompatActivity {

    ListView listViewForPrescription;
    ArrayList<Prescription> prescriptionArrayList;
    PrescriptionAdapter mprescriptionAdapter;
    FirebaseFirestore prescription_details;
    FirebaseAuth dAuth;
    FirebaseFirestore doc_user;
    String doctor;
    Toolbar tb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_consultation);
        listViewForPrescription= findViewById(R.id.listViewForPrescription);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        prescription_details = FirebaseFirestore.getInstance();
        dAuth= FirebaseAuth.getInstance();
        doc_user=FirebaseFirestore.getInstance();
        prescriptionArrayList = new ArrayList<Prescription>();
        mprescriptionAdapter = new PrescriptionAdapter(PostConsultation.this, prescriptionArrayList);
        filterprescriptionByDoctor();
        EventChangeListener();
        listViewForPrescription.setAdapter(mprescriptionAdapter);

    }
    private void filterprescriptionByDoctor(){
        String userID = dAuth.getCurrentUser().getUid();

        DocumentReference documentReference = doc_user.collection("doc_user").document(userID);
        prescriptionArrayList.clear();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                doctor = documentSnapshot.getString("username");
                prescription_details.collection("prescription_details").whereEqualTo("DoctorName", doctor).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Prescription prescription = document.toObject(Prescription.class);
                            prescription.setMid(document.getId());
                            prescriptionArrayList.add(prescription);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostConsultation.this, "Error Fetching prescription_details", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void EventChangeListener() {
        prescription_details.collection("prescription_details").orderBy("PatientName", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Prescription prescription = dc.getDocument().toObject(Prescription.class);
                        prescription.setMid(dc.getDocument().getId());
                        prescriptionArrayList.add(prescription);
                    }
                    mprescriptionAdapter.notifyDataSetChanged();
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
//            if(mprescriptionAdapter!=null){
//                mprescriptionAdapter.getFilter().filter(newText);
//            }
                filterPrescriptionBySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void filterPrescriptionBySearch(String query) {
        ArrayList<Prescription> filterprescription = new ArrayList<>();

        for (Prescription prescription: prescriptionArrayList) {
            if (prescription.getPatientName().toLowerCase().contains(query.toLowerCase())
                    || prescription.getDate().toLowerCase().contains(query.toLowerCase())
            ) {
                filterprescription.add(prescription);
            }
        }

        mprescriptionAdapter = new PrescriptionAdapter(PostConsultation.this, filterprescription);
        listViewForPrescription.setAdapter(mprescriptionAdapter);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tb.setTitle("prescription List");
    }

}


    