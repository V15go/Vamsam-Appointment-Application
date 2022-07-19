package com.example.doctorappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class about_us_page extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager_about;
    BottomNavigationView bottomNavigationView;

    FloatingActionButton floatingActionButton;
    viewAdapter viewAdapter;

    private final String[] cities =  new String[]{"Coimbatore","Tirunelveli","Trichy","Ooty","Arakkonam"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);

        bottomNavigationView = findViewById(R.id.bottom_about);
        floatingActionButton = findViewById(R.id.fab_about);

        tabLayout = findViewById(R.id.tablayout);
        viewPager_about = findViewById(R.id.view_about);

        viewAdapter = new viewAdapter(this);
        viewPager_about.setAdapter(viewAdapter);

     new TabLayoutMediator(tabLayout,viewPager_about,(( (tab, position) ->  tab.setText(cities[position])))).attach();

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




    }

    private void gotoURl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}