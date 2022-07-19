package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView name_dis, home_navi,chat_navi;
    ViewPager viewPager, doctor_view;
    FirebaseFirestore firebaseFirestore;



    ArrayList<model_doctor> model_doctors;
    doctor_adapter doctor_adapter;
    FloatingActionButton floatingActionButton;
    FirestoreRecyclerAdapter<firebasemodel, appointViewHolder> appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat_navi = findViewById(R.id.chat_navi);



        recyclerView = findViewById(R.id.recyclerView);
        home_navi = findViewById(R.id.home_navi);
        bottomNavigationView = findViewById(R.id.bottom_home);
        firebaseAuth = FirebaseAuth.getInstance();
        floatingActionButton = findViewById(R.id.fab_home);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        name_dis = findViewById(R.id.name_dis);
        doctor_view = findViewById(R.id.doctor_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(0).setEnabled(true);

        load_doctor();

        chat_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl("https://chatwith.io/s/6274151043ee8");
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), appointment_types.class));
            }
        });

        home_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), appointment_types.class));
            }
        });
        bottomNavigationView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {


        Intent intent = new Intent(MainActivity.this, about_us_page.class);
        startActivity(intent);
        return false;
    }
});

        bottomNavigationView.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(MainActivity.this, profile_page.class);
                startActivity(intent);
                return false;
            }
        });

        bottomNavigationView.getMenu().getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(MainActivity.this, settings_page.class);
                startActivity(intent);
                return false;
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Registered users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())

                    name_dis.setText("Hello, " + snapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        Query query = firebaseFirestore.collection("Appointments").document(firebaseUser.getUid()).
                collection("Your Appointments").orderBy("Date", Query.Direction.ASCENDING).limit(1);
        FirestoreRecyclerOptions<firebasemodel> all_appointments = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query, firebasemodel.class).build();



        appointmentAdapter = new FirestoreRecyclerAdapter<firebasemodel, appointViewHolder>(all_appointments) {
            @Override
            protected void onBindViewHolder(@NonNull appointViewHolder holder, int position, @NonNull firebasemodel model) {



                holder.doctor_dis.setText(model.getDoctor());
                String date = null;
                if(model.getDate() != null)
                    date = model.getDate().toString();

                assert date != null;
                date = date.substring(0,10);

                String time = date + " | "+ model.getTimings();
                holder.date_dis.setText(time);

                holder.treatment_for.setText(model.getTreatments_Investigation());

                String appId = appointmentAdapter.getSnapshots().getSnapshot(position).getId();

                String finalDate = date;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), appointment_details_page.class);
                        intent.putExtra("Name", model.getName());
                        intent.putExtra("Phone", model.getPhone());
                        intent.putExtra("Type", model.getTreatments_Investigation());
                        intent.putExtra("Additional",model.getAdditional_Info());
                        intent.putExtra("Doctor", model.getDoctor());
                        intent.putExtra("Date", finalDate);
                        intent.putExtra("Time",model.getTimings());
                        intent.putExtra("Appointment_id", appId);
                        v.getContext().startActivity(intent);
                    }
                });



            }

            @NonNull
            @Override
            public appointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_grid, parent, false);
                return new appointViewHolder(view);
            }
        };

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(appointmentAdapter);








    }
    public void appoint_navi(View view) {
        startActivity(new Intent(getApplicationContext(), Appointment_view_page.class));
    }

    private void load_doctor() {
        model_doctors = new ArrayList<>(8);

        model_doctors.add(new model_doctor(R.drawable.doc_1));
        model_doctors.add(new model_doctor(R.drawable.doc_2));
        model_doctors.add(new model_doctor(R.drawable.doc_3));
        model_doctors.add(new model_doctor(R.drawable.doc_4));
      //  model_doctors.add(new model_doctor(R.drawable.doc_5));
        //model_doctors.add(new model_doctor(R.drawable.doc_6));
        //model_doctors.add(new model_doctor(R.drawable.doc_7));
        //model_doctors.add(new model_doctor(R.drawable.doc_8));
        doctor_adapter = new doctor_adapter(this, model_doctors);
        doctor_view.setAdapter(doctor_adapter);
        doctor_view.setPadding(30,0,30,0);
    }




    public static class appointViewHolder extends RecyclerView.ViewHolder{


        private TextView doctor_dis;
        private TextView date_dis, treatment_for;

        public appointViewHolder(@NonNull View itemView) {
            super(itemView);

           doctor_dis = itemView.findViewById(R.id.Doctor_name);
           date_dis = itemView.findViewById(R.id.time_date);
           treatment_for = itemView.findViewById(R.id.treatment_of);




        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        appointmentAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (appointmentAdapter != null) {
            appointmentAdapter.startListening();
        }
    }
    public void appoint_history(View view) {
        Intent intent = new Intent(getApplicationContext(), Appointment_view_page.class);
        startActivity(intent);
    }
    private void gotoURl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}