package com.example.doctorappointment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class tirchy_abt extends Fragment {

    TextView google_maps, facebook, insta, phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_tirchy_abt, container, false);
        google_maps = view.findViewById(R.id.google_maps_cbe);
        facebook  = view.findViewById(R.id.facebook);
        insta = view.findViewById(R.id.insta);
        phone = view.findViewById(R.id.phone);

        google_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl("https://goo.gl/maps/QXZ8eSyP1yxjuPVr8");
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl("https://www.facebook.com/vamsamfertilitycentre");
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURl("https://www.instagram.com/vamsamfertilitycentre/");
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9362373344"));
                startActivity(intent);
            }
        });




        return view;

    }
    private void gotoURl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}