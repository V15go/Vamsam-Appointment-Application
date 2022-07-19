package com.example.doctorappointment;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class doctor_adapter extends PagerAdapter {

    private Context context;
    private ArrayList<model_doctor> modelArrayList;

    public doctor_adapter(Context context, ArrayList<model_doctor> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card, container,false);

        ImageView image = view.findViewById(R.id.doctor_photo);

        model_doctor model_doctor = modelArrayList.get(position);
        int img = model_doctor.getImage();
        image.setImageResource(img);
        container.addView(view,position);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
