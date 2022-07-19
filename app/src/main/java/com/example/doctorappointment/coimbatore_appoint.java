package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class coimbatore_appoint extends AppCompatActivity {

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<firebasemodel, appointViewHolder> appointmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coimbatore_appoint);

        recyclerView = findViewById(R.id.coimbatore_recyclerView);


        Query query = FirebaseFirestore.getInstance().collectionGroup("Your Appointments").whereEqualTo("Doctor", "Coimbatore");
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
                        Intent intent = new Intent(coimbatore_appoint.this, admin_appointment_details.class);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_admin_grid, parent, false);
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