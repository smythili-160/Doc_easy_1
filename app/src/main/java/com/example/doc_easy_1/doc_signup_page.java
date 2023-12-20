package com.example.doc_easy_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class doc_signup_page extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText  doc_signup_email, doc_signup_password;
    Button doc_signup_button,doc_loginRedirectText;
    FirebaseAuth dAuth;
    FirebaseFirestore doc_user;
    String doc_name,doc_username,doc_phno,doc_spec,doc_exp,doc_ts,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_signup_page);
        doc_signup_email = findViewById(R.id.doc_signup_email);
        doc_signup_password = findViewById(R.id.doc_signup_password);
        doc_loginRedirectText = findViewById(R.id.doc_loginRedirectText);
        doc_signup_button = findViewById(R.id.doc_signup_button);
        dAuth=FirebaseAuth.getInstance();
        doc_user=FirebaseFirestore.getInstance();
        doc_loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doc_signup_page.this, doc_login_page.class);
                startActivity(intent);
                finish();
            }
        });
        Bundle b = getIntent().getExtras();
        doc_name = b != null ? b.getString("name") : null;
        doc_username = b != null ? b.getString("username") : null;
        doc_phno = b != null ? b.getString("phoneNumber") : null;
        doc_spec = b != null ? b.getString("speciality") : null;
        doc_exp = b != null ? b.getString("Experience") : null;
        doc_ts = b != null ? b.getString("TimeSlots") : null;

        doc_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc_email = doc_signup_email.getText().toString().trim();
                String doc_password = doc_signup_password.getText().toString().trim();

                if(dAuth.getCurrentUser() != null){
                    Intent intent=new Intent(new Intent(getApplicationContext(),doc_home_page.class));
                    startActivity(intent);
                    finish();

                }
                if(doc_password.length()<6){
                    doc_signup_password.setError("Password must have more than 6 characters");
                }


                if(doc_name.isEmpty() || doc_email.isEmpty() || doc_password.isEmpty() || doc_username.isEmpty()|| doc_phno.isEmpty()){
                    Toast.makeText( doc_signup_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }




                dAuth.createUserWithEmailAndPassword(doc_email, doc_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(doc_signup_page.this, "user creation successful", Toast.LENGTH_SHORT).show();
                            userID=dAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=doc_user.collection("doc_user").document(userID);
                            Map<String,Object> doc_user=new HashMap<>();

                            doc_user.put("name",doc_name);
                            doc_user.put("username",doc_username);
                            doc_user.put("phoneNumber",doc_phno);
                            doc_user.put("speciality",doc_spec);
                            doc_user.put("Experience",doc_exp);
                            doc_user.put("TimeSlots",doc_ts);
                            doc_user.put("doc_email_address",doc_email);

                            documentReference.set(doc_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"user is created"+userID);

                                }
                            });

                            Intent intent=new Intent(doc_signup_page.this,doc_home_page.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(doc_signup_page.this, "user creation unsuccessful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });
        


    }

}