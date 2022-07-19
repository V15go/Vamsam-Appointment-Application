package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password_page extends AppCompatActivity {



    Button rest_btn;

    EditText username_reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        rest_btn = findViewById(R.id.reset_btn);
        username_reset = findViewById(R.id.username_rest);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        rest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_reset.getText().toString();
                String[] valid = username.split("@");


                if(TextUtils.isEmpty(username)){
                    Toast.makeText(forgot_password_page.this,"Please Enter your Email ID",Toast.LENGTH_LONG).show();
                    username_reset.setError("Email ID Required*");
                    username_reset.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    Toast.makeText(forgot_password_page.this,"Enter valid email ID",Toast.LENGTH_LONG).show();
                    username_reset.setError("Email ID not valid*");
                    username_reset.requestFocus();
                }
                else if(!valid[1].equals("gmail.com")){
                    Toast.makeText(forgot_password_page.this,"Gmail account required",Toast.LENGTH_LONG).show();
                    username_reset.setError("Gmail account required*");
                    username_reset.requestFocus();
                }
                else{
                    restpassword(username);
                }
            }

            private void restpassword(String username) {

                auth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgot_password_page.this,"Check Your to change your password",Toast.LENGTH_LONG).show();;
                            startActivity(new Intent(forgot_password_page.this,login_page.class));
                        }
                        else{
                            Toast.makeText(forgot_password_page.this,"Something went Wrong! Try Again",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });



    }
}