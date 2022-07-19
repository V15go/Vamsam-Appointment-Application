package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    ImageView back_login;
    EditText username_login, password_login;
    Button login_btn;
    TextView forgot_password;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        back_login = findViewById(R.id.imageView);
        username_login = findViewById(R.id.username_login);
        password_login = findViewById(R.id.password_login);
        forgot_password = findViewById(R.id.forgot_password_login);
        login_btn = findViewById(R.id.login_btn);

        progressDialog = new ProgressDialog(login_page.this);
        progressDialog.setIcon(R.drawable.appoitn_icon);
        progressDialog.setTitle("Hold on");
        progressDialog.setMessage("Getting you in");


        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_page.this, get_started.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_l = username_login.getText().toString();
                String password_l = password_login.getText().toString();

                if(username_l.equals("Vamsam Admin Panel") && password_l.equals("vamsam123")){
                    startActivity(new Intent(getApplicationContext(), arakkonam_appoint.class));
                }
                else if(TextUtils.isEmpty(username_l)){
                    Toast.makeText(login_page.this,"Please Enter your username/Email ID", Toast.LENGTH_LONG).show();
                    username_login.setError("Email ID Required*");
                    username_login.requestFocus();
                }
                else if(TextUtils.isEmpty(password_l)){
                    Toast.makeText(login_page.this,"Please Enter your Password", Toast.LENGTH_LONG).show();
                    password_login.setError("Password Required*");
                    password_login.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username_l).matches()){
                    Toast.makeText(login_page.this,"Enter valid email ID",Toast.LENGTH_LONG).show();
                    username_login.setError("Email ID not valid*");
                    username_login.requestFocus();
                }
                else{
                    login_user(username_l, password_l);
                }
            }

            private void login_user(String username_l, String password_l) {
                progressDialog.show();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(username_l,password_l).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.show();
                            Toast.makeText(login_page.this,"Welcome Back!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login_page.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            progressDialog.dismiss();
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(login_page.this, "Something went wrong! Ensure that the email or password is correct or Verfication of email may not be done", Toast.LENGTH_LONG).show();
                            username_login.setError("Check your username");
                            username_login.requestFocus();
                            password_login.setError("Check your Password");
                            password_login.requestFocus();
                        }
                    }
                });
            }
        });



    }

    public void forgot_password(View view) {

        startActivity(new Intent(getApplicationContext(), forgot_password_page.class));
    }
}