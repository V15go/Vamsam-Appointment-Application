package com.example.doctorappointment;

public class treatment_model {

    String title, description, treatment_click;
    int image;

    public treatment_model(String title, String description, String treatment_click, int image) {
        this.title = title;
        this.description = description;
        this.treatment_click = treatment_click;
        this.image = image;
    }

    public  treatment_model(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment_click() {
        return treatment_click;
    }

    public void setTreatment_click(String treatment_click) {
        this.treatment_click = treatment_click;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
