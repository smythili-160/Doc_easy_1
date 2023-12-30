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

public class PrescriptionAdapter extends ArrayAdapter<Prescriptions> {
    private Context pContext;
    private List<Prescriptions> mprescription;


    public PrescriptionAdapter(@NonNull Context context, List<Prescriptions > prescriptions) {
        super(context, 0, prescriptions);
        pContext = context;
        mprescription=prescriptions;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrescriptionAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(pContext).inflate(R.layout.patient_list_item, parent, false);

            holder = new PrescriptionAdapter.ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.ageTextView = convertView.findViewById(R.id.ageTextView);
            holder.timeSlotTextView= convertView.findViewById(R.id.timeSlotTextView);
            holder.dateTextView = convertView.findViewById(R.id.dateTextView);

            convertView.setTag(holder);
        } else {
            holder = (PrescriptionAdapter.ViewHolder) convertView.getTag();
        }
        Prescriptions prescriptions = mprescription.get(position);


        convertView.setVisibility(View.VISIBLE);
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        updateViews(holder, prescriptions);
        setItemClickAction(convertView, prescriptions);


        return convertView;
    }

    private void updateViews(PrescriptionAdapter.ViewHolder holder, Prescriptions prescriptions) {
        if (prescriptions.getGender().toLowerCase().equals("male")) {
            holder.genderIcon.setImageResource(R.drawable.male_icon);
        } else {
            holder.genderIcon.setImageResource(R.drawable.female_icon);
        }

        holder.nameTextView.setText(prescriptions.getPatientName());
        holder.ageTextView.setText(String.valueOf(prescriptions.getAge()));
        holder.timeSlotTextView.setText(prescriptions.getTimeSlot());

    }

    private void setItemClickAction(View itemView, Prescriptions prescriptions) {
        itemView.setOnClickListener(view -> {
            if (prescriptions != null && prescriptions.getMid() != null) {
                String prescriptionID = prescriptions.getMid();
                DocumentReference prescriptionRef = FirebaseFirestore.getInstance().collection("prescription_details").document(prescriptionID);
                prescriptionRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Bundle prescriptionsDetails= new Bundle();
                            for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                                prescriptionsDetails.putString(entry.getKey(), entry.getValue().toString());
                            }
                            startprescriptionsDetailsActivity(prescriptionID, prescriptionsDetails);
                        } else {
                            Toast.makeText(pContext, "Unable to open details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(pContext, "Sorry Prescription ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startprescriptionsDetailsActivity(String prescriptionID, Bundle prescriptionsDetails) {
        Intent intent = new Intent(getContext(), PrescriptionForm.class);
        intent.putExtra("documentId", prescriptionID);
        intent.putExtras(prescriptionsDetails);
        pContext.startActivity(intent);
    }

    static class ViewHolder {
        ImageView genderIcon;
        TextView nameTextView;
        TextView ageTextView;
        TextView dateTextView;
        TextView timeSlotTextView;
    }
}
