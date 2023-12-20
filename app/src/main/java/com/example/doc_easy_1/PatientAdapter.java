package com.example.doc_easy_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PatientAdapter extends ArrayAdapter<Appointment> {

    private Context mContext;
    private List<Appointment> mAppointments;

    public PatientAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
        mContext = context;
        mAppointments = appointments;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Appointment currentAppointment = mAppointments.get(position);

        ImageView genderIcon = listItem.findViewById(R.id.genderIcon);
        if (currentAppointment.getGender().equals("male")) {
            genderIcon.setImageResource(R.drawable.male_icon);
        } else{
            genderIcon.setImageResource(R.drawable.female_icon);
        }

        TextView nameTextView = listItem.findViewById(R.id.nameTextView);
        nameTextView.setText(currentAppointment.getName());

        TextView ageTextView = listItem.findViewById(R.id.ageTextView);
        ageTextView.setText(String.valueOf(currentAppointment.getAge()));

        TextView typeTextView = listItem.findViewById(R.id.typeTextView);
        typeTextView.setText(currentAppointment.getType());

        //Handle CLicked item to AppointmentDetails
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment clickedAppointment = mAppointments.get(position);
                if(clickedAppointment!=null && clickedAppointment.getMid() != null){
                    Log.d("AppointmentAdapter", "Clicked Appointment ID: " + clickedAppointment.getMid());
                    Log.d("AppointmentAdapter", "Clicked Appointment Name: " + clickedAppointment.getName());

                    String appointmentID = clickedAppointment.getMid();
                    DocumentReference appointmentRef = FirebaseFirestore.getInstance().collection("patients").document(appointmentID);
                    appointmentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                Log.d("AppointmentAdapter", "DocumentSnapshot exists");
                                Bundle appointmentDetails = new Bundle();
                                for(Map.Entry<String, Object> entry: documentSnapshot.getData().entrySet()){
                                    appointmentDetails.putString(entry.getKey(), entry.getValue().toString());
                                }
                                Intent intent = new Intent(getContext(), PatientDetails.class);
                                intent.putExtra("documentId", appointmentID);
                                intent.putExtras(appointmentDetails);
                                getContext().startActivity(intent);
                            }else{
                                Toast.makeText(mContext, "Unable to open details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(mContext, "Sorry Appointment ID is Null...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return listItem;
    }



}
