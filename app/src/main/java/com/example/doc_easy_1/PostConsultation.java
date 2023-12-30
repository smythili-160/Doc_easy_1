package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostConsultation extends AppCompatActivity {
   ListView listViewForPrescription;
   ArrayList<Prescriptions> prescriptionsArrayList;
   PrescriptionAdapter prescriptionAdapter;
   FirebaseFirestore prescription_details;
   Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_consultation);
        listViewForPrescription =findViewById(R.id.listViewForPrescription);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        prescription_details=FirebaseFirestore.getInstance();
        prescriptionsArrayList=new ArrayList<Prescriptions>();
        prescriptionAdapter=new PrescriptionAdapter(PostConsultation.this,prescriptionsArrayList);
        EventChangeListener();
        listViewForPrescription.setAdapter(prescriptionAdapter);
    }


    private void EventChangeListener(){
        prescription_details.collection("prescription_details").orderBy("prescriptionsName", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Prescriptions prescriptions= dc.getDocument().toObject(Prescriptions.class);
                        prescriptions.setMid(dc.getDocument().getId());
                        prescriptionsArrayList.add(prescriptions);
                    }
                    prescriptionAdapter.notifyDataSetChanged();
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
                filterprescriptionsBySearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterprescriptionsBySearch(String query) {
        ArrayList<Prescriptions> filterprescriptions = new ArrayList<>();

        for (Prescriptions prescriptions : prescriptionsArrayList) {
            if (prescriptions.getPatientName().toLowerCase().contains(query.toLowerCase())
                    || prescriptions.getTimeSlot().toLowerCase().contains(query.toLowerCase())
            || prescriptions.getDate().toLowerCase().contains(query.toLowerCase())
            ) {
                filterprescriptions.add(prescriptions);
            }
        }

        prescriptionAdapter = new PrescriptionAdapter(PostConsultation.this, filterprescriptions);
        listViewForPrescription.setAdapter(prescriptionAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tb.setTitle("prescriptions List");
    }
}