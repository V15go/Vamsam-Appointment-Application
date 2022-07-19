package com.example.doctorappointment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class appointment_details_page extends AppCompatActivity {

    TextView name_d, phone_d, type_d, additional_d, doctor_d, date_d, time_d;
    Button cancel_btn;

    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details_page);
        name_d = findViewById(R.id.name_appoint);
        phone_d = findViewById(R.id.phone_appoint);
        type_d = findViewById(R.id.appointment_for_appoint);
        additional_d = findViewById(R.id.additional_appoint);
        doctor_d = findViewById(R.id.doctor_appoint);
        date_d = findViewById(R.id.date_appoint);
        time_d = findViewById(R.id.time_appoint);
        cancel_btn = findViewById(R.id.cancel_appoint);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        Intent i = getIntent();


        name_d.setText(i.getStringExtra("Name"));
        phone_d.setText(i.getStringExtra("Phone"));
        type_d.setText(i.getStringExtra("Type"));
        additional_d.setText(i.getStringExtra("Additional"));
        doctor_d.setText(i.getStringExtra("Doctor"));
        date_d.setText(i.getStringExtra("Date"));
        time_d.setText(i.getStringExtra("Time"));

        DocumentReference documentReference = firebaseFirestore.collection("Appointments").document(firebaseUser.getUid()).collection("Your Appointments").document(i.getStringExtra("Appointment_id"));


       cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(appointment_details_page.this).setIcon(R.drawable.treatment_icon)
                        .setTitle("Appointment")
                        .setMessage("Are you sure to Cancel the Appointment")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               documentReference.delete();
                               startActivity(new Intent(getApplicationContext(),Appointment_view_page.class));

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Thank you",Toast.LENGTH_LONG).show();

                    }
                }).show();
            }
        });
    }

    public void bacK_to(View view) {
        startActivity(new Intent(getApplicationContext(), Appointment_view_page.class));
    }
}