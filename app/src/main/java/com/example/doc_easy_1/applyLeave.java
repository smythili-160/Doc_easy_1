package com.example.doc_easy_1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class applyLeave extends AppCompatActivity {
    EditText docName,selectDate,remarks;
    Button submit_btn;
    FirebaseFirestore applied_leaves;
    String documentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        docName=findViewById(R.id.docName);
        selectDate=findViewById(R.id.selectDate);
        remarks=findViewById(R.id.remarks);
        submit_btn=findViewById(R.id.submit_btn);
        applied_leaves=FirebaseFirestore.getInstance();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        Map<String, Object> data = new HashMap<>();
        data.put("Doctor name",docName);
        data.put("Applied leave on",selectDate);
        data.put("Remarks",remarks);
        documentID = getIntent().getExtras().getString("documentId");

// Add the data to a Firestore collection
        applied_leaves.collection("applied_leaves")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Document added successfully
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        DocumentReference documentReference=applied_leaves.collection("applied_leaves").document(documentID);
        documentReference.addSnapshotListener(applyLeave.this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                docName.setText(documentSnapshot.getString("name"));
                selectDate.setText(documentSnapshot.getString("doc_email_address"));
                remarks.setText(documentSnapshot.getString("username"));

            }
        });
            }
        });


    }
}