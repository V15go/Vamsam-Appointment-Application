package com.example.doctorappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_main extends AppCompatActivity {

    EditText username, password;
    Button get_in_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        username = findViewById(R.id.username_admin);
        password = findViewById(R.id.password_admin);
        get_in_btn = findViewById(R.id.center_get_in);


        get_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("vamsam coimbatore") && pass.equals("vamsamcbe22")){
                    startActivity(new Intent(getApplicationContext(), coimbatore_appoint.class));
                }

               else if(user.equals("vamsam tirunelveli") && pass.equals("vamsamtirunelveli22")){
                    startActivity(new Intent(getApplicationContext(), tirunlveli_appoint.class));
                }
               else if(user.equals("vamsam trichy") && pass.equals("vamsamtrichy22")){
                    startActivity(new Intent(getApplicationContext(), trichy_appoint.class));
                }
                else if(user.equals("vamsam ooty") && pass.equals("vamsamooty22")){
                    startActivity(new Intent(getApplicationContext(), ooty_appoint.class));
                }
                else if(user.equals("vamsam arakkonam") && pass.equals("vamsamarakkonam22")){
                    startActivity(new Intent(getApplicationContext(), arakkonam_appoint.class));
                }
                else{
                    username.setError("Wrong Credentials");
                    password.setError("Wrong Credentials");
                    Toast.makeText(getApplicationContext(),"Pease check the Cerdentials", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}