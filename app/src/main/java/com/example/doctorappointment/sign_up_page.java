package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class sign_up_page extends AppCompatActivity {

    ImageView back_btn;
            private Button sign_up_btn;
             private EditText name_sign, spouse_name_sign,age_sign, dob_sign,city_sign,phone_sign,username_sign,password_sign;
             private RadioGroup gender_sign;
    private static final String TAG = "Regisiter_page";

    private RadioButton options_gender;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    public static HashMap<String,Object> map  = new HashMap<>();
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        back_btn = findViewById(R.id.back_btn);
        sign_up_btn  = findViewById(R.id.signup_btn);
        name_sign = findViewById(R.id.name_signup);
        spouse_name_sign = findViewById(R.id.spouse_name_signup);
        age_sign = findViewById(R.id.age_signup);
        dob_sign = findViewById(R.id.dob_signup);
        city_sign = findViewById(R.id.city_signup);

        phone_sign = findViewById(R.id.phone_signup);
        username_sign = findViewById(R.id.username_signup);
        password_sign = findViewById(R.id.password_signup);
        gender_sign = findViewById(R.id.gender_signup);
        progressDialog = new ProgressDialog(sign_up_page.this);
        progressDialog.setIcon(R.drawable.appoitn_icon);
        progressDialog.setTitle("Hold on");
        progressDialog.setMessage("Your Account is getting Ready!");



        dob_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(sign_up_page.this, android.R.style.Theme_Holo_Light_Dialog
                        ,onDateSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth+"/"+ month +"/"+ year;
                dob_sign.setText(date);

            }
        };


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_up_page.this, get_started.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });


        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textname = name_sign.getText().toString();
                String textspousename = spouse_name_sign.getText().toString();
                String textage = age_sign.getText().toString();
                String textdob = dob_sign.getText().toString();
                int radioId = gender_sign.getCheckedRadioButtonId();
                options_gender =findViewById(radioId);
                String textgender = options_gender.getText().toString();
                String textaddress = city_sign.getText().toString();
                String textphone  =phone_sign.getText().toString();
                String textusername = username_sign.getText().toString();
                String textpass  = password_sign.getText().toString();

                if(TextUtils.isEmpty(textname)){
                    Toast.makeText(sign_up_page.this,"Please Enter your name",Toast.LENGTH_LONG).show();
                    name_sign.setError("Name Required*");
                    name_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textspousename)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Spouse Name",Toast.LENGTH_LONG).show();
                    spouse_name_sign.setError("Spouse Name Required*");
                    spouse_name_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textage)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Age",Toast.LENGTH_LONG).show();
                    age_sign.setError("Age Required*");
                    age_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textdob)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Date of Birth",Toast.LENGTH_LONG).show();
                    dob_sign.setError("DOB Required*");
                    dob_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textaddress)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Address",Toast.LENGTH_LONG).show();
                    city_sign.setError("Address Required*");
                    city_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textphone)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Phone Number",Toast.LENGTH_LONG).show();
                    phone_sign.setError("Phone number Required*");
                    phone_sign.requestFocus();
                }
                else if(textphone.length()!=10){
                    Toast.makeText(sign_up_page.this,"Please Enter your Phone Number in 10 Digits",Toast.LENGTH_LONG).show();
                    phone_sign.setError("Phone number Should be in 10 Digits*");
                    phone_sign.requestFocus();
                }
                else if(TextUtils.isEmpty(textusername)){
                    Toast.makeText(sign_up_page.this,"Please Enter your Email ID",Toast.LENGTH_LONG).show();
                    username_sign.setError("Email ID Required*");
                    username_sign.requestFocus();
                }
                else if(password_sign.length()<8){
                    Toast.makeText(sign_up_page.this,"Password should be of more than 8 characters",Toast.LENGTH_LONG).show();
                    password_sign.setError("Password should be of more than 8 characters*");
                    password_sign.requestFocus();
                }
                else{
                    Reg_user(textname,textspousename,textage,textdob,textgender,textphone,textaddress,textusername,textpass);

                }



                

                
            }

            private void Reg_user(String textname, String textspousename, String textage, String textdob, String textgender, String textphone, String textaddress, String textusername, String textpass) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();
                auth.createUserWithEmailAndPassword(textusername,textpass).addOnCompleteListener(sign_up_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.show();
                            Toast.makeText(sign_up_page.this,"You Have been Registered",Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            map.put("Name",textname);
                            map.put("Spouse name",textspousename);
                            map.put("Age",textage);
                            map.put("DOB",textdob);
                            map.put("Gender",textgender);
                            map.put("City",textaddress);
                            map.put("Phone Number",textphone);
                            map.put("Username", textusername);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(sign_up_page.this, "You Have been Registered. Please verify your email ID", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(sign_up_page.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        finish();
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(sign_up_page.this,"Registration failed. Do it again",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }
                        else {
                            progressDialog.dismiss();
                            try {
                                throw Objects.requireNonNull(task.getException());
                            }
                            catch (FirebaseAuthWeakPasswordException e){
                                username_sign.setError("Your Password is too weak, Kindly use a mix of alphabets and numericals");
                                username_sign.requestFocus();

                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                username_sign.setError("Your email is invalid or already in use. kindly re-entry");
                                username_sign.requestFocus();
                            }
                            catch (FirebaseAuthUserCollisionException e){
                                username_sign.setError("The email is already registered with us");
                                username_sign.requestFocus();
                            }
                            catch (Exception e){
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(sign_up_page.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });






            }
        });


















    }

}