package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AllHandovers extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private ListView doctorsOnLeave;

    private DoctorLeavesAdapter leavesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_handovers);
        doctorsOnLeave = findViewById(R.id.doctor_leaves);
        doctorsOnLeave.setEmptyView(findViewById(R.id.emptyElement));
        firestore = FirebaseFirestore.getInstance();
        getAndLoadAllDoctorsLeaves();
    }

    private void getAndLoadAllDoctorsLeaves() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        firestore.collection("applied_leaves").whereLessThanOrEqualTo("leave_end_date", formattedDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("Handovers", "query success: "+queryDocumentSnapshots.size());
                ArrayList<Leave> leaveArrayList = new ArrayList<>();
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Leave patient = dc.getDocument().toObject(Leave.class);
                        leaveArrayList.add(patient);
                    }
                }
                leavesAdapter = new DoctorLeavesAdapter(AllHandovers.this, leaveArrayList);
                doctorsOnLeave.setAdapter(leavesAdapter);
                leavesAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Handovers", "failed"+e);
            }
        });
    }
}


