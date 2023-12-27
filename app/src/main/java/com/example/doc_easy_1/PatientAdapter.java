package com.example.doc_easy_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatientAdapter extends ArrayAdapter<Patient> {
    private Context pContext;
    private List<Patient> mpatient;
    private List<String> appliedFilters;


    public PatientAdapter(Context context, List<Patient> patient) {
        super(context, 0, patient);
        pContext = context;
        mpatient = patient;
       
    }

    

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatientAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(pContext).inflate(R.layout.patient_list_item, parent, false);

            holder = new PatientAdapter.ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.ageTextView = convertView.findViewById(R.id.ageTextView);
            holder.phoneNumberTextView = convertView.findViewById(R.id.phoneNumberTextView);

            convertView.setTag(holder);
        } else {
            holder = (PatientAdapter.ViewHolder) convertView.getTag();
        }
        Patient patient = mpatient.get(position);

//        if (!shouldShowAppointment(patient)) {
//            convertView.setVisibility(View.GONE);
//            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        } else {
        convertView.setVisibility(View.VISIBLE);
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        updateViews(holder, patient);
        setItemClickAction(convertView, patient);
//        }

        return convertView;
    }

    private void updateViews(PatientAdapter.ViewHolder holder, Patient patient) {
        if (patient.getGender().toLowerCase().equals("male")) {
            holder.genderIcon.setImageResource(R.drawable.male_icon);
        } else {
            holder.genderIcon.setImageResource(R.drawable.female_icon);
        }

        holder.nameTextView.setText(patient.getName());
        holder.ageTextView.setText(String.valueOf(patient.getAge()));
        holder.phoneNumberTextView.setText(patient.getPhoneNumber());
    }

    private void setItemClickAction(View itemView, Patient patient) {
        itemView.setOnClickListener(view -> {
            if (patient != null && patient.getMid() != null) {
                String patientID = patient.getMid();
                DocumentReference patientRef = FirebaseFirestore.getInstance().collection("patients").document(patientID);
                patientRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Bundle patientDetails = new Bundle();
                            for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                                patientDetails.putString(entry.getKey(), entry.getValue().toString());
                            }
                            startPatientDetailsActivity(patientID, patientDetails);
                        } else {
                            Toast.makeText(pContext, "Unable to open details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(pContext, "Sorry Patient ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPatientDetailsActivity(String patientID, Bundle patientDetails) {
        Intent intent = new Intent(getContext(), PatientDetails.class);
        intent.putExtra("documentId", patientID);
        intent.putExtras(patientDetails);
        pContext.startActivity(intent);
    }
/*
    private boolean shouldShowPatient(Patient patient) {
        if (appliedFilters.isEmpty()) {
            return true;
        }

        for (String filter : appliedFilters) {
            switch (filter.toLowerCase()) {
                case "male":
                case "female":
                    if (patient.getGender().equalsIgnoreCase(filter)) {
                        return true;
                    }
                    break;

                case "inpatient":
                case "outpatient":
                    if (patient.getType().equalsIgnoreCase(filter)) {
                        return true;
                    }
                    break;

                case "age":
                    // Implement logic to check age filters if needed
                    // Example: if (patient.getAge() >= MIN_AGE && patient.getAge() <= MAX_AGE)
                    break;

                // Add additional cases for other filters as needed
            }
        }
        return false;
    }
*/
    static class ViewHolder {
        ImageView genderIcon;
        TextView nameTextView;
        TextView ageTextView;
        TextView phoneNumberTextView;
    }
}
