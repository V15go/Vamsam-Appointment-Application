package com.example.doctorappointment;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewAdapter extends FragmentStateAdapter {

    private final String[] cities =  new String[]{"Coimbatore","Tirunelveli","Trichy","Ooty","Arakkonam"};
    public viewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new coimbatore_abt();
            case 1:
               return new tirunelveli_abt();
            case 2:
                return new tirchy_abt();
            case 3:
                return new ooty_abt();
            case 4:
                return new arakkonam_abt();
        }

        return new coimbatore_abt();
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }
}
