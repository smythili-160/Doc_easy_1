package com.example.doc_easy_1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApplyLeave extends AppCompatActivity {
    private TextView startDate, endDate;
    private String startDateValue, endDateValue;
    private EditText remarks;
    private Button submit_btn;
    private FirebaseFirestore firestore;
    private FirebaseAuth dAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        startDate = findViewById(R.id.select_start_date);
        endDate = findViewById(R.id.select_end_date);
        remarks = findViewById(R.id.remarks);
        submit_btn = findViewById(R.id.submit_btn);
        firestore = FirebaseFirestore.getInstance();
        dAuth = FirebaseAuth.getInstance();
        startDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(Calendar.YEAR, year);
                            selectedDate.set(Calendar.MONTH, month);
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = dateFormat.format(selectedDate.getTime());
                            startDateValue = formattedDate;
                            startDate.setText(startDateValue);
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        endDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(Calendar.YEAR, year);
                            selectedDate.set(Calendar.MONTH, month);
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = dateFormat.format(selectedDate.getTime());
                            endDateValue = formattedDate;
                            endDate.setText(endDateValue);
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the data to a Firestore collection
                String userID = dAuth.getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("doc_user").document(userID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String doctorName = value.getString("username");
                        Map<String, Object> data = new HashMap<>();
                        data.put("doctor_user_name", doctorName);
                        data.put("leave_start_date", startDateValue);
                        data.put("leave_end_date", endDateValue);
                        data.put("remarks", remarks.getText().toString());
                        firestore.collection("applied_leaves").add(data)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // Document added successfully
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(ApplyLeave.this, "Leave Applied!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle errors
                                        Log.w(TAG, "Error adding document", e);
                                        Toast.makeText(ApplyLeave.this, "Leave application failed. Please retry.!", Toast.LENGTH_LONG).show();

                                    }
                                });
                    }
                });

            }
        });


    }
}