package com.example.doctorappointment;


import java.sql.Timestamp;
import java.util.Date;


public class firebasemodel {

    private String Name;
    private String Phone;
    private String Treatments_Investigation;
    private String Additional_Info;
    private String Doctor;
    private java.util.Date Date;
    private String Timings;



    public firebasemodel(){

    }

    public firebasemodel(String Name, String Phone, String Treatments_Investigation, String  Additional_Info,  String Doctor, String Timings){

        this.Name = Name;
        this.Phone = Phone;
        this.Treatments_Investigation = Treatments_Investigation;
        this.Additional_Info  = Additional_Info;
        this.Doctor = Doctor;
        this.Date = Date;
        this.Timings = Timings;


    }

    public firebasemodel(String Doctor,java.util.Date Date,  String Timings,String Treatments_Investigation ){
        this.Doctor = Doctor;
        this.Date = Date;
        this.Timings = Timings;
        this.Treatments_Investigation = Treatments_Investigation;

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTreatments_Investigation() {
        return Treatments_Investigation;
    }

    public void setTreatments_Investigation(String treatments_Investigation) {
        Treatments_Investigation = treatments_Investigation;
    }

    public String getAdditional_Info() {
        return Additional_Info;
    }

    public void setAdditional_Info(String additional_Info) {
        Additional_Info = additional_Info;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public java.util.Date getDate() {
        return  Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getTimings() {
        return Timings;
    }

    public void setTimings(String timings) {
        Timings = timings;
    }
}
