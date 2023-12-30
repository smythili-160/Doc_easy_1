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

import java.util.List;
import java.util.Map;

public class DoctorLeavesAdapter extends ArrayAdapter<Leave> {
    private Context pContext;
    private List<Leave> leaves;

    public DoctorLeavesAdapter(Context context, List<Leave> leaves) {
        super(context, 0, leaves);
        pContext = context;
        this.leaves = leaves;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DoctorLeavesAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(pContext).inflate(R.layout.patient_list_item, parent, false);
            holder = new DoctorLeavesAdapter.ViewHolder();
            holder.genderIcon = convertView.findViewById(R.id.genderIcon);
            holder.name = convertView.findViewById(R.id.nameTextView);
            holder.startDate = convertView.findViewById(R.id.ageTextView);
            holder.endDate = convertView.findViewById(R.id.phoneNumberTextView);
            convertView.setTag(holder);
        } else {
            holder = (DoctorLeavesAdapter.ViewHolder) convertView.getTag();
        }
        Leave leave = leaves.get(position);

        convertView.setVisibility(View.VISIBLE);
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        updateViews(holder, leave);

        return convertView;
    }

    private void updateViews(DoctorLeavesAdapter.ViewHolder holder, Leave leave) {
        holder.genderIcon.setImageResource(R.drawable.doctor_icon);
        holder.name.setText(leave.getDoctor_user_name());
        holder.startDate.setText("Start Date: " + leave.getLeave_start_date());
        holder.endDate.setText("End Date: " + leave.getLeave_end_date());
    }

    static class ViewHolder {
        ImageView genderIcon;
        TextView startDate;
        TextView endDate;
        TextView name;
    }
}
