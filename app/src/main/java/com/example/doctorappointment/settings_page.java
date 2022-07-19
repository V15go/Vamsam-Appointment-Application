package com.example.doctorappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class settings_page extends AppCompatActivity {

        private TextView profile_settings,  appointments_settings, about_us_settings, sign_out_btn;


         BottomNavigationView bottomNavigationView;

    FloatingActionButton floatingActionButton;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);


        profile_settings = findViewById(R.id.profile_settings);
        appointments_settings = findViewById(R.id.appointments_settings);
        about_us_settings = findViewById(R.id.about_us_settings);
        sign_out_btn = findViewById(R.id.sign_out_settings);

        bottomNavigationView = findViewById(R.id.bottom_settings);
        floatingActionButton = findViewById(R.id.fab_settings);

        bottomNavigationView.getMenu().getItem(4).setEnabled(true);
        bottomNavigationView.getMenu().getItem(4).setChecked(true);

        bottomNavigationView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return false;
            }
        });

        bottomNavigationView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),about_us_page.class));
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
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), appointment_types.class));
            }
        });




        about_us_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_page.this, about_us_page.class));
            }
        });
      /*  about_us_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_page.this, about_us_page.class));
            }
        });*/
    }

    public void profile_navi(View view) {

        startActivity(new Intent(getApplicationContext(), profile_page.class));
    }

    public void appoints_navi(View view) {
        startActivity(new Intent(getApplicationContext(), Appointment_view_page.class));
    }

    public void about_us_navi(View view) {
        startActivity(new Intent(getApplicationContext(), about_us_page.class));
    }
}