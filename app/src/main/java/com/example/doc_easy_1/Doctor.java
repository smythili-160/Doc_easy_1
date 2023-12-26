package com.example.doc_easy_1;

public class Doctor {
    String name;
    String username;
    String phoneNumber;
    String speciality; 
    String Experience;
    String TimeSlots;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    String mid;
    public Doctor(){

    }
    public Doctor(String name, String doc_username, String phoneNumber, String doc_spec, String Experience, String TimeSlots) {
        this.name = name;
        this.username = doc_username;
        this.phoneNumber = phoneNumber;
        this.speciality = doc_spec;
        this.Experience = Experience;
        this.TimeSlots = TimeSlots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        this.Experience = experience;
    }

    public String getTimeSlots() {
        return TimeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        this.TimeSlots = timeSlots;
    }
}
