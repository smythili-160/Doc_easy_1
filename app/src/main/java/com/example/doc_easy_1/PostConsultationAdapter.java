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

public class PostConsultationAdapter extends ArrayAdapter<Prescription> {

    private Context mContext;
    private List<Prescription> mprescpt_details;
    private List<String> appliedFilters;


    public PostConsultationAdapter(Context context, List<Prescription> appointments) {
        super(context, 0, appointments);
        mContext = context;
        mprescpt_details = appointments;
        appliedFilters = new ArrayList<>();
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.postcons, parent, false);

            holder = new ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.nameTextView = convertView.findViewById(R.id.name);
            holder.pres_details = convertView.findViewById(R.id.pres_details);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Prescription prescp_details = mprescpt_details.get(position);

        if (!shouldShowPrescription(prescp_details)) {
            convertView.setVisibility(View.GONE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        } else {
            convertView.setVisibility(View.VISIBLE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            updateViews(holder, prescp_details);
            setItemClickAction(convertView, prescp_details);
        }

        return convertView;
    }

    private void updateViews(ViewHolder holder, Prescription appointment) {
        if (appointment.getGender().toLowerCase().equals("male")) {
            holder.genderIcon.setImageResource(R.drawable.male_icon);
        } else {
            holder.genderIcon.setImageResource(R.drawable.female_icon);
        }

        holder.nameTextView.setText(appointment.getPatientName());
        holder.pres_details.setText(appointment.getPrescriptionDetails());

    }

    private void setItemClickAction(View itemView, Prescription appointment) {
        itemView.setOnClickListener(view -> {
            if (appointment != null && appointment.getPatientName() != null) {
                String appointmentID = appointment.getPatientName();
                DocumentReference appointmentRef = FirebaseFirestore.getInstance().collection("prescription_details").document(appointmentID);
                appointmentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Bundle appointmentDetails = new Bundle();
                            for (Map.Entry<String, Object> entry : documentSnapshot.getData().entrySet()) {
                                appointmentDetails.putString(entry.getKey(), entry.getValue().toString());
                            }
                            startPrescriptionFormActivity(appointmentID, appointmentDetails);
                        } else {
                            Toast.makeText(mContext, "Unable to open details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(mContext, "Sorry Prescription ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPrescriptionFormActivity(String appointmentID, Bundle appointmentDetails) {
        Intent intent = new Intent(getContext(), PrescriptionForm.class);
        intent.putExtra("documentId", appointmentID);
        intent.putExtras(appointmentDetails);
        mContext.startActivity(intent);
    }

    private boolean shouldShowPrescription(Prescription appointment) {
        if (appliedFilters.isEmpty()) {
            return true;
        }

        for (String filter : appliedFilters) {
            switch (filter.toLowerCase()) {
                case "male":
                case "female":
                    if (appointment.getGender().equalsIgnoreCase(filter)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    static class ViewHolder {
        ImageView genderIcon;
        TextView nameTextView;
        TextView pres_details;
        
    }
}