package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_page extends AppCompatActivity {



    TextView name_profile, spouse_name_profile, age_profile, gender_profile, city_profile, phone_profile;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        name_profile = findViewById(R.id.name_profile);
        city_profile = findViewById(R.id.city_profile);
        age_profile = findViewById(R.id.Age_profile);
        gender_profile = findViewById(R.id.gender_profile);
        spouse_name_profile = findViewById(R.id.spouse_name_profile);
        phone_profile = findViewById(R.id.phone_profile);
        bottomNavigationView = findViewById(R.id.bottom_profile);
        floatingActionButton = findViewById(R.id.fab_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Registered users");
        bottomNavigationView.getMenu().getItem(3).setEnabled(true);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);


        bottomNavigationView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return false;
            }
        });

        bottomNavigationView.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),profile_page.class));
                return false;
            }
        });
        bottomNavigationView.getMenu().getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),settings_page.class));
                return false;
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), appointment_types.class));
            }
        });



        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name_profile.setText(snapshot.child("Name").getValue().toString());
                    city_profile.setText(snapshot.child("City").getValue().toString());
                    age_profile.setText(snapshot.child("Age").getValue().toString());
                    spouse_name_profile.setText(snapshot.child("Spouse name").getValue().toString());
                    gender_profile.setText(snapshot.child("Gender").getValue().toString());
                    phone_profile.setText(snapshot.child("Phone Number").getValue().toString());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}