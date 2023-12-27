package com.example.doc_easy_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    Context mContext;
    ArrayList<Doctor> doctorArrayList;
    private List<String> appliedFilters;

    public DoctorAdapter(@NonNull Context context, ArrayList<Doctor> list) {
        super(context, 0, list);
        mContext = context;
        doctorArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DoctorAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.doctor_list_item, parent, false);

            holder = new DoctorAdapter.ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.d_usernameTextView);
            holder.phoneNumberTextView = convertView.findViewById(R.id.d_phoneNumberTextView);
            holder.availabilityTextView = convertView.findViewById(R.id.d_availabilityTextView);
            holder.specialityTextView = convertView.findViewById(R.id.d_SpecialityTextView);

            convertView.setTag(holder);
        } else {
            holder = (DoctorAdapter.ViewHolder) convertView.getTag();
        }

        Doctor doctor = doctorArrayList.get(position);

//        if (!shouldShowAppointment(doctor)) {
//            convertView.setVisibility(View.GONE);
//            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        } else {
            convertView.setVisibility(View.VISIBLE);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            updateViews(holder, doctor);
            setItemClickAction(convertView, doctor);
//        }

        return convertView;
    }

    private void updateViews(DoctorAdapter.ViewHolder holder, Doctor doctor) {
        holder.nameTextView.setText(doctor.getUsername());
        holder.phoneNumberTextView.setText(String.valueOf(doctor.getPhoneNumber()));
        holder.availabilityTextView.setText(doctor.getTimeSlots());
        holder.specialityTextView.setText(doctor.getSpeciality());
    }

    private void setItemClickAction(View itemView, Doctor doctor) {
        itemView.setOnClickListener(view -> {
            if (doctor != null && doctor.getMid() != null) {
                String doctorMid = doctor.getMid();
                startDoctorDetailsActivity(doctorMid);
            } else {
                Toast.makeText(mContext, "Sorry Doctor ID is Null...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDoctorDetailsActivity(String doctorID) {
        Intent intent = new Intent(getContext(), doc_prof.class);
        intent.putExtra("documentId", doctorID);
        mContext.startActivity(intent);
    }

//    private boolean shouldShowAppointment(Doctor doctor) {
//        if (appliedFilters.isEmpty()) {
//            return true;
//        }
//
//        for (String filter : appliedFilters) {
//            switch (filter.toLowerCase()) {
//                case "male":
//                case "female":
//                    if (appointment.getGender().equalsIgnoreCase(filter)) {
//                        return true;
//                    }
//                    break;
//
//                case "inpatient":
//                case "outpatient":
//                    if (appointment.getType().equalsIgnoreCase(filter)) {
//                        return true;
//                    }
//                    break;
//
//                case "age":
//                    // Implement logic to check age filters if needed
//                    // Example: if (appointment.getAge() >= MIN_AGE && appointment.getAge() <= MAX_AGE)
//                    break;
//
//                // Add additional cases for other filters as needed
//            }
//        }
//        return false;
//    }

    static class ViewHolder {
        TextView nameTextView;
        TextView phoneNumberTextView;
        TextView availabilityTextView;
        TextView specialityTextView;
    }
}
