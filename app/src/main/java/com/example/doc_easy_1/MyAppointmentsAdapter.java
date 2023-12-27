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

public class MyAppointmentsAdapter extends ArrayAdapter<Appointment> {

    private Context mContext;
    private List<Appointment> mAppointments;
    private List<String> appliedFilters;


    public MyAppointmentsAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
        mContext = context;
        mAppointments = appointments;
        appliedFilters = new ArrayList<>();
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_appointments_list_item, parent, false);

            holder = new ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.dateTextView = convertView.findViewById(R.id.dateTextView);
            holder.timeslotTextView = convertView.findViewById(R.id.timeslotTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Appointment currentAppointment = mAppointments.get(position);

        if (!shouldShowAppointment(currentAppointment)) {
            convertView.setVisibility(View.GONE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        } else {
            convertView.setVisibility(View.VISIBLE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            updateViews(holder, currentAppointment);
            setItemClickAction(convertView, currentAppointment);
        }

        return convertView;
    }

    private void updateViews(ViewHolder holder, Appointment appointment) {
        if (appointment.getGender().toLowerCase().equals("male")) {
            holder.genderIcon.setImageResource(R.drawable.male_icon);
        } else {
            holder.genderIcon.setImageResource(R.drawable.female_icon);
        }

        holder.nameTextView.setText(appointment.getName());
        holder.dateTextView.setText(appointment.getDate());
        holder.timeslotTextView.setText(appointment.getTime());
    }

    private void setItemClickAction(View itemView, Appointment appointment) {
        itemView.setOnClickListener(view -> {
            if (appointment != null && appointment.getMid() != null) {
                String appointmentID = appointment.getMid();
                DocumentReference appointmentRef = FirebaseFirestore.getInstance().collection("appointments").document(appointmentID);
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
                Toast.makeText(mContext, "Sorry Appointment ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPrescriptionFormActivity(String appointmentID, Bundle appointmentDetails) {
        Intent intent = new Intent(getContext(), PrescriptionForm.class);
        intent.putExtra("documentId", appointmentID);
        intent.putExtras(appointmentDetails);
        mContext.startActivity(intent);
    }

    private boolean shouldShowAppointment(Appointment appointment) {
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

                case "inpatient":
                case "outpatient":
                    if (appointment.getType().equalsIgnoreCase(filter)) {
                        return true;
                    }
                    break;

                case "age":
                    // Implement logic to check age filters if needed
                    // Example: if (appointment.getAge() >= MIN_AGE && appointment.getAge() <= MAX_AGE)
                    break;

                // Add additional cases for other filters as needed
            }
        }
        return false;
    }

    static class ViewHolder {
        ImageView genderIcon;
        TextView nameTextView;
        TextView dateTextView;
        TextView timeslotTextView;
    }
}
