package com.example.doc_easy_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AllHandovers extends AppCompatActivity {
    FirebaseFirestore all_handovers;
    EditText handover_details;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_handovers);

        handover_details = findViewById(R.id.handover_details);
        save_btn = findViewById(R.id.save_btn); // Initialize the save_btn

        all_handovers = FirebaseFirestore.getInstance();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use collection instead of DocumentReference
                CollectionReference collectionReference = all_handovers.collection("all_handover");

                Map<String, Object> allHandoversMap = new HashMap<>();
                allHandoversMap.put("Handover Details", handover_details.getText().toString());

                // Now, add a new document to the collection with the handover details
                collectionReference.add(allHandoversMap);
            }
        });



    }
    }


