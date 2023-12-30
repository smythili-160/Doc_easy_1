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

public class PrescriptionAdapter extends ArrayAdapter<Prescription> {
    private Context pContext;
    private List<Prescription> mprescription;
    public PrescriptionAdapter(@NonNull Context context, @NonNull List<Prescription> prescription) {
        super(context, 0, prescription);
        pContext = context;
        mprescription = prescription;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrescriptionAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(pContext).inflate(R.layout.prescription_list_item, parent, false);

            holder = new PrescriptionAdapter.ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.ageTextView = convertView.findViewById(R.id.ageTextView);
            holder.dateTextView = convertView.findViewById(R.id.dateTextView);
            holder.tsTextView=convertView.findViewById(R.id.tsTextView);

            convertView.setTag(holder);
        } else {
            holder = (PrescriptionAdapter.ViewHolder) convertView.getTag();
        }
        Prescription prescription = mprescription.get(position);
        convertView.setVisibility(View.VISIBLE);
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        updateViews(holder, prescription);
        setItemClickAction(convertView, prescription);
//        }

        return convertView;

    }
    private void updateViews(PrescriptionAdapter.ViewHolder holder, Prescription prescription) {
        if (prescription.getGender().toLowerCase().equals("male")) {
            holder.genderIcon.setImageResource(R.drawable.male_icon);
        } else {
            holder.genderIcon.setImageResource(R.drawable.female_icon);
        }

        holder.nameTextView.setText(prescription.getPatientName());
        holder.ageTextView.setText(String.valueOf(prescription.getAge()));
        holder.dateTextView.setText(prescription.getDate());
        holder.tsTextView.setText(prescription.getTimeSlot());
    }
    private void setItemClickAction(View itemView, Prescription prescription) {
        itemView.setOnClickListener(view -> {
            if (prescription != null && prescription.getMid() != null) {
                String prescriptionID = prescription.getMid();
                DocumentReference prescriptionRef = FirebaseFirestore.getInstance().collection("prescription_details").document(prescriptionID);
                prescriptionRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Bundle prescriptionDetails = new Bundle();
                            for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                                prescriptionDetails.putString(entry.getKey(), entry.getValue().toString());
                            }
                            startPrescriptionDetailsActivity(prescriptionID, prescriptionDetails);
                        } else {
                            Toast.makeText(pContext, "Unable to open details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(pContext, "Sorry prescription ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startPrescriptionDetailsActivity(String prescriptionID, Bundle prescriptionDetails) {
        Intent i = new Intent(getContext(), PrescriptionDetails.class);
        i.putExtra("documentId", prescriptionID);
        i.putExtras(prescriptionDetails);
        pContext.startActivity(i);
    }
        static class ViewHolder {
            ImageView genderIcon;
            TextView nameTextView;
            TextView ageTextView;
            TextView tsTextView;
            TextView dateTextView;
        }
    
}
