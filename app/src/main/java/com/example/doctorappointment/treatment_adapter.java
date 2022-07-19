package com.example.doctorappointment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class treatment_adapter extends PagerAdapter {
    private Context context;
    private ArrayList<treatment_model> modelArrayList;

    public treatment_adapter(Context context, ArrayList<treatment_model> modelArrayList) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.card_treatments, container,false);
        ImageView treatment_image = view.findViewById(R.id.image_of_treatment);
        TextView title_treatment = view.findViewById(R.id.title_of_treatment);
        TextView description_treatment = view.findViewById(R.id.desciption_of_treatment);

        TextView treatment_click = view.findViewById(R.id.btn_of_treatment);

        treatment_model treatment_model = modelArrayList.get(position);

        String title = treatment_model.getTitle();
        String description = treatment_model.getDescription();
        String click = treatment_model.getTreatment_click();
        int image = treatment_model.getImage();

        title_treatment.setText(title);
        description_treatment.setText(description);
        treatment_click.setText(click);
        treatment_image.setImageResource(image);



        container.addView(view,position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
container.removeView((View) object);    }
}
