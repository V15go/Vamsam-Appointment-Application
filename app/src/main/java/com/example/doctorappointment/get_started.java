package com.example.doctorappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class get_started extends AppCompatActivity {

    ImageView get_image;
    Button login_navi, signup_navi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        get_image = findViewById(R.id.get_image);
        login_navi = findViewById(R.id.login_get_started_btn);
        signup_navi = findViewById(R.id.new_user_get_started_btn);

        //animate




        //login
        login_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(get_started.this, login_page.class));

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        //SignUp
        signup_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(get_started.this, sign_up_page.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });











    }
}