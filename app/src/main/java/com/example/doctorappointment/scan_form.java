package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class scan_form extends AppCompatActivity {




    private EditText name_scan, phone_scan, additional_info_scan, date_scan;

    private AutoCompleteTextView doctor_scan, time_scan;

    private ArrayAdapter<String> arrayAdapter1, arrayAdapter2;

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    ProgressDialog progressDialog;

    private Button submit_scan;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_form);


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        submit_scan = findViewById(R.id.fix_scan_appoint);


        name_scan = findViewById(R.id.name_scan);
        phone_scan = findViewById(R.id.phone_scan);
        additional_info_scan = findViewById(R.id.additional_info_scan);
        date_scan = findViewById(R.id.date_scan);
        doctor_scan = findViewById(R.id.select_doc_scan);
        time_scan = findViewById(R.id.time_slots_scan);
        progressDialog = new ProgressDialog(scan_form.this);
        progressDialog.setIcon(R.drawable.appoitn_icon);

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Your appointment is scheduling");
        ArrayList<String> time_slots = new ArrayList<>();
        time_slots.add("10:00 AM");
        time_slots.add("10:30 AM");
        time_slots.add("11:00 AM");
        time_slots.add("11:30 AM");
        time_slots.add("12:00 PM");
        time_slots.add("12:30 PM");
        time_slots.add("13:00 PM");
        time_slots.add("13:30 PM");
        time_slots.add("14:30 PM");
        time_slots.add("15:00 PM");
        time_slots.add("15:30 PM");
        time_slots.add("16:00 PM");
        time_slots.add("16:30 PM");
        time_slots.add("17:00 PM");
        time_slots.add("17:30 PM");
        time_slots.add("18:00 PM");
        arrayAdapter1 = new ArrayAdapter<>(this, R.layout.list_dropdown, time_slots);
        time_scan.setAdapter(arrayAdapter1);

        ArrayList<String> doctor_list = new ArrayList<>();
        doctor_list.add("Coimbatore");
        doctor_list.add("Tirunelveli");
        doctor_list.add("Trichy");
        doctor_list.add("Ooty");
        doctor_list.add("Arakkonam");
        arrayAdapter2 = new ArrayAdapter<>(this, R.layout.list_dropdown, doctor_list);
        doctor_scan.setAdapter(arrayAdapter2);

        date_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(scan_form.this, android.R.style.Theme_Holo_Light_Dialog
                        ,onDateSetListener,year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth+"/"+ month +"/"+ year;
                date_scan.setText(date);

            }
        };
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Registered users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    name_scan.setText(snapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        submit_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_scan.getText().toString();
                String phone = phone_scan.getText().toString();
                String additional = additional_info_scan.getText().toString();
                String doctor = doctor_scan.getText().toString();
                String date = date_scan.getText().toString();
                String timings = time_scan.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name is Empty",Toast.LENGTH_LONG).show();
                    name_scan.setError("Name is Empty");
                }
                else if(phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Phone number  not enterd",Toast.LENGTH_LONG).show();
                    phone_scan.setError("Please Enter Phone Number");
                }
                else if(phone.length() != 10){
                    Toast.makeText(getApplicationContext(), "Phone Number Not Valid",Toast.LENGTH_LONG).show();
                    phone_scan.setError("Phone Number Not Valid");
                }
                else if(additional.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Additional Info is Empty",Toast.LENGTH_LONG).show();
                    additional_info_scan.setError("Please Enter Additional Info");
                }
                else if(doctor.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Doctor not selected",Toast.LENGTH_LONG).show();
                    doctor_scan.setError("Please select The Doctor");
                }
                else if(date.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Date is Empty",Toast.LENGTH_LONG).show();
                    date_scan.setError("Enter the Date");
                }
                else if(timings.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No time slot selected",Toast.LENGTH_LONG).show();
                    time_scan.setError("No time slot selected");
                }
                else{
                    submit_scan_form(name,phone,additional,doctor,date,timings);
                }
            }

            private void submit_scan_form(String name, String phone, String additional, String doctor, String date, String timings) {
                DocumentReference documentReference = firebaseFirestore.collection("Appointments").document(firebaseUser.getUid()).collection("Your Appointments").document();
                Map<String, Object> map = new HashMap<>();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = null;
                try {
                    date1 = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                map.put("Name", name);
                map.put("Phone", phone);
                map.put("Treatments_Investigation", "Scan");
                map.put("Additional_Info", additional);
                map.put("Doctor", doctor);
                map.put("Date",date1);
                map.put("Timings", timings);
                new AlertDialog.Builder(scan_form.this).setIcon(R.drawable.treatment_icon)
                        .setTitle("Appointment Confirmation")
                        .setMessage("Your sure to fix an Appointment")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), Appointment_view_page.class));

                                    }
                                });

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Once done with the mind, Please do fix appointment",Toast.LENGTH_LONG).show();

                    }
                }).show();
            }
        });
    }
    int count = 0;
    @Override
    public void onBackPressed() {
        count+=1;
        if(count == 1){
            Toast.makeText(getApplicationContext(),"Are you confirmed to leave the form it will be not saved? If so once more backpress",Toast.LENGTH_SHORT).show();
        }
        else if(count == 2)
            super.onBackPressed();
    }
}