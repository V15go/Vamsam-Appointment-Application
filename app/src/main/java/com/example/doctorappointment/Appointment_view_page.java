package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import pl.droidsonroids.gif.GifImageView;

public class Appointment_view_page extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    TextView empty;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    BottomNavigationView bottomNavigationView;


    FirestoreRecyclerAdapter<firebasemodel, appointViewHolder> appointmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view_page);


        bottomNavigationView = findViewById(R.id.bottom_view);
        floatingActionButton = findViewById(R.id.fab_view);
        recyclerView = findViewById(R.id.recyclerView);
        empty = findViewById(R.id.empty);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        bottomNavigationView.getMenu().getItem(1).setEnabled(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);


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


        Query query = firebaseFirestore.collection("Appointments").document(firebaseUser.getUid()).collection("Your Appointments").orderBy("Date", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<firebasemodel> all_appointments = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query, firebasemodel.class).build();




                appointmentAdapter = new FirestoreRecyclerAdapter<firebasemodel, appointViewHolder>(all_appointments) {


                    @Override
                    public int getItemCount() {
                        return all_appointments.getSnapshots().size();
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull appointViewHolder holder, int position, @NonNull firebasemodel model) {


                        holder.doctor_app.setText(model.getDoctor());
                        String date = null;
                        if (model.getDate() != null)
                            date = model.getDate().toString();

                        assert date != null;
                        date = date.substring(0, 10);

                        String time = "Time: " + model.getTimings();
                        holder.time_app.setText(time);
                        holder.date_app.setText(date);
                        holder.type_app.setText(model.getTreatments_Investigation());

                        String appId = appointmentAdapter.getSnapshots().getSnapshot(position).getId();

                        String finalDate = date;
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Appointment_view_page.this, appointment_details_page.class);
                                intent.putExtra("Name", model.getName());
                                intent.putExtra("Phone", model.getPhone());
                                intent.putExtra("Type", model.getTreatments_Investigation());
                                intent.putExtra("Additional", model.getAdditional_Info());
                                intent.putExtra("Doctor", model.getDoctor());
                                intent.putExtra("Date", finalDate);
                                intent.putExtra("Time", model.getTimings());
                                intent.putExtra("Appointment_id", appId);
                                v.getContext().startActivity(intent);
                            }

                        });


                    }

                    @NonNull
                    @Override
                    public appointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_grid, parent, false);
                        return new appointViewHolder(view);
                    }
                };


                staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(appointmentAdapter);






    }

    public static class appointViewHolder extends RecyclerView.ViewHolder{


       private TextView doctor_app;
       private TextView date_app, time_app, type_app;

        public appointViewHolder(@NonNull View itemView) {
            super(itemView);

            doctor_app = itemView.findViewById(R.id.doctor_name);
            date_app = itemView.findViewById(R.id.date_of_appointment);
            time_app = itemView.findViewById(R.id.time_of_appointment);
            type_app = itemView.findViewById(R.id.type_of_appointment);



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
}